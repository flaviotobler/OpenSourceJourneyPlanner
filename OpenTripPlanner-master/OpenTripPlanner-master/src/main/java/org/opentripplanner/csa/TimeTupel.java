package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTupel implements Cloneable{
    private Calendar departureTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private Calendar arrivalTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private JourneyPointer jp = new JourneyPointer();
    
    
    public TimeTupel(){
        
    }
    
    public Calendar getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(Calendar departureTime) {
        this.departureTime = departureTime;
    }
    public Calendar getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(Calendar arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public JourneyPointer getJourneyPointer(){
        return jp;
    }
    public void setJourneyPointer(JourneyPointer jp){
        this.jp = jp;
    }
    public Object clone()throws CloneNotSupportedException{  
        return (TimeTupel)super.clone();  
   }
}
