package org.opentripplanner.csa;

import java.util.LinkedHashSet;
import java.util.Set;

import org.opentripplanner.routing.core.RoutingRequest;

class CSA_EACTest {
    TimeTable timeTable = new TimeTable();
    RoutingRequest request = null;
    Set<Journey> assertionJourneys = new LinkedHashSet<Journey>();
    
    @BeforeAll
    static void initAll(){
        timeTable = getTimeTableFromText();
    }
    
    @Test
    void unsteigetest(){
        
    }
    
    @Test
    @Disabled
    void tageswechseltest(){
        
    }
    
    @Test
    void performanceTest1(){
        
    }
    
    @Test
    void performanceTest2(){
        
    }
    
    @Test
    void performanceTest3(){
        
    }
}
