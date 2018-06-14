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
    public StopHandlerPCSA(StopCSA stop){
        this.stop = stop;
        
    }
    public StopCSA getStop() {
        return stop;
    }
    public void setStop(StopCSA stop) {
        this.stop = stop;
    }
    
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
    public void replaceTupelFront(TimeTupel timeTupel){
        timeTupels.set(0,timeTupel);
    }
    
    public TimeTupel getTupel(int index){
        System.out.println(timeTupels.get(index).getArrivalTime().getTimeInMillis());
        return timeTupels.get(index);
    }
    public List<TimeTupel> getTupels(){
        return timeTupels;
    }
    
}