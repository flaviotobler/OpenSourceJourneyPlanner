package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class implements the required TimeTupel-Object for the Connection Scan Algorithm.
 * @author Christian
 *
 */
public class TimeTupel implements Cloneable{
    private Calendar departureTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private Calendar arrivalTime = new GregorianCalendar(20000,12 ,31,23,59,59);
    private JourneyPointer jp = new JourneyPointer();
    
    
    public TimeTupel(){
        
    }
    
    /**
     * 
     * @return the departureTime
     */
    public Calendar getDepartureTime() {
        return departureTime;
    }
    
    /**
     * Sets the departureTime
     * @param departureTime
     */
    public void setDepartureTime(Calendar departureTime) {
        this.departureTime = departureTime;
    }
    
    /**
     * 
     * @return the arrivalTime
     */
    public Calendar getArrivalTime() {
        return arrivalTime;
    }
    
    /**
     * Sets the arrivalTime
     * @param arrivalTime
     */
    public void setArrivalTime(Calendar arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    /**
     * 
     * @return the JourneyPointer
     */
    public JourneyPointer getJourneyPointer(){
        return jp;
    }
    
    /**
     * Sets the JourneyPointer
     * @param jp
     */
    public void setJourneyPointer(JourneyPointer jp){
        this.jp = jp;
    }
    public Object clone()throws CloneNotSupportedException{  
        return (TimeTupel)super.clone();  
   }
    
    /**
     * increases of the Day of the ArrivalTime by 1
     */
    public void addArrivalTimeDay(){
        arrivalTime.add(Calendar.DATE, 1);
    }
    
    /**
     * increases of the Day of the DepartureTime by 1
     */
    public void addDepartureTimeDay(){
        departureTime.add(Calendar.DATE, 1);
    }
}
