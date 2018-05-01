package org.opentripplanner.csa;

public class StopCSA {
    
	private String stopId;
    private String name;
    private double latitude;
    private double longitude;
    
    
    
    public StopCSA(String stopId,String name, double latitude, double longitude)
    {
    	this.stopId = stopId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getStopId() {
    	return stopId;
    }
    
    public void setStopId(String stopId) {
    	this.stopId = stopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    
    

}
