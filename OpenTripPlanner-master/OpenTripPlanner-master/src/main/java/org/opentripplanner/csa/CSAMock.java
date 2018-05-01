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
        
        
        StopCSA testStop = new StopCSA("Heerbrugg Dornacherhof", 9.63119748849971, 47.4142968347706);
        StopCSA testStop2 = new StopCSA("Heerbrugg Bahnhof", 9.62778389041958, 47.4108621837542);
        testStop2.setName("Heerbrugg Bahnhof");
        testStop2.setName
        TripCSA testTrip = new TripCSA();
        testTrip.setTripName("DerBus");
        testTrip.setTripId("1");
        Calendar startZeit = new GregorianCalendar(2018,04,02,12,37);
        Calendar stopZeit = new GregorianCalendar(2018,04,02,12,42);
        Connection testConnection = new Connection(testStop, testStop2, startZeit, stopZeit, testTrip);
        Footpath testFootpath = new Footpath(testStop, testStop, 3);
        Footpath testFootpath2 = new Footpath(testStop2, testStop2, 5);
        LegCSA testLeg = new LegCSA(testConnection, testConnection);
        JourneyPointer testJourneyPointer = new JourneyPointer(testLeg, testFootpath2);
        Journey testJourney = new Journey(testFootpath);
        testJourney.addJourneyPointer(testJourneyPointer);
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        journeys.add(testJourney);
        return journeys;
    }
}
