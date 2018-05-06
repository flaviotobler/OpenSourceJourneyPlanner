package org.opentripplanner.csa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class TimeTable {

    private Set<StopCSA> stops = new HashSet<StopCSA>();
    private Set<TripCSA> trips = new HashSet<TripCSA>();
    private Set<FootpathCSA> footpaths = new HashSet<FootpathCSA>();
    private Set<ConnectionCSA> connections = new TreeSet<ConnectionCSA>();
    
    public TimeTable(){}
    
    public void addStop(StopCSA stop){
        stops.add(stop);
    }
    
    public void addTrip(TripCSA trip){
        trips.add(trip);
    }
    
    public void addFootpaths(FootpathCSA footpath){
        footpaths.add(footpath);
    }
    
    public void addConnection(ConnectionCSA connection){
        connections.add(connection);
    }
    
    public void showStops() {
    	System.out.println("STOPs: "+stops.size()+"--> "+stops);
    }
    public void showTrips() {
    	System.out.println("TRIPs: "+trips.size()+"--> "+trips);
    }
    public void showFootPaths() {
    	System.out.println("FOOTPATHs: "+footpaths.size()+"--> "+footpaths);
    }
    public void showConnections() {
    	System.out.println("CONNECTIONS: "+connections.size()+"--> "+connections);
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
