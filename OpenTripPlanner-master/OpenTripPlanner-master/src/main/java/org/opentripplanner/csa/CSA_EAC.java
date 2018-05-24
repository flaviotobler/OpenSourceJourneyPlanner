package org.opentripplanner.csa;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opentripplanner.routing.core.RoutingRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CSA_EAC {
    
    public static Set<Journey> createJourneys(TimeTable timeTable, RoutingRequest request) throws JsonGenerationException, JsonMappingException, IOException{
        
        Date dateTime = request.getDateTime();
        Date tempTime = dateTime;
        int year = request.getDateTime().getYear() + 1900;
        int month = request.getDateTime().getMonth();
        int day = request.getDateTime().getDate();
        System.out.println(year + " " + month + " " + day);
        System.out.println(request.from.name);
        StopCSA startStop = timeTable.getStopFromCor(request.from.lng,request.from.lat);
        StopCSA endStop = timeTable.getStopFromCor(request.to.lng,request.to.lat);
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
            if(c.getTrip().getTripFlag() || c.getDepartureStop().getStopTime().before(departureTime)){
                if(!c.getTrip().getTripFlag()){
                    
                    tempTime.setHours(c.getDepartureStop().getStopTime().get(Calendar.HOUR));
                    tempTime.setMinutes(c.getDepartureStop().getStopTime().get(Calendar.MINUTE));
                    Calendar changeTestTime = new GregorianCalendar();
                    changeTestTime.setTime(tempTime);
                    changeTestTime.add(Calendar.SECOND, (int) timeTable.getFootPathChange(c.getDepartureStop()).getDuration());
                    if(changeTestTime.before(departureTime)){
                        c.getTrip().setTripFlag(true);
                        c.getTrip().setTripEnterConnection(c);
                        if(arrivalTime.before(c.getArrivalStop().getStopTime())){
                            c.getArrivalStop().setStopTime(arrivalTime);
                            c.getArrivalStop().setStopJP(new JourneyPointer(new LegCSA(c.getTrip().getTripEnterConnection(),c),timeTable.getFootPathChange(c.getArrivalStop())));
                        }
                    }
                }
                else{
                    if(arrivalTime.before(c.getArrivalStop().getStopTime())){
                        c.getArrivalStop().setStopTime(arrivalTime);
                        c.getArrivalStop().setStopJP(new JourneyPointer(new LegCSA(c.getTrip().getTripEnterConnection(),c),timeTable.getFootPathChange(c.getArrivalStop())));
                    }
                }
            }
        }
        StopCSA reconstructionPoint = endStop;
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        Journey journey = new Journey();
        while(reconstructionPoint.getStopJP() != null){
            journey.addJourneyPointer(reconstructionPoint.getStopJP()); // LinkedHaschSet ändern da vorne eingefügt werden muss
            reconstructionPoint = reconstructionPoint.getStopJP().getLeg().getEnter().getDepartureStop();
        }
        journey.setStartPath(timeTable.getFootPathChange(startStop));
        return journeys;
        
    }
}
