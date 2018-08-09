package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;


/**
 * Stop-Handler class for the EAC. It provides changeable parameters so the TimeTable has not to be changed.
 * @author Flavio
 *
 */
public class StopHandlerCSA {
    
    private StopCSA stop = new StopCSA();
    private Calendar stopTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private JourneyPointer stopJP = null;
    
    /**
     * Constructor to create a new StopHandler-Object
     * @param stop
     */
    public StopHandlerCSA(StopCSA stop){
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
     * 
     * @return StopTime of the StopHandler
     */
    public Calendar getStopTime() {
        return stopTime;
    }
    
    /**
     * Sets the StopTime of the StopHandler
     * @param stopTime
     */
    public void setStopTime(Calendar stopTime) {
        this.stopTime = stopTime;
    }
    
    /**
     * 
     * @return JourneyPointer of the StopHandler
     */
    public JourneyPointer getStopJP() {
        return stopJP;
    }
    
    /**
     * Sets the JourneyPointer of the StopHandler
     * @param stopJP
     */
    public void setStopJP(JourneyPointer stopJP) {
        this.stopJP = stopJP;
    }
}
