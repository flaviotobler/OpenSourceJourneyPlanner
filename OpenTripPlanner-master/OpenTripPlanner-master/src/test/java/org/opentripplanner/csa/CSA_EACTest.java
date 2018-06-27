package org.opentripplanner.csa;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Before;
import org.opentripplanner.api.model.Place;
import org.opentripplanner.common.model.GenericLocation;
import org.opentripplanner.routing.core.RoutingRequest;
import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


class CSA_EACTest {
    TimeTable timeTable = new TimeTable();
    TimeTable timeTableK = new TimeTable();
    RoutingRequest request = null;
    Set<Journey> assertionJourneys = new LinkedHashSet<Journey>();
    
    @Before
    static void initAll(){
        timeTable = getTimeTableFromText();
    }
    
    @Test
    void unsteigetest(){
        
    }
    
    @Test
    void tageswechseltest(){
        
    }
    
    @BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
    @Test
    void performanceTest0(){
        
        timeTableK = timeTable.clone();
        Date date = new Date();
        date.setDate(1529899500);
        request.setDateTime(date);
        GenericLocation from = new GenericLocation();
        from.name = "stop Schaan Bahnhof";
        from.lat = 47.1682614512578;
        from.lng = 9.50862236796454;
        from.place = "47.1682614512578,9.50862236796454";
        request.from = from;
        GenericLocation to = new GenericLocation();
        to.name = "stop Schellenberg Post";
        to.lat = 47.2320552940269;
        to.lng = 9.54675585178072;
        to.place = "47.2320552940269,9.54675585178072";
        request.to = to;
        
        
        assertionJourneys = CSA_EAC.createJourneys(timeTable, request);
    }
    
    @BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
    @Test
    void performanceTest1(){
        
    }
    
    @BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
    @Test
    void performanceTest2(){
        
    }
}
