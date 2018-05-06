package org.opentripplanner.csa;

import java.util.LinkedHashSet;
import java.util.Set;

public class Journey {
    
    private FootpathCSA startPath;
    private Set<JourneyPointer> journeyPointers = new LinkedHashSet<JourneyPointer>();
    
    public Journey(){}
    
    public Journey(FootpathCSA startPath){
        this.startPath = startPath;
    }

    public FootpathCSA getStartPath() {
        return startPath;
    }

    public void setStartPath(FootpathCSA startPath) {
        this.startPath = startPath;
    }
    
    public void addJourneyPointer(JourneyPointer journeyPointer){
        journeyPointers.add(journeyPointer);
    }
    
    public Set<JourneyPointer> getJourneyPointers(){
        return journeyPointers;
    }
}
