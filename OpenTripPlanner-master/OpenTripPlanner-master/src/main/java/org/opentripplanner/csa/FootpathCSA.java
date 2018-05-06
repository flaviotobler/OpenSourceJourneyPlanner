package org.opentripplanner.csa;

public class FootpathCSA {
    
    private StopCSA departureStop;
    private StopCSA arrivalStop;
    private long duration;  //sekunden? --> Minuten
    
   
    
    public FootpathCSA(StopCSA departureStop, StopCSA arrivalStop, long duration) 
    {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.duration = duration;
    }

    public StopCSA getDepartureStop() {
        return departureStop;
    }

    public void setDepartureStop(StopCSA departureStop) {
        this.departureStop = departureStop;
    }

    public StopCSA getArrivalStop() {
        return arrivalStop;
    }

    public void setArrivalStop(StopCSA arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    
    
    

}
