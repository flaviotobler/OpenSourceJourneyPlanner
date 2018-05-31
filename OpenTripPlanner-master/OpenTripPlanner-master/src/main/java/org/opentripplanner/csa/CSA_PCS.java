package org.opentripplanner.csa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.opentripplanner.routing.core.RoutingRequest;

public class CSA_PCS {
    
    public static Set<Journey> createJourneys(TimeTable timeTable, RoutingRequest request){
        
        Date dateTime = request.getDateTime();
        Date tempTime = dateTime;
        Set<StopHandlerPCSA> stops = generateStopHandlers(timeTable.getStops());
        Set<TripHandlerPCSA> trips = generateTripHandlers(timeTable.getTrips());
        StopHandlerPCSA startStop = getStopFromCor(request.from.lng,request.from.lat,stops);
        StopHandlerPCSA endStop = getStopFromCor(request.to.lng,request.to.lat,stops);
        int year = request.getDateTime().getYear() + 1900;
        int month = request.getDateTime().getMonth();
        int day = request.getDateTime().getDate();
        Calendar t1 = new GregorianCalendar(20000,12 ,31,23,59,59);
        Calendar t2 = new GregorianCalendar(20000,12 ,31,23,59,59);
        Calendar t3 = new GregorianCalendar(20000,12 ,31,23,59,59);
        Calendar tc = new GregorianCalendar(20000,12 ,31,23,59,59);
        
        
        for(ConnectionCSA c: timeTable.getConnectionsDescending()){
            tempTime.setHours(c.gethDepartureTime());
            tempTime.setMinutes(c.getMinDepartureTime());
            Calendar departureTime = new GregorianCalendar();
            departureTime.setTime(tempTime);
            tempTime.setHours(c.gethArrivalTime());
            tempTime.setMinutes(c.getMinArrivalTime());
            Calendar arrivalTime = new GregorianCalendar();
            arrivalTime.setTime(tempTime);
            
            
            if(c.getArrivalStop() == endStop.getStop()){
                t1.set(Calendar.YEAR, year);
                t1.set(Calendar.MONTH, month);
                t1.set(Calendar.DATE, day);
                t1.set(Calendar.HOUR, c.gethArrivalTime());
                t1.set(Calendar.MINUTE, c.getMinArrivalTime());
                t1.set(Calendar.SECOND, c.getsArrivalTime());
                t1.add(Calendar.SECOND, (int) timeTable.getFootPathChange(c.getArrivalStop()).getDuration());
            } else {
                t1.set(Calendar.YEAR, 20000);
                t1.set(Calendar.MONTH, 11);
                t1.set(Calendar.DATE, 31);
                t1.set(Calendar.HOUR, 23);
                t1.set(Calendar.MINUTE, 59);
                t1.set(Calendar.SECOND, 59);
            }
            t2 = getTrip(trips, c.getTrip()).getTripTime();
            int index = 0;
            TimeTupel p = getStop(stops, c.getArrivalStop()).getTupel(index);
            while(p.getDepartureTime().before(arrivalTime)){
                index ++;
                p = getStop(stops, c.getArrivalStop()).getTupel(index);
            }
            t3 = p.getArrivalTime();
            
            tc.setTimeInMillis(Math.min(t1.getTimeInMillis(), Math.min(t2.getTimeInMillis(), t3.getTimeInMillis())));
            Calendar tTime = departureTime;
            tTime.add(Calendar.SECOND, ((int) timeTable.getFootPathChange(c.getDepartureStop()).getDuration())*(-1));
            p.setArrivalTime(tc);   
            p.setDepartureTime(tTime);
            TripHandlerPCSA tempTrip = getTrip(trips, c.getTrip());
            if(tempTrip.getTripExitConnection() == null){
                tempTrip.setTripExitConnection(c);
            }
            p.setJourneyPointer(new JourneyPointer(new LegCSA(c,tempTrip.getTripExitConnection()),timeTable.getFootPathChange(tempTrip.getTripExitConnection().getArrivalStop())));
            //TimeTupel q = getStop(stops, c.getDepartureStop()).getTupel(0);
            getStop(stops, c.getDepartureStop()).addTupelFront(p);
            getTrip(trips, c.getTrip()).setTripTime(tc);
        }
        
        
        
        
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        
        for(TimeTupel tt: getStop(stops, startStop.getStop()).getTupels()){
            List<StopCSA> controllStops = new ArrayList<StopCSA>();
            StopHandlerPCSA reconstructionPoint = endStop;
            Journey journey = new Journey();
            Boolean circelBit = false;
            while(reconstructionPoint.getStopJP() != null){
                journey.addJourneyPointer(reconstructionPoint.getStopJP());
                if(controllStops.contains(reconstructionPoint.getStop())){
                    circelBit = true;
                } else{
                    controllStops.add(reconstructionPoint.getStop());
                }
                reconstructionPoint = getStop(stops, reconstructionPoint.getStopJP().getLeg().getEnter().getDepartureStop());
            }
            if(!circelBit){
                journeys.add(journey);
            }
        }
        
        return journeys;
        
    }
    
    public static TripHandlerPCSA getTrip(Set<TripHandlerPCSA> trips, TripCSA trip){
        for(TripHandlerPCSA th : trips){
            if(th.getTrip() == trip){
                return th;
            }
        }
        return null;
    }
    
    public static StopHandlerPCSA getStop(Set<StopHandlerPCSA> stops, StopCSA stop){
        for(StopHandlerPCSA sh : stops){
            if(sh.getStop() == stop){
                return sh;
            }
        }
        return null;
    }
    
    private static Set<TripHandlerPCSA> generateTripHandlers(Set<TripCSA> trips) {
        Set<TripHandlerPCSA> tripHandlers = new HashSet<TripHandlerPCSA>();
        for(TripCSA t : trips){
            TripHandlerPCSA th = new TripHandlerPCSA(t);
            tripHandlers.add(th);
        }
        return tripHandlers;
    }

    private static Set<StopHandlerPCSA> generateStopHandlers(Set<StopCSA> stops) {
        Set<StopHandlerPCSA> stopHandlers = new HashSet<StopHandlerPCSA>();
        for(StopCSA s : stops){
            StopHandlerPCSA sh = new StopHandlerPCSA(s);
            TimeTupel tt = new TimeTupel();
            sh.addTupelFront(tt);
            stopHandlers.add(sh);
        }
        return stopHandlers;
    }
    
    public static StopHandlerPCSA getStopFromCor (double lng, double lat, Set<StopHandlerPCSA> stops) {

        StopHandlerPCSA stop = null;

                Iterator<StopHandlerPCSA> it = stops.iterator();
                 
                while(it.hasNext()){
                        stop = (StopHandlerPCSA)it.next();
                        if(lng == stop.getStop().getLongitude()) {
                            if(lat == stop.getStop().getLatitude()){
                                return stop;
                            }
                                
                        }
                }

        return null;
    }

}
