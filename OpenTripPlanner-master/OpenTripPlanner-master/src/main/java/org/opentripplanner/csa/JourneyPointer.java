package org.opentripplanner.csa;

public class JourneyPointer {
    
    private LegCSA leg;
    private Footpath footpath;
    
    public JourneyPointer(){}

    public JourneyPointer(LegCSA leg, Footpath footpath) {
        this.leg = leg;
        this.footpath = footpath;
    }

    public LegCSA getLeg() {
        return leg;
    }

    public void setLeg(LegCSA leg) {
        this.leg = leg;
    }

    public Footpath getFootpath() {
        return footpath;
    }

    public void setFootpath(Footpath footpath) {
        this.footpath = footpath;
    };
    
    
    
    

}
