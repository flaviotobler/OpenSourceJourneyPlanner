package org.opentripplanner.csa;

import java.util.LinkedHashSet;
import java.util.Set;

public class Journey {
    
    private Footpath startPath;
    private Set<JourneyPointer> journeyPointers = new LinkedHashSet<JourneyPointer>();
    
    public Journey(){}
    
    public Journey(Footpath startPath){
        this.startPath = startPath;
    }

    public Footpath getStartPath() {
        return startPath;
    }

    public void setStartPath(Footpath startPath) {
        this.startPath = startPath;
    }
    
    public void addJourneyPointer(JourneyPointer journeyPointer){
        journeyPointers.add(journeyPointer);
    }
}
