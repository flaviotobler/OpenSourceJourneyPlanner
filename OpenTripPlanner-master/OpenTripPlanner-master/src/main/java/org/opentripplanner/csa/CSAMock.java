package org.opentripplanner.csa;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CSAMock {
    
    public Set<Journey> createJourneys(TimeTable timeTable) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("timeTableMockJSON.txt"),timeTable);
        
        
        Stop testStop = new Stop("Heerbrugg Dornacherhof", 9.63119748849971, 47.4142968347706);
        Stop testStop2 = new Stop("Heerbrugg Bahnhof", 9.62778389041958, 47.4108621837542);
        Trip testTrip = new Trip(1, "DerBus");
        Calendar startZeit = new GregorianCalendar(2018,04,02,12,37);
        Calendar stopZeit = new GregorianCalendar(2018,04,02,12,42);
        Connection testConnection = new Connection(testStop, testStop2, startZeit, stopZeit, testTrip);
        Footpath testFootpath = new Footpath(testStop, testStop, 3);
        Footpath testFootpath2 = new Footpath(testStop2, testStop2, 5);
        Leg testLeg = new Leg(testConnection, testConnection);
        JourneyPointer testJourneyPointer = new JourneyPointer(testLeg, testFootpath2);
        Journey testJourney = new Journey(testFootpath);
        testJourney.addJourneyPointer(testJourneyPointer);
        Set<Journey> journeys = new LinkedHashSet<Journey>();
        journeys.add(testJourney);
        return journeys;
    }
}
