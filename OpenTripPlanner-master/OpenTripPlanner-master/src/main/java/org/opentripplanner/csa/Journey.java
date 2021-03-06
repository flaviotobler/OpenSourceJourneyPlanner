package org.opentripplanner.csa;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The CSA returns the found results in form of Journeys. They exist of a StartFootpath and a set of JourneyPointers.
 * @author Flavio
 */
public class Journey implements Cloneable{
    
    private FootpathCSA startPath;
    private ArrayList<JourneyPointer> journeyPointers = new ArrayList<JourneyPointer>();
    
    public Journey(){}
    
    /**
     * Constructor for the Journey with a startPath
     * @param startPath
     */
    public Journey(FootpathCSA startPath){
        this.startPath = startPath;
    }
    
    /**
     * Returns the StartPath
     * @return startPAth
     */
    public FootpathCSA getStartPath() {
        return startPath;
    }
    
    /**
     * Sets the StartPath
     * @param startPath
     */
    public void setStartPath(FootpathCSA startPath) {
        this.startPath = startPath;
    }
    
    /**
     * Adds a JourneyPointer to the set.
     * @param journeyPointer
     */
    public void addJourneyPointer(JourneyPointer journeyPointer){
        journeyPointers.add(0,journeyPointer);
    }
    
    /**
     * Returns the Set of JourneyPointers
     * @return
     */
    public ArrayList<JourneyPointer> getJourneyPointers(){
        return journeyPointers;
    }
    
    /**
     * Adds a JourneyPointer in the front of the JourneyPointer-List
     * @param jp
     */
    public void addJourneyPointerFront(JourneyPointer jp){
        journeyPointers.add(0,jp);
    }
    
    /**
     * Adds a JourneyPointer at a specific Index of the JourneyPointer-List
     * @param index
     * @param jp
     */
    public void addJourneyPointerIndex(int index, JourneyPointer jp){
        journeyPointers.add(index,jp);
    }
    
    /**
     * Clone function to clone the object for the Jackson2 serializiation 
     */
    public Object clone()throws CloneNotSupportedException{  
        return (Journey)super.clone();  
   }
}
