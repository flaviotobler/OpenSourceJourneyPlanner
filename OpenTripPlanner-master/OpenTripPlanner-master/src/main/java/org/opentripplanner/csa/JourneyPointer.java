package org.opentripplanner.csa;

public class JourneyPointer {
    
    private LegCSA leg;
    private FootpathCSA footpath;
    
    public JourneyPointer(){}

    public JourneyPointer(LegCSA leg, FootpathCSA footpath) {
        this.leg = leg;
        this.footpath = footpath;
    }

    public LegCSA getLeg() {
        return leg;
    }

    public void setLeg(LegCSA leg) {
        this.leg = leg;
    }

    public FootpathCSA getFootpath() {
        return footpath;
    }

    public void setFootpath(FootpathCSA footpath) {
        this.footpath = footpath;
    };
    
    
    
    

}
