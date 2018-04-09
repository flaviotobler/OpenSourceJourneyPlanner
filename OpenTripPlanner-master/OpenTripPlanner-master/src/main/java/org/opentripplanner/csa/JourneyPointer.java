package org.opentripplanner.csa;

public class JourneyPointer {
    
    private Leg leg;
    private Footpath footpath;
    
    public JourneyPointer(){}

    public JourneyPointer(Leg leg, Footpath footpath) {
        this.leg = leg;
        this.footpath = footpath;
    }

    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    public Footpath getFootpath() {
        return footpath;
    }

    public void setFootpath(Footpath footpath) {
        this.footpath = footpath;
    };
    
    
    
    

}
