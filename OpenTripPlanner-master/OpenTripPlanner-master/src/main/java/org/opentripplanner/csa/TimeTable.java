package org.opentripplanner.csa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
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
    private static Set<ConnectionCSA> connectionsAscending = new LinkedHashSet<ConnectionCSA>();
    
    private static Set<ConnectionCSA> connectionsDescending = new LinkedHashSet<ConnectionCSA>();
    
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
    public void addConnectionsAscending(Set<ConnectionCSA> connections){
    	connectionsAscending.addAll(connections);
    }
    
    public void addConnectionsDescending(Set<ConnectionCSA> connections){
        connectionsDescending.addAll(connections);
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
    	System.out.println("CONNECTIONS: "+connectionsAscending.size()+"--> "+connectionsAscending);
    }
    
    
    
    
    public FootpathCSA getFootPathChange(StopCSA stop) {
    	
    	FootpathCSA footpath = null;
    	
		Iterator<FootpathCSA> it = footpaths.iterator();
		 
		while(it.hasNext()){
			footpath = (FootpathCSA)it.next();
			
			if(footpath.getDepartureStop().equals(stop)) {
				if(footpath.getArrivalStop().equals(stop)) {
					return footpath;
				
				}
			return null;
			}
		}

    	return null;
    }
    
    public static Set<ConnectionCSA> getConnectionsAscending() {
    	return connectionsAscending;
    }
    
    public static Set<ConnectionCSA> getConnectionsDescending() {
    	return connectionsDescending;
    }
    
    
    
    public void showCon(Set<ConnectionCSA> connections) {
    	
    	Iterator<ConnectionCSA> it = connections.iterator();
		 
		while(it.hasNext())
		{
			ConnectionCSA printcon = (ConnectionCSA) it.next();
		      System.out.println("DepStop   "+printcon.getDepartureStop().getName()+"   DepartureTime "+printcon.gethDepartureTime()+":"+printcon.getMinDepartureTime()+":"+printcon.getsDepartureTime()+"  CON "+printcon);
		}
		
    	/*Iterator<ConnectionCSA> it2 = ((TreeSet<ConnectionCSA>) connections).descendingIterator();
		 
		while(it2.hasNext())
		{
			ConnectionCSA zzzz = (ConnectionCSA) it2.next();
		      System.out.println("DepStop   "+zzzz.getDepartureStop().getName()+"   DepartureTime "+zzzz.gethDepartureTime()+":"+zzzz.getMinDepartureTime()+":"+zzzz.getsDepartureTime()+"  CON "+zzzz);
		}*/
    	
    	
    	
    }

    public Set<StopCSA> getStops() {
        return stops;
    }

    public Set<TripCSA> getTrips() {
        return trips;
    }

    public Set<FootpathCSA> getFootpaths() {
        return footpaths;
    }

    public void setConnectionsAscending(Set<ConnectionCSA> connectionsAscending) {
        this.connectionsAscending = connectionsAscending;
        
    }
    
    public void setConnectionsDescending(Set<ConnectionCSA> connectionsDescending) {
        this.connectionsDescending = connectionsDescending;
        
    }
    
    public void setStops(Set<StopCSA> stops) {
        this.stops = stops;
        
    }
    
    public void setTrips(Set<TripCSA> trips) {
        this.trips = trips;
        
    }
    
    public void setFootpaths(Set<FootpathCSA> footpaths) {
        this.footpaths = footpaths;
        
    }
    
    
    
}
