package org.opentripplanner.csa;

public class StopCSA {
    
	private String stopId;
    private String name;
    private double xCord;
    private double yCord;
    
    //public Stop(){};
    
    public StopCSA(String stopId,String name, double xCord, double yCord)
    {
    	this.stopId = stopId;
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
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

    public double getXCord() {
        return xCord;
    }

    public void setXCord(double xCord) {
        this.xCord = xCord;
    }

    public double getYCord() {
        return yCord;
    }

    public void setYCord(double yCord) {
        this.yCord = yCord;
    }
    
    
    

}
