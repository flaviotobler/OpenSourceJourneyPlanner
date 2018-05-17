package org.opentripplanner.csa;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opentripplanner.routing.core.RoutingRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CSA_EAC {
    
    public static Set<Journey> createJourneys(TimeTable timeTable, RoutingRequest request) throws JsonGenerationException, JsonMappingException, IOException{
        
        int year = request.getDateTime().getYear() + 1900;
        int month = request.getDateTime().getMonth() + 1;
        int day = request.getDateTime().getDate();
        
        StopCSA startStop = timeTable.getStop(request.from.name);
        StopCSA endStop = timeTable.getStop(request.to.name);
        Calendar startTime = new GregorianCalendar(year,month,day,request.getDateTime().getHours(),request.getDateTime().getMinutes());
        startStop.setStopTime(startTime);
        
        // Todo startpunkt für schleife finden
        for(ConnectionCSA c : TimeTable.getConnectionsAscending())
        {
            Calendar departureTime = new GregorianCalendar(year,month,day,c.gethDepartureTime(),c.getMinDepartureTime());
            Calendar arrivalTime = new GregorianCalendar(year,month,day,c.gethArrivalTime(),c.getMinArrivalTime());
            if(endStop.getStopTime().before(departureTime)){
                break;
            }
            if(c.getTrip().getTripFlag() || c.getDepartureStop().getStopTime().before(departureTime)){
                if(!c.getTrip().getTripFlag()){
                    Calendar changeTestTime = c.getDepartureStop().getStopTime();
                    changeTestTime.SECOND = changeTestTime.SECOND + timeTable.getFootpathChange(c.getDepartureStop()).getDuration;
                    if(changeTestTime.before(departureTime)){
                        c.getTrip().setTripFlag(true);
                        c.getTrip().setTripEnterConnection(c);
                        if(arrivalTime.before(c.getArrivalStop().getStopTime())){
                            c.getArrivalStop().setStopTime(arrivalTime);
                            c.getArrivalStop().setStopJP(new JourneyPointer(new LegCSA(c.getTrip().getTripEnterConnection(),c),timeTable.getFootpathChange(c.getArrivalStop()).getDuration));
                        }
                    }
                }
                else{
                    if(arrivalTime.before(c.getArrivalStop().getStopTime())){
                        c.getArrivalStop().setStopTime(arrivalTime);
                        c.getArrivalStop().setStopJP(new JourneyPointer(new LegCSA(c.getTrip().getTripEnterConnection(),c),timeTable.getFootpathChange(c.getArrivalStop()).getDuration));
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
        journey.setStartPath(timeTable.getFootpathChange(startStop));
        return journeys;
        
    }
}
