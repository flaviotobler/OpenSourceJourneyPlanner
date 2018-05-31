package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class StopHandlerCSA {
    
    private StopCSA stop = new StopCSA();
    private Calendar stopTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private JourneyPointer stopJP = null;
    public StopHandlerCSA(StopCSA stop){
        this.stop = stop;
        
    }
    public StopCSA getStop() {
        return stop;
    }
    public void setStop(StopCSA stop) {
        this.stop = stop;
    }
    public Calendar getStopTime() {
        return stopTime;
    }
    public void setStopTime(Calendar stopTime) {
        this.stopTime = stopTime;
    }
    public JourneyPointer getStopJP() {
        return stopJP;
    }
    public void setStopJP(JourneyPointer stopJP) {
        this.stopJP = stopJP;
    }
}
