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
public class TimeTable implements java.io.Serializable, Cloneable {

    private Set<StopCSA> stops = new HashSet<StopCSA>();
    private Set<TripCSA> trips = new HashSet<TripCSA>();
    private Set<FootpathCSA> footpaths = new HashSet<FootpathCSA>();
    
    private static Set<ConnectionCSA> connectionsAscending = new LinkedHashSet<ConnectionCSA>();
    private static Set<ConnectionCSA> connectionsDescending = new LinkedHashSet<ConnectionCSA>();
    
    private Set<ConnectionCSA> connectionsAscendingNonStatic = new LinkedHashSet<ConnectionCSA>();
    private Set<ConnectionCSA> connectionsDescendingNonStatic = new LinkedHashSet<ConnectionCSA>();
    
    
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
    
    
    
    /**
     * 
     * @param stop
     * @return the footpath of the Stop
     */
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
    
    /**
     * The next four functions return different Set-Configurations for the Connections
     */
    
    public static Set<ConnectionCSA> getConnectionsAscending() {
    	return connectionsAscending;
    }
    
    public static Set<ConnectionCSA> getConnectionsDescending() {
    	return connectionsDescending;
    }
    
    public Set<ConnectionCSA> getConnectionsAscendingNotStatic() {
    	return connectionsAscendingNonStatic;
    }
    
    public Set<ConnectionCSA> getConnectionsDescendingNotStatic() {
    	return connectionsDescendingNonStatic;
    }
    
    /**
     * shows the Connections in the Connection Set
     * @param connections
     */
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
    
    
    /**
     * 
     * @return the Set of Stops
     */
    public Set<StopCSA> getStops() {
        return stops;
    }

    /**
     * 
     * @return the Set of Trips
     */
    public Set<TripCSA> getTrips() {
        return trips;
    }

    /**
     * 
     * @return the Set of Footpaths
     */
    public Set<FootpathCSA> getFootpaths() {
        return footpaths;
    }

    /**
     * Sets the Set of ascending Connections
     * @param connectionsAscending
     */
    public void setConnectionsAscending(Set<ConnectionCSA> connectionsAscending) {
        this.connectionsAscending = connectionsAscending;
        
    }
    
    /**
     * Sets the Set of descending Connections
     * @param connectionsDescending
     */
    public void setConnectionsDescending(Set<ConnectionCSA> connectionsDescending) {
        this.connectionsDescending = connectionsDescending;
        
    }
    
    /**
     * Sets the Set of nonStatic ascending Connections
     * @param connectionsAscendingNonStatic
     */
    public void setConnectionsAscendingNonStatic(Set<ConnectionCSA> connectionsAscendingNonStatic) {
        this.connectionsAscendingNonStatic = connectionsAscendingNonStatic;
        
    }
    
    /**
     * Sets the Set of nonStatic descending Connections
     * @param connectionsDescendingNonStatic
     */
    public void setConnectionsDescendingNonStatic(Set<ConnectionCSA> connectionsDescendingNonStatic) {
        this.connectionsDescendingNonStatic = connectionsDescendingNonStatic;
        
    }
    
    /**
     * Sets the Set of Stops
     * @param stops
     */
    public void setStops(Set<StopCSA> stops) {
        this.stops = stops;
        
    }
    
    /**
     * Sets the Set of Trips
     * @param trips
     */
    public void setTrips(Set<TripCSA> trips) {
        this.trips = trips;
        
    }
    
    /**
     *Sets the Set of Footpaths 
     * @param footpaths
     */
    public void setFootpaths(Set<FootpathCSA> footpaths) {
        this.footpaths = footpaths;
        
    }
    
    public Object clone()throws CloneNotSupportedException{
    	return (TimeTable)super.clone();
    }
    
    
    
}
