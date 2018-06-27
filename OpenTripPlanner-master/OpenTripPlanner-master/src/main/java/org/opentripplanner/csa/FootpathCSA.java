package org.opentripplanner.csa;

/**
 * This class implements the required footpaths for the Connection Scan Algorithm.
 * @author Christian
 *
 */
public class FootpathCSA implements java.io.Serializable {
    
    private StopCSA departureStop;
    private StopCSA arrivalStop;
    private long duration; 
    
   
    /**
     * constructor for the footpath.
     * @param departureStop
     * @param arrivalStop
     * @param duration
     */
    public FootpathCSA(StopCSA departureStop, StopCSA arrivalStop, long duration) 
    {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.duration = duration;
    }
    
    public FootpathCSA(){
        
    }

    /**
     * returns the departureStop
     * @return departureStop as object
     */
    public StopCSA getDepartureStop() {
        return departureStop;
    }

    /**
     * sets the departureStop
     * @param departureStop as object
     */
    public void setDepartureStop(StopCSA departureStop) {
        this.departureStop = departureStop;
    }

    /**
     * returns the arrivalStop
     * @return arrivalStop as object
     */
    public StopCSA getArrivalStop() {
        return arrivalStop;
    }
    
    /**
     * sets the arrivalStop
     * @param arrivalStop as object
     */
    public void setArrivalStop(StopCSA arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    /** 
     * returns the duration of the footpath 
     * @return duration as long in seconds
     */
    public long getDuration() {
        return duration;
    }

    /**
     * sets the duration of the footpath
     * @param duration as long in seconds
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    
    
    

}
