package org.opentripplanner.csa;

public class LegCSA {
    
    private Connection enter;
    private Connection exit;
    
    public LegCSA(){}

    public LegCSA(Connection enter, Connection exit) {
        this.enter = enter;
        this.exit = exit;
    }

    public Connection getEnter() {
        return enter;
    }

    public void setEnter(Connection enter) {
        this.enter = enter;
    }

    public Connection getExit() {
        return exit;
    }

    public void setExit(Connection exit) {
        this.exit = exit;
    };
    
    
    
    

}
