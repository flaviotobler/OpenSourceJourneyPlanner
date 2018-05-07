package org.opentripplanner.csa;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opentripplanner.routing.core.RoutingRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CSAMock {
    
    public static Set<Journey> createJourneys(TimeTable timeTable, RoutingRequest request) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.writeValue(new File("timeTableMockJSON.txt"),timeTable);
        
        
        StopCSA testStop = new StopCSA("8578433", "Heerbrugg Dornacherhof", 9.63119748849971, 47.4142968347706);
        StopCSA testStop2 = new StopCSA("8574137", "Heerbrugg Bahnhof", 9.62778389041958, 47.4108621837542);

        TripCSA testTrip = new TripCSA("64.TA.16-304-j18-1.3.R","14065","Heerbrugg, Bahnhof","16-304-j18-1","304"," ",700,"1","Bus Ostschweiz (Rheintal)","Bus Ostschweiz (Rheintal)","http://www.sbb.ch/", "Europe/Berlin", "");
        int startZeit = 12*3600+37*60;
        int stopZeit = 12*3600+42*60;
        ConnectionCSA testConnection = new ConnectionCSA(testStop, testStop2, startZeit, stopZeit, testTrip);
        FootpathCSA testFootpath = new FootpathCSA(testStop, testStop, 3);
        FootpathCSA testFootpath2 = new FootpathCSA(testStop2, testStop2, 5);
        LegCSA testLeg = new LegCSA(testConnection, testConnection);
        JourneyPointer testJourneyPointer = new JourneyPointer(testLeg, testFootpath2);
        Journey testJourney = new Journey(testFootpath);
        testJourney.addJourneyPointer(testJourneyPointer);
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        journeys.add(testJourney);
        return journeys;
    }
}
