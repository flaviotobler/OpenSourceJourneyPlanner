package org.opentripplanner.csa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Stop-Handler class for the PCS. It provides changeable parameters so the TimeTable has not to be changed.
 * @author Flavio
 *
 */
public class StopHandlerPCSA {
    
    private StopCSA stop = new StopCSA();
    private List<TimeTupel> timeTupels = new ArrayList<TimeTupel>();
    
    /**
     * Constructor to create a new StopHandler-Object
     * @param stop
     */
    public StopHandlerPCSA(StopCSA stop){
        this.stop = stop;
        
    }
    
    /**
     * 
     * @return to stop which belongs to the StopHandler
     */
    public StopCSA getStop() {
        return stop;
    }
    /**
     * Sets the stop which is referenced in the StopHandeler
     * @param stop
     */
    public void setStop(StopCSA stop) {
        this.stop = stop;
    }
    
    /**
     * Adds a TimeTupel in the front of the TimeTupel-List
     * @param timeTupel
     */
    public void addTupelFront(TimeTupel timeTupel){
        TimeTupel ttupel = new TimeTupel();
        ttupel.setArrivalTime(timeTupel.getArrivalTime());
        ttupel.setDepartureTime(timeTupel.getDepartureTime());
        ttupel.setJourneyPointer(timeTupel.getJourneyPointer());
        
        timeTupels.add(0,ttupel);
        System.out.println(timeTupel.getArrivalTime().getTimeInMillis());
        System.out.println(ttupel.getArrivalTime().getTimeInMillis());
        System.out.println(this.timeTupels.get(0).getArrivalTime().getTimeInMillis());
    }
    /**
     * Replaces the TimeTupel at the first index of the TimeTupel-List
     * @param timeTupel
     */
    public void replaceTupelFront(TimeTupel timeTupel){
        timeTupels.set(0,timeTupel);
    }
    
    /**
     * 
     * @param index
     * @return a specific TimeTupel from the TimeTupel-List
     */
    public TimeTupel getTupel(int index){
        System.out.println(timeTupels.get(index).getArrivalTime().getTimeInMillis());
        return timeTupels.get(index);
    }
    /**
     * 
     * @return the TimeTupel-List
     */
    public List<TimeTupel> getTupels(){
        return timeTupels;
    }
    
}