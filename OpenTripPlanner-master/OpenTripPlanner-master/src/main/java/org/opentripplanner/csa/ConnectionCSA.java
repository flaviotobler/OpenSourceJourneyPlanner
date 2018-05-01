package org.opentripplanner.csa;


public class ConnectionCSA implements Comparable { //Treeset braucht Comparable sonst exception  //add type?vielleicht?
    private StopCSA departureStop;
    private StopCSA arrivalStop;
    
    private int gtfsDepartureTime;
    private int gtfsArrivalTime;
    
    private int hDepartureTime;
    private int minDepartureTime;
    private int sDepartureTime;
    
    private int hArrivalTime;
    private int minArrivalTime;
    private int sArrivalTime;
    
    private TripCSA trip;
    
    

    public ConnectionCSA(StopCSA departureStop, StopCSA arrivalStop, int gtfsDepartureTime,
    		int gtfsArrivalTime, TripCSA trip) {
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        
        this.gtfsDepartureTime = gtfsDepartureTime;
        gtfsDepartureTimeConvertToHmS(this.gtfsDepartureTime);
        
        this.gtfsArrivalTime = gtfsArrivalTime;
        gtfsArrivalTimeConvertToHmS(this.gtfsArrivalTime);
   
        this.trip = trip;
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

	public int getGtfsDepartureTime() {
		return gtfsDepartureTime;
	}

	public void setGtfsDepartureTime(int gtfsDepartureTime) {
		this.gtfsDepartureTime = gtfsDepartureTime;
	}

	public int getGtfsArrivalTime() {
		return gtfsArrivalTime;
	}

	public void setGtfsArrivalTime(int gtfsArrivalTime) {
		this.gtfsArrivalTime = gtfsArrivalTime;
	}

	public int gethDepartureTime() {
		return hDepartureTime;
	}

	public void sethDepartureTime(int hDepartureTime) {
		this.hDepartureTime = hDepartureTime;
	}

	public int getsDepartureTime() {
		return sDepartureTime;
	}

	public void setsDepartureTime(int sDepartureTime) {
		this.sDepartureTime = sDepartureTime;
	}

	public int getMinDepartureTime() {
		return minDepartureTime;
	}

	public void setMinDepartureTime(int minDepartureTime) {
		this.minDepartureTime = minDepartureTime;
	}

	public int gethArrivalTime() {
		return hArrivalTime;
	}

	public void sethArrivalTime(int hArrivalTime) {
		this.hArrivalTime = hArrivalTime;
	}

	public int getsArrivalTime() {
		return sArrivalTime;
	}

	public void setsArrivalTime(int sArrivalTime) {
		this.sArrivalTime = sArrivalTime;
	}

	public int getMinArrivalTime() {
		return minArrivalTime;
	}

	public void setMinArrivalTime(int minArrivalTime) {
		this.minArrivalTime = minArrivalTime;
	}
	
	
    public TripCSA getTrip() {
        return trip;
    }

    public void setTrip(TripCSA trip) {
        this.trip = trip;
    }
    
    
    public void gtfsDepartureTimeConvertToHmS(int gtfsDepartureTime){
        int hours = (int)(gtfsDepartureTime / (60 * 60) % 24);
        int minutes = (int)(gtfsDepartureTime / (60) % 60);
        int seconds = (int)(gtfsDepartureTime % 60);
       
        System.out.println("DepartureTime: " +hours+":"+ minutes+":"+seconds);
        
        this.hDepartureTime = hours;
        this.minDepartureTime = minutes;
        this.sDepartureTime = seconds;
    }
    
    public void gtfsArrivalTimeConvertToHmS(int gtfsArrivalTime){
        int hours = (int)(gtfsArrivalTime / (60 * 60) % 24);
        int minutes = (int)(gtfsArrivalTime / (60) % 60);
        int seconds = (int)(gtfsArrivalTime % 60);

        System.out.println("ArrivalTime: " +hours+":"+ minutes+":"+seconds);
        
        this.hArrivalTime = hours;
        this.minArrivalTime = minutes;
        this.sArrivalTime = seconds;

    }
    

	@Override
	public int compareTo(Object arg0) {   //TreeSet sortieren  nach ...
		// TODO Auto-generated method stub
		
		/*
		return 0; dublikat wird nicht aufgenommen
		return 1; für in Reihenfolge ein begin mit erstem 1-2-3-4-5....
		return-1; Reihenfolge begin mit letztem 15-14-13-12...
		
		
		
		*/
		
		return 1;
	}


}
