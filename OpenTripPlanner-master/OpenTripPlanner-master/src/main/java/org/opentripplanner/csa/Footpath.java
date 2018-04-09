package org.opentripplanner.csa;

public class Footpath {
    
    private Stop departureStop;
    private Stop arrivalStop;
    private long duration;
    
    public Footpath(){};
    
    public Footpath(Stop departureStop, Stop arrivalStop, long duration) 
    {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.duration = duration;
    }

    public Stop getDepartureStop() {
        return departureStop;
    }

    public void setDepartureStop(Stop departureStop) {
        this.departureStop = departureStop;
    }

    public Stop getArrivalStop() {
        return arrivalStop;
    }

    public void setArrivalStop(Stop arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    
    
    

}
