package org.opentripplanner.csa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.onebusaway.gtfs.model.AgencyAndId;


/**
 * This class implements the required trips for the Connection Scan Algorithm.
 * @author Christian
 *
 */
public class TripCSA {
    
    private String tripShortName;
    private String tripHeadSign;
    
    private String routeShortName;
    private String routeDesc;   
    private int routeType;
    private String mode;
    
    private String agencyName;
    private String agencyNameLong;
    private String agencyUrl;
    private String agencyTimeZoneOffset;
    
    private AgencyAndId tAAId; 
    private AgencyAndId rAAId;
    
    //Calendar Dates
    private String serviceId;
    ArrayList<ServiceCalendarCSA> serviceCalendars = new ArrayList<ServiceCalendarCSA>();
    ArrayList<ServiceCalendarDateCSA> serviceCalendarDates = new ArrayList<ServiceCalendarDateCSA>();
    
    //CSA attributes
    private ConnectionCSA tripEnterConnection = null;
    
    
    
    /**
     * constructor for the trip.	
     * @param tripShortName
     * @param tripHeadSign
     * @param routeShortName
     * @param routeDesc
     * @param routeType
     * @param agencyName
     * @param agencyNameLong
     * @param agencyUrl
     * @param agencyTimeZoneOffset
     * @param serviceId
     * @param tAAId
     * @param rAAId
     */
    public TripCSA(String tripShortName, String tripHeadSign, String routeShortName,String routeDesc, int routeType, String agencyName, String agencyNameLong, String agencyUrl, String agencyTimeZoneOffset, String serviceId, AgencyAndId tAAId, AgencyAndId rAAId) {
        this.tripShortName = tripShortName;
        this.tripHeadSign = tripHeadSign;
        
        this.routeShortName = routeShortName;
        this.routeDesc = routeDesc;
        this.routeType = routeType;
        this.mode = getModeFromRouteType(getRouteType());
        
        this.agencyName = agencyName;
        this.agencyNameLong = agencyNameLong;
        this.agencyUrl = agencyUrl;
        this.agencyTimeZoneOffset = agencyTimeZoneOffset;
        
        
        this.tAAId = tAAId;
        this.rAAId = rAAId;
        
        
        this.serviceId = serviceId;    
    }
    
    public TripCSA(String tripShortName, String tripHeadSign, String routeShortName,String routeDesc, int routeType, String mode, String agencyName, String agencyNameLong, String agencyUrl, String agencyTimeZoneOffset, AgencyAndId tAAId, AgencyAndId rAAId, String serviceId, boolean tripFlag, ConnectionCSA tripEnterConnection) {
        this.tripShortName = tripShortName;
        this.tripHeadSign = tripHeadSign;
        
        this.routeShortName = routeShortName;
        this.routeDesc = routeDesc;
        this.routeType = routeType;
        this.mode = mode;
        
        this.agencyName = agencyName;
        this.agencyNameLong = agencyNameLong;
        this.agencyUrl = agencyUrl;
        this.agencyTimeZoneOffset = agencyTimeZoneOffset;
        
        
        this.tAAId = tAAId;
        this.rAAId = rAAId;
        
        
        this.serviceId = serviceId;    
        
        this.tripEnterConnection = tripEnterConnection;
    }
    
    public TripCSA(AgencyAndId tAAId) {  //constructor for Template Trip 
        this.tAAId = tAAId;
    }
    
    public TripCSA() {
    	
    }
     
    
    /**
     * returns the tripShortName
     * @return tripShortName as String
     */
    public String getTripShortName() {
        return tripShortName;
    }

    /**
     * sets the tripName
     * @param tripShortName as String
     */
    public void setTripShortName(String tripShortName) {
    	this.tripShortName = tripShortName;
    }
    
    /**
     * returns the tripHeadSign
     * @return tripHeadSign as as String
     */
	public String getTripHeadSign() {
		return tripHeadSign;
	}
	
	/**
	 * sets the tripHeadsign
	 * @param tripHeadSign as String
	 */
	public void setTripHeadSign(String tripHeadSign) {
		this.tripHeadSign = tripHeadSign;
	}

	/**
	 * returns the routeType 
	 * @return routeType as Integer
	 */
	public int getRouteType() {
		return routeType;
	}

	/**
	 * sets the routeType
	 * @param routeType as Integer
	 */
	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	/**
	 * returns the agencyName
	 * @return agencyName as String
	 */
	public String getAgencyName() {
		return agencyName;
	}
	
	/**
	 * sets the agencyName
	 * @param agencyName as String
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * returns the agencyNameLong
	 * @return agencyNameLong as String
	 */
	public String getAgencyNameLong() {
		return agencyNameLong;
	}

	/**
	 * sets the agencyNameLong
	 * @param agencyNameLong as String
	 */
	public void setAgencyNameLong(String agencyNameLong) {
		this.agencyNameLong = agencyNameLong;
	}
	
	/**
	 * returns the agencyUrl
	 * @return agencyUrl as String
	 */
	public String getAgencyUrl() {
		return agencyUrl;
	}

	/**
	 * sets the agencyUrl
	 * @param agencyUrl as String
	 */
	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	/**
	 * returns the agencyTimeZoneOffset
	 * @return agencyTimeZoneOffset as String
	 */
	public String getAgencyTimeZoneOffset() {
		return agencyTimeZoneOffset;
	}

	/**
	 * sets the agencyTimeZoneOffset
	 * @param agencyTimeZoneOffset as String
	 */
	public void setAgencyTimeZoneOffset(String agencyTimeZoneOffset) {
		this.agencyTimeZoneOffset = agencyTimeZoneOffset;
	}

	/**
	 * returns the serviceId
	 * @return serviceId as String
	 */
	public String getServiceId() {
		return serviceId;
	}
	
	/**
	 * sets the serviceId
	 * @param serviceId as String
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	/**
	 * This methode checks if a trip is available on a specific Date (requestDate) as Calendar.
	 * and returns true or false.
	 * @param requestDate as Calendar object
	 * @return true if service is available and false if there is no service available
	 */
	public boolean checkTripAvailability(Calendar requestDate) { //month starts counting at 0 not a 1!
		int dayofweek = requestDate.get(Calendar.DAY_OF_WEEK);
        String dayname;
        switch (dayofweek) {
            case 1:  dayname = "Sunday";
                     break;
            case 2:  dayname = "Monday";
                     break;
            case 3:  dayname = "Tuesday";
                     break;
            case 4:  dayname = "Wednesday";
                     break;
            case 5:  dayname = "Thursday";
                     break;
            case 6:  dayname = "Friday";
                     break;
            case 7:  dayname = "Saturday";
                     break;
            
            default: dayname = "Invalid dayofweek!!!";
                     break;
        }
        
        for(ServiceCalendarDateCSA serviceCalendarDate : serviceCalendarDates) {
        	Calendar exceptionDate = new GregorianCalendar(serviceCalendarDate.getExceptionYear(), serviceCalendarDate.getExceptionMonth()-1, serviceCalendarDate.getExceptionDay());
        	if(exceptionDate.equals(requestDate)) {
        		boolean exceptiontype = serviceCalendarDate.getServiceCalendarDateExceptionType();
        		System.out.println("-->exception Tripplan  "+"TripID  "+gettAAId().getId()+"  ServiceId "+serviceId+"  TripAvailable?("+dayname+")?  "+exceptiontype);
        		return exceptiontype;
        	} 	
        }
		
		for(ServiceCalendarCSA serviceCalendar : serviceCalendars) {
			Calendar startDate = new GregorianCalendar(serviceCalendar.getStartDateYear(), serviceCalendar.getStartDateMonth()-1, serviceCalendar.getStartDateDay());
			Calendar endDate = new GregorianCalendar(serviceCalendar.getEndDateYear(), serviceCalendar.getEndDateMonth()-1, serviceCalendar.getEndDateDay());
			
			if(startDate.before(requestDate) == true || startDate.equals(requestDate)) {
				if(endDate.after(requestDate) == true || endDate.equals(requestDate)) {	
					boolean status = false;
			        switch (dayname) {
		            case "Sunday": 		status = serviceCalendar.isServiceCalendarSu();
		                     			break;
		            case "Monday":  	status = serviceCalendar.isServiceCalendarMo();
		            					break;
		            case "Tuesday":  	status = serviceCalendar.isServiceCalendarTu();
		                     			break;		
		            case "Wednesday":  	status = serviceCalendar.isServiceCalendarWe();
		            					break;
		            case "Thursday":  	status = serviceCalendar.isServiceCalendarTh();
		                     			break;
		            case "Friday":  	status = serviceCalendar.isServiceCalendarFr();
		            					break;
		            case "Saturday":  	status = serviceCalendar.isServiceCalendarSa();
		                     			break;
		            default: 			
		                     			break;
			        }
			        System.out.println("-->regular Tripplan  "+"TripID  "+gettAAId().getId()+"  ServiceId "+serviceId+"  TripAvailable?("+dayname+")?  "+status);
			        return status;		
				}		
	
			}
			
		}
		return false;
	}

	/** 
	 * returns the mode derived from routeType
	 * @return mode as String
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * sets the mode 
	 * @param mode as String
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * The methode converts the routeType into a String what it is.
	 * @param routeType as Integer
	 * @return String what it is (Rail,Bus,..)
	 */
	public String getModeFromRouteType(int routeType) {
        
        /* TPEG Extension  https://groups.google.com/d/msg/gtfs-changes/keT5rTPS7Y0/71uMz2l6ke0J */
        if (routeType >= 100 && routeType < 200){ // Railway Service
            return "RAIL";
        }else if (routeType >= 200 && routeType < 300){ //Coach Service
            return "BUS";
        }else if (routeType >= 300 && routeType < 500){ //Suburban Railway Service and Urban Railway service
            if (routeType >= 401 && routeType <= 402) {
                return "SUBWAY";
            }
            return "RAIL";
        }else if (routeType >= 500 && routeType < 700){ //Metro Service and Underground Service
            return "SUBWAY";
        }else if (routeType >= 700 && routeType < 900){ //Bus Service and Trolleybus service
            return "BUS";
        }else if (routeType >= 900 && routeType < 1000){ //Tram service
            return "TRAM";
        }else if (routeType >= 1000 && routeType < 1100){ //Water Transport Service
            return "FERRY";
        }else if (routeType >= 1100 && routeType < 1200){ //Air Service
            return "AIRPLANE";
        }else if (routeType >= 1200 && routeType < 1300){ //Ferry Service
            return "FERRY";
        }else if (routeType >= 1300 && routeType < 1400){ //Telecabin Service
            return "GONDOLA";
        }else if (routeType >= 1400 && routeType < 1500){ //Funicalar Service
            return "FUNICULAR";
        }else if (routeType >= 1500 && routeType < 1600){ //Taxi Service. Rerouted to TraverseMode.Car to ignore them.
            return "CAR";
        }else if (routeType >= 1600 && routeType < 1700){ //Self drive
            return "CAR";
        }else if (routeType >= 1700 && routeType < 1800){ //Some Cable-Cars and Car Transport Trains. Rerouted to TraverseMode.Car to ignore them.
        	return "CAR";
        }
        /* Original GTFS route types. Should these be checked before TPEG types? */
        switch (routeType) {
        case 0:
            return "TRAM";
        case 1:
            return "SUBWAY";
        case 2:
            return "RAIL";
        case 3:
            return "BUS";
        case 4:
            return "FERRY";
        case 5:
            return "CABLE_CAR";
        case 6:
            return "GONDOLA";
        case 7:
            return "FUNICULAR";
        default:
            throw new IllegalArgumentException("unknown gtfs route type " + routeType);
        }
    }

	/**
	 * returns the routeShortName 
	 * @return routeShortName as String
	 */
	public String getRouteShortName() {
		return routeShortName;
	}

	/**
	 * sets the routeShortName 
	 * @param routeShortName as String
	 */
	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	/**
	 * returns the routeDescription from the gtfs
	 * @return routeDesc as String
	 */
	public String getRouteDesc() {
		return routeDesc;
	}

	/**
	 * sets the routeDescription
	 * @param routeDesc
	 */
	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	/**
	 * returns the agencyAndId object from the trip
	 * @return tAAId as object
	 */
	public AgencyAndId gettAAId() {
		return tAAId;
	}
	
	/**
	 * sets the agencyAndId object from the trip
	 * @param tAAId as object
	 */
	public void settAAId(AgencyAndId tAAId) {
		this.tAAId = tAAId;
	}

	/** 
	 * returns the agencyAndId object from the route
	 * @return rAAId as object
	 */
	public AgencyAndId getrAAId() {
		return rAAId;
	}

	/**
	 * sets the agencyAndId object from the route
	 * @param rAAId as object
	 */
	public void setrAAId(AgencyAndId rAAId) {
		this.rAAId = rAAId;
	}


	public ConnectionCSA getTripEnterConnection() {
		return tripEnterConnection;
	}


	public void setTripEnterConnection(ConnectionCSA tripEnterConnection) {
		this.tripEnterConnection = tripEnterConnection;
	}



}
