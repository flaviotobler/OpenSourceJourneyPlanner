package org.opentripplanner.csa;

/**
 * Represents a Leg with the following Footpath to change Legs.
 * @author flavi
 */
public class JourneyPointer {
    
    private LegCSA leg;
    private FootpathCSA footpath;
    
    public JourneyPointer(){}

    /**
     * Constructs a new JourneyPointer with a leg and a footpath
     * @param leg
     * @param footpath
     */
    public JourneyPointer(LegCSA leg, FootpathCSA footpath) {
        this.leg = leg;
        this.footpath = footpath;
    }

    /**
     * Returns the Leg
     * @return leg
     */
    public LegCSA getLeg() {
        return leg;
    }

    /**
     * Sets the Leg
     * @param leg
     */
    public void setLeg(LegCSA leg) {
        this.leg = leg;
    }

    /**
     * Returns the Footpath
     * @return footpath
     */
    public FootpathCSA getFootpath() {
        return footpath;
    }

    /**
     * Sets the Footpath
     * @param footpath
     */
    public void setFootpath(FootpathCSA footpath) {
        this.footpath = footpath;
    };
    
    
    
    

}
