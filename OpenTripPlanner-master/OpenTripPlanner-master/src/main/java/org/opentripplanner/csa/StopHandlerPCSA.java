package org.opentripplanner.csa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopHandlerPCSA {
    
    private StopCSA stop = new StopCSA();
    private List<TimeTupel> timeTupels = new ArrayList<TimeTupel>();
    private JourneyPointer stopJP = null;
    public StopHandlerPCSA(StopCSA stop){
        this.stop = stop;
        
    }
    public StopCSA getStop() {
        return stop;
    }
    public void setStop(StopCSA stop) {
        this.stop = stop;
    }
    
    public JourneyPointer getStopJP() {
        return stopJP;
    }
    public void setStopJP(JourneyPointer stopJP) {
        this.stopJP = stopJP;
    }
    public void addTupelFront(TimeTupel timeTupel){
        timeTupels.add(0,timeTupel);
    }
    public void replaceTupelFront(TimeTupel timeTupel){
        timeTupels.set(0,timeTupel);
    }
    
    public TimeTupel getTupel(int index){
        return timeTupels.get(index);
    }
    public List<TimeTupel> getTupels(){
        return timeTupels;
    }
}