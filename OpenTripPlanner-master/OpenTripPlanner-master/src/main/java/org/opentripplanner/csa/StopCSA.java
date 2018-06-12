package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.onebusaway.gtfs.model.AgencyAndId;

/**
 * This class implements the required stops for the Connection Scan Algorithm.
 * @author Christian
 *
 */
public class StopCSA {
    
    private String name;
    private double latitude;
    private double longitude;

    private AgencyAndId sAAId;
    
    
    
    
    
    
    /**
     * constructor for the stop.
     * @param name
     * @param latitude
     * @param longitude
     * @param sAAId
     */
    public StopCSA(String name, double latitude, double longitude, AgencyAndId sAAId)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sAAId = sAAId;
    }
    
    public StopCSA(AgencyAndId sAAId)  //constructor for Template Stop 
    {
        this.sAAId = sAAId;
    }
    
    
    public StopCSA(){
        
    }
    
    /**
     * returns the stopname
     * @return name as String
     */
    public String getName() {
        return name;
    }
    
    /**
     * sets the stopname
     * @param name as String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the latitude of the stop location
     * @return latitude as double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * sets the latitude of the stop
     * @param latitude as double
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * returns the longitude of the stop location
     * @return longitude as double
     */
    public double getLongitude() {
        return longitude;
    }

    /** 
     * sets the longitude of the stop
     * @param longitude as double
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * returns the agencyAndId object from the stop
     * @return sAAId as object
     */
	public AgencyAndId getsAAId() {
		return sAAId;
	}
	
	/**
	 * sets the agencyAndId object from the stop
	 * @param sAAId as object
	 */
	public void setsAAId(AgencyAndId sAAId) {
		this.sAAId = sAAId;
	}
	

}
