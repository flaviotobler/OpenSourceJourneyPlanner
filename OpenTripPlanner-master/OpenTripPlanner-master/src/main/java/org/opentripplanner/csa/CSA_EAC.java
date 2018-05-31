package org.opentripplanner.csa;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opentripplanner.routing.core.RoutingRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CSA_EAC {
    
    public static Set<Journey> createJourneys(TimeTable timeTable, RoutingRequest request) throws JsonGenerationException, JsonMappingException, IOException{
        
        Set<StopHandlerCSA> stops = generateStopHandlers(timeTable.getStops());
        Set<TripHandlerCSA> trips = generateTripHandlers(timeTable.getTrips());
        Date dateTime = request.getDateTime();
        Date tempTime = dateTime;
        int year = request.getDateTime().getYear() + 1900;
        int month = request.getDateTime().getMonth();
        int day = request.getDateTime().getDate();
        System.out.println(year + " " + month + " " + day);
        System.out.println(request.from.name);
        StopHandlerCSA startStop = getStopFromCor(request.from.lng,request.from.lat,stops);
        StopHandlerCSA endStop = getStopFromCor(request.to.lng,request.to.lat,stops);
        System.out.println(request.getDateTime().getHours());
        System.out.println(request.getDateTime().getMinutes());
        Calendar testTime = new GregorianCalendar();
        System.out.println(testTime.get(Calendar.YEAR));
        Calendar startTime = new GregorianCalendar();
        startTime.setTime(dateTime);
        startStop.setStopTime(startTime);
        // Todo startpunkt für schleife finden
        for(ConnectionCSA c : timeTable.getConnectionsAscending())
        {
            tempTime.setHours(c.gethDepartureTime());
            tempTime.setMinutes(c.getMinDepartureTime());
            Calendar departureTime = new GregorianCalendar();
            departureTime.setTime(tempTime);
            tempTime.setHours(c.gethArrivalTime());
            tempTime.setMinutes(c.getMinArrivalTime());
            Calendar arrivalTime = new GregorianCalendar();
            arrivalTime.setTime(tempTime);
            if(endStop.getStopTime().before(departureTime)){
                break;
            }
            if(getTrip(c.getTrip(), trips).isTripFlag() || getStop(c.getDepartureStop(),stops).getStopTime().before(departureTime)){
                System.out.println("first if");
                if(!getTrip(c.getTrip(), trips).isTripFlag()){
                    System.out.println("second if");
                    tempTime.setHours(getStop(c.getDepartureStop(),stops).getStopTime().get(Calendar.HOUR));
                    tempTime.setMinutes(getStop(c.getDepartureStop(),stops).getStopTime().get(Calendar.MINUTE));
                    Calendar changeTestTime = new GregorianCalendar();
                    changeTestTime.setTime(tempTime);
                    changeTestTime.add(Calendar.SECOND, (int) timeTable.getFootPathChange(c.getDepartureStop()).getDuration());
                    if(changeTestTime.before(departureTime)){
                        System.out.println("third if");
                        setTripFlag(c.getTrip(),trips);
                        c.getTrip().setTripEnterConnection(c);
                        if(arrivalTime.before(getStop(c.getArrivalStop(),stops).getStopTime())){
                            System.out.println("fourth if");
                            setStopTime(c.getArrivalStop(),stops,arrivalTime);
                            setStopJP(c.getArrivalStop(),stops,(new JourneyPointer(new LegCSA(c.getTrip().getTripEnterConnection(),c),timeTable.getFootPathChange(c.getArrivalStop()))));
                        }
                    }
                }
                else{
                    System.out.println("second else");
                    if(arrivalTime.before(getStop(c.getArrivalStop(),stops).getStopTime())){
                        System.out.println("if in else");
                        setStopTime(c.getArrivalStop(),stops,arrivalTime);
                        setStopJP(c.getArrivalStop(),stops,(new JourneyPointer(new LegCSA(c.getTrip().getTripEnterConnection(),c),timeTable.getFootPathChange(c.getArrivalStop()))));
                    }
                }
            }
            System.out.println("not right connection");
        }
        StopHandlerCSA reconstructionPoint = endStop;
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        Journey journey = new Journey();
        while(reconstructionPoint.getStopJP() != null){
            journey.addJourneyPointer(reconstructionPoint.getStopJP()); 
            reconstructionPoint = getStop(reconstructionPoint.getStopJP().getLeg().getEnter().getDepartureStop(),stops);
        }
        journey.setStartPath(timeTable.getFootPathChange(startStop.getStop()));
        journeys.add(journey);
        
        
        return journeys;
        
    }
    
    private static void setStopJP(StopCSA stop, Set<StopHandlerCSA> stops,
            JourneyPointer journeyPointer) {
        for(StopHandlerCSA sh : stops){
            if(sh.getStop() == stop){
                sh.setStopJP(journeyPointer);
            }
        }
        
    }

    private static void setStopTime(StopCSA stop, Set<StopHandlerCSA> stops,
            Calendar time) {
        for(StopHandlerCSA sh : stops){
            if(sh.getStop() == stop){
                sh.setStopTime(time);
            }
        }
        
    }

    private static void setTripFlag(TripCSA trip, Set<TripHandlerCSA> trips) {
        for(TripHandlerCSA th : trips){
            if(th.getTrip() == trip){
                th.setTripFlag(true);
            }
        }
        
    }

    private static StopHandlerCSA getStop(StopCSA stop, Set<StopHandlerCSA> stops) {
        for(StopHandlerCSA sh : stops){
            if(sh.getStop() == stop){
                return sh;
            }
        }
        return null;
    }

    private static TripHandlerCSA getTrip(TripCSA trip, Set<TripHandlerCSA> trips) {
        for(TripHandlerCSA th : trips){
            if(th.getTrip() == trip){
                return th;
            }
        }
        return null;
    }

    private static Set<TripHandlerCSA> generateTripHandlers(Set<TripCSA> trips) {
        Set<TripHandlerCSA> tripHandlers = new HashSet<TripHandlerCSA>();
        for(TripCSA t : trips){
            TripHandlerCSA th = new TripHandlerCSA(t);
            tripHandlers.add(th);
        }
        return tripHandlers;
    }

    private static Set<StopHandlerCSA> generateStopHandlers(Set<StopCSA> stops) {
        Set<StopHandlerCSA> stopHandlers = new HashSet<StopHandlerCSA>();
        for(StopCSA s : stops){
            StopHandlerCSA sh = new StopHandlerCSA(s);
            stopHandlers.add(sh);
        }
        return stopHandlers;
    }

    public static StopHandlerCSA getStopFromCor (double lng, double lat, Set<StopHandlerCSA> stops) {

        StopHandlerCSA stop = null;

                Iterator<StopHandlerCSA> it = stops.iterator();
                 
                while(it.hasNext()){
                        stop = (StopHandlerCSA)it.next();
                        if(lng == stop.getStop().getLongitude()) {
                            if(lat == stop.getStop().getLatitude()){
                                return stop;
                            }
                                
                        }
                }

        return null;
    }
}
