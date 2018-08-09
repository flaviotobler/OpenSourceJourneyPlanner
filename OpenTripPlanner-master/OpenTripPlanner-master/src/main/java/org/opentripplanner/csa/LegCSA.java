package org.opentripplanner.csa;


/**
 * This class implements the leg-object which is part of a Journey
 * @author Flavio
 *
 */
public class LegCSA {
    
    private ConnectionCSA enter;
    private ConnectionCSA exit;
    
    /**
     * Constructor to generate an empty Leg
     */
    public LegCSA(){}
    
    /**
     * Constructor to generate a new Leg
     * @param enter
     * @param exit
     */
    public LegCSA(ConnectionCSA enter, ConnectionCSA exit) {
        this.enter = enter;
        this.exit = exit;
    }

    /**
     *  
     * @return the Enter-Connection of the Leg
     */
    public ConnectionCSA getEnter() {
        return enter;
    }

    /**
     * sets the Enter-Connection of the Leg
     * @param enter
     */
    public void setEnter(ConnectionCSA enter) {
        this.enter = enter;
    }

    /**
     * 
     * @return the Exit-Connection of the Leg
     */
    public ConnectionCSA getExit() {
        return exit;
    }

    /**
     * sets the Exit-Connection of the Leg
     * @param exit
     */
    public void setExit(ConnectionCSA exit) {
        this.exit = exit;
    };
    
    
    
    

}
