package org.opentripplanner.csa;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.opentripplanner.common.MavenVersion;

public class TimeTable implements Cloneable, Serializable{


    private static final long serialVersionUID = MavenVersion.VERSION.getUID();
    private Set<Stop> stops = new HashSet<Stop>();
    private Set<Trip> trips = new HashSet<Trip>();
    private Set<Footpath> footpaths = new HashSet<Footpath>();
    private Set<Connection> connections = new TreeSet<Connection>();
    
    
    
    public TimeTable(){}
    

    
    public void addStop(Stop stop){
        stops.add(stop);
    }
    
    public void addTrip(Trip trip){
        trips.add(trip);
    }
    
    public void addFootpaths(Footpath footpath){
        footpaths.add(footpath);
    }
    
    public void addConnection(Connection connection){
        connections.add(connection);
    }
    
    //Wird wahrscheinlich nicht gebraucht da normal darueber iteriert werden kann.
    /*public void sortConnections(){
        Set<Connection> con = new TreeSet<Connection>();
        Iterator<Connection> itr = connections.iterator();
        while (itr.hasNext()){
            con.add(itr.next());
        }
        connections = con;
        
    }*/
    
    
    
}
