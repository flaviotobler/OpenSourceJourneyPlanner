package org.opentripplanner.csa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class holds all the information together for the  Connection Scan Algorithm.
 * @author Christian
 *
 */
public class TimeTable {

    private Set<StopCSA> stops = new HashSet<StopCSA>();
    private Set<TripCSA> trips = new HashSet<TripCSA>();
    private Set<FootpathCSA> footpaths = new HashSet<FootpathCSA>();
    private Set<ConnectionCSA> connections = new TreeSet<ConnectionCSA>();
    
    /**
     * constructor for the TimeTable
     */
    public TimeTable(){}
    
    /**
     * adds a Stop to the stops
     * @param stop as object
     */
    public void addStop(StopCSA stop){
        stops.add(stop);
    }
    
    /**
     * adds a trip to the trips
     * @param trip as object
     */
    public void addTrip(TripCSA trip){
        trips.add(trip);
    }
    
    /**
     * adds a footpath to the footpaths
     * @param footpath as object
     */
    public void addFootpaths(FootpathCSA footpath){
        footpaths.add(footpath);
    }
    
    /**
     * adds a connection to the connections
     * @param connection as object
     */
    public void addConnection(ConnectionCSA connection){
        connections.add(connection);
    }
    
    /**
     * shows all the stops and and the size of the collection
     */
    public void showStops() {
    	System.out.println("STOPs: "+stops.size()+"--> "+stops);
    }
    
    /**
     * shows all the trips and and the size of the collection
     */
    public void showTrips() {
    	System.out.println("TRIPs: "+trips.size()+"--> "+trips);
    }
    
    /**
     * shows all the footpaths and and the size of the collection
     */
    public void showFootPaths() {
    	System.out.println("FOOTPATHs: "+footpaths.size()+"--> "+footpaths);
    }
    
    /**
     * shows all the connections and and the size of the collection
     */
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
