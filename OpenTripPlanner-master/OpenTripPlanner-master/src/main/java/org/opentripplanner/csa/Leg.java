package org.opentripplanner.csa;

public class Leg {
    
    private Connection enter;
    private Connection exit;
    
    public Leg(){}

    public Leg(Connection enter, Connection exit) {
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
