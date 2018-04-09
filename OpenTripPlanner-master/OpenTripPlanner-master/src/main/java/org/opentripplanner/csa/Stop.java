package org.opentripplanner.csa;

public class Stop {
    
    private String name;
    private double xCord;
    private double yCord;
    
    public Stop(){};
    
    public Stop(String name, double xCord, double yCord)
    {
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
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
