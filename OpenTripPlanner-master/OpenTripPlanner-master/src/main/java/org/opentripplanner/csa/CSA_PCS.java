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

/**
 * This class is the implementation of the Profile Connection Scan Algorithm. 
 * To switch between the different algorithms you need to adjust the code in the class "PlannerResource"
 * in the Package "org.opentripplanner.api.resource".
 * @author Flavio Tobler
 *
 */

import org.opentripplanner.routing.core.RoutingRequest;

public class CSA_PCS {
    
	/**
	 * implementation of the Profile Connection Scan Algorithm.
	 * @param timeTable
	 * @param request
	 * @return a list of the journey which go from the start- to the endpoint
	 */
    public static Set<Journey> createJourneys(TimeTable timeTable, RoutingRequest request){
        
        Date dateTime = request.getDateTime();
        Date tempTime = dateTime;
        Set<StopHandlerPCSA> stops = generateStopHandlers(timeTable.getStops());
        Set<TripHandlerPCSA> trips = generateTripHandlers(timeTable.getTrips());
        StopHandlerPCSA startStop = getStopFromCor(request.from.lng,request.from.lat,stops);
        StopHandlerPCSA endStop = getStopFromCor(request.to.lng,request.to.lat,stops);
        Boolean dayChangeBit = false;
        int year = request.getDateTime().getYear() + 1900;
        int month = request.getDateTime().getMonth();
        int day = request.getDateTime().getDate();
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(request.getDateTime().getHours());
        System.out.println(request.getDateTime().getMinutes());
        Calendar t1 = new GregorianCalendar(20000,12 ,31,23,59,59);
        Calendar t2 = new GregorianCalendar(20000,12 ,31,23,59,59);
        Calendar t3 = new GregorianCalendar(20000,12 ,31,23,59,59);
        Calendar tc = new GregorianCalendar(20000,12 ,31,23,59,59);
        System.out.println("Timezone tc:" + tc.getTimeZone());
        Calendar tconst = new GregorianCalendar(20000,12 ,31,23,59,59);
        System.out.println(tconst.getTimeInMillis());
        Calendar startingTime = new GregorianCalendar();
        startingTime.setTime(request.getDateTime());
        CSA_EAC_Time cTime = new CSA_EAC_Time();
        Calendar frAnZeit = new GregorianCalendar(20000,12 ,31,23,59,59);
        frAnZeit = cTime.getEarliestArrivalTime(timeTable, request);
        frAnZeit.add(Calendar.HOUR, 2);
        
        
        for(ConnectionCSA c: timeTable.getConnectionsDescending()){
            tempTime.setHours(c.gethDepartureTime());
            tempTime.setMinutes(c.getMinDepartureTime());
            Calendar departureTime = new GregorianCalendar();
            departureTime.setTime(tempTime);
            tempTime.setHours(c.gethArrivalTime());
            tempTime.setMinutes(c.getMinArrivalTime());
            Calendar arrivalTime = new GregorianCalendar();
            arrivalTime.setTime(tempTime);
            
            if(departureTime.before(startingTime)){
                break;
            }
            if(departureTime.before(frAnZeit))
            {
            
            
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
                    t1.set(Calendar.MONTH, 12);
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
                    StopHandlerPCSA test = getStop(stops, c.getArrivalStop());
                    p = test.getTupel(index);
                }
                t3 = p.getArrivalTime();
                
                tc.setTimeInMillis(Math.min(t1.getTimeInMillis(), Math.min(t2.getTimeInMillis(), t3.getTimeInMillis())));
                Calendar tTime = departureTime;
                tTime.add(Calendar.SECOND, ((int) timeTable.getFootPathChange(c.getDepartureStop()).getDuration())*(-1));
                TripHandlerPCSA tempTrip = getTrip(trips, c.getTrip());
                
                TimeTupel q = new TimeTupel();
                q.setArrivalTime((Calendar)tc.clone());   
                q.setDepartureTime(tTime);
                
                //TimeTupel q = getStop(stops, c.getDepartureStop()).getTupel(0);
                try {
                    if(q.getArrivalTime().before(tconst))
                    {
                        if(tempTrip.getTripExitConnection() == null){
                            tempTrip.setTripExitConnection((ConnectionCSA)c.clone());
                        }
                        if(dayChangeBit && q.getArrivalTime().after(q.getDepartureTime())){
                            q.addArrivalTimeDay();
                            q.addDepartureTimeDay();
                            day = day + 1;
                        }
                        if(q.getArrivalTime().before(q.getDepartureTime()))
                        {
                            q.addArrivalTimeDay();
                            dayChangeBit = true;
                        }
                        
                            q.setJourneyPointer(new JourneyPointer(new LegCSA(c,tempTrip.getTripExitConnection()),timeTable.getFootPathChange(tempTrip.getTripExitConnection().getArrivalStop())));
                            getStop(stops, c.getDepartureStop()).addTupelFront((TimeTupel)q.clone());
                            getTrip(trips, c.getTrip()).setTripTime((Calendar)tc.clone());    
                    }
                }
                catch (CloneNotSupportedException e){
                    e.printStackTrace();
                }
            }
                
        }
        
        
        
        
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        
        for(TimeTupel tt: getStop(stops, startStop.getStop()).getTupels()){
            Boolean circelBit = false;
            Journey journey = new Journey(timeTable.getFootPathChange(startStop.getStop()));
            TimeTupel ttx = new TimeTupel();
            List<StopCSA> controllStops = new ArrayList<StopCSA>();
            try{
                controllStops.add((StopCSA)startStop.getStop().clone());
                journey.addJourneyPointer((JourneyPointer)tt.getJourneyPointer().clone());
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            if(tt.getJourneyPointer().getLeg() != null)
            {
                StopHandlerPCSA reconstructionPoint = getStop(stops, tt.getJourneyPointer().getLeg().getExit().getArrivalStop());
                Calendar reconstructionTime = new GregorianCalendar();
                reconstructionTime.setTime(request.getDateTime());
                reconstructionTime.set(Calendar.YEAR, year);
                reconstructionTime.set(Calendar.MONTH, month);
                reconstructionTime.set(Calendar.DATE, day);
                reconstructionTime.set(Calendar.HOUR, tt.getJourneyPointer().getLeg().getExit().gethArrivalTime());
                reconstructionTime.set(Calendar.MINUTE, tt.getJourneyPointer().getLeg().getExit().getMinArrivalTime());
                reconstructionTime.set(Calendar.SECOND, tt.getJourneyPointer().getLeg().getExit().getsArrivalTime());
                reconstructionTime.add(Calendar.SECOND, (int)timeTable.getFootPathChange(reconstructionPoint.getStop()).getDuration()); 
                        
                
                while(reconstructionPoint != endStop){
                    tupelfound:
                    for(TimeTupel tt2: reconstructionPoint.getTupels()){
                        if(tt2.getDepartureTime().after(reconstructionTime)){
                            ttx = tt2;
                            break tupelfound;
                        }
                    }
                    try{
                        journey.addJourneyPointer((JourneyPointer)ttx.getJourneyPointer().clone());
                    }catch (CloneNotSupportedException e){
                        e.printStackTrace();
                    }
                    if(controllStops.contains(reconstructionPoint.getStop())){
                        circelBit = true;
                    } else{
                        controllStops.add(reconstructionPoint.getStop());
                    }
                    reconstructionPoint = getStop(stops, ttx.getJourneyPointer().getLeg().getExit().getArrivalStop());
                    reconstructionTime.set(Calendar.HOUR, ttx.getJourneyPointer().getLeg().getExit().gethArrivalTime());
                    reconstructionTime.set(Calendar.MINUTE, ttx.getJourneyPointer().getLeg().getExit().getMinArrivalTime());
                    reconstructionTime.set(Calendar.SECOND, ttx.getJourneyPointer().getLeg().getExit().getsArrivalTime());
                    reconstructionTime.add(Calendar.SECOND, (int)timeTable.getFootPathChange(reconstructionPoint.getStop()).getDuration()); 
                }
                if(!circelBit){
                    int in = 0;
                    int size = journey.getJourneyPointers().size() - 1;
                    System.out.println(size);
                    JourneyPointer switchpointer = new JourneyPointer();
                    while(size >= in){
                        try{
                            switchpointer = (JourneyPointer)journey.getJourneyPointers().get(size).clone();
                            journey.getJourneyPointers().set(size, (JourneyPointer)journey.getJourneyPointers().get(in).clone());
                            journey.getJourneyPointers().set(in, (JourneyPointer)switchpointer.clone());
                        }
                        catch (CloneNotSupportedException e){
                            e.printStackTrace();
                        }
                        size--;
                        in++;
                    }
                    try{
                        journeys.add((Journey)journey.clone());
                    }catch (CloneNotSupportedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return journeys;     
    }
    
    /**
     * Finds a specific TripHandler
     * @param trip
     * @param trips
     * @return the found TripHandler or null if none is found
     */
    public static TripHandlerPCSA getTrip(Set<TripHandlerPCSA> trips, TripCSA trip){
        for(TripHandlerPCSA th : trips){
            if(th.getTrip() == trip){
                return th;
            }
        }
        return null;
    }
    
    /**
     * Finds a specific StopHandler
     * @param stop
     * @param stops
     * @return the found StopHandler or null if none is found.
     */
    public static StopHandlerPCSA getStop(Set<StopHandlerPCSA> stops, StopCSA stop){
        for(StopHandlerPCSA sh : stops){
            if(sh.getStop() == stop){
                return sh;
            }
        }
        return null;
    }
    
    /**
     * Generates a TripHandler for each Trip in the TimeTable
     * @param trips
     * @return a list of the generated TripHandler
     */
    private static Set<TripHandlerPCSA> generateTripHandlers(Set<TripCSA> trips) {
        Set<TripHandlerPCSA> tripHandlers = new HashSet<TripHandlerPCSA>();
        for(TripCSA t : trips){
            TripHandlerPCSA th = new TripHandlerPCSA(t);
            tripHandlers.add(th);
        }
        return tripHandlers;
    }

    /**
     * Generates a StopHandler for each Stop in the TimeTable
     * @param stops
     * @return a list of the generated TripHandler
     */
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
    
    /**
     * 
	 * finds a stop based on his coordinates
     * @param lng
     * @param lat
     * @param stops
     * @return the found Stop or null if none is found
     */
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
