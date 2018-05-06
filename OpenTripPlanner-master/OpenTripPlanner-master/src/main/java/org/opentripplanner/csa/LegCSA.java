package org.opentripplanner.csa;

public class LegCSA {
    
    private ConnectionCSA enter;
    private ConnectionCSA exit;
    
    public LegCSA(){}

    public LegCSA(ConnectionCSA enter, ConnectionCSA exit) {
        this.enter = enter;
        this.exit = exit;
    }

    public ConnectionCSA getEnter() {
        return enter;
    }

    public void setEnter(ConnectionCSA enter) {
        this.enter = enter;
    }

    public ConnectionCSA getExit() {
        return exit;
    }

    public void setExit(ConnectionCSA exit) {
        this.exit = exit;
    };
    
    
    
    

}
