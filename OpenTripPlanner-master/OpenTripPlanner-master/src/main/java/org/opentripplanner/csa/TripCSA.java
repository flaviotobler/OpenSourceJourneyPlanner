package org.opentripplanner.csa;

import java.util.ArrayList;

public class TripCSA {
    
    private String tripId;
    private String tripShortName;
    private String tripHeadSign;
    
    private String routeId;
    private String routeShortName;
    private String routeDesc;   //routeDesc selbe wie mode?  //gtfs optional
    private int routeType;
    private String mode;
    
    private String agencyId;
    private String agencyName;
    private String agencyNameLong;
    private String agencyUrl;
    private String agencyTimeZoneOffset;
    
    //Calendar Daten
    private String serviceId;
    ArrayList<ServiceCalendarCSA> serviceCalendars = new ArrayList<ServiceCalendarCSA>();
    ArrayList<ServiceCalendarDateCSA> serviceCalendarDates = new ArrayList<ServiceCalendarDateCSA>();
    
    public TripCSA(String tripId, String tripShortName, String tripHeadSign, String routeId,String routeShortName,String routeDesc, int routeType, String agencyId, String agencyName, String agencyNameLong, String agencyUrl, String agencyTimeZoneOffset, String serviceId) {
        this.tripId = tripId;
        this.tripShortName = tripShortName;
        this.tripHeadSign = tripHeadSign;
        
        this.routeId = routeId;
        this.routeShortName = routeShortName;
        this.routeDesc = routeDesc;
        this.routeType = routeType;
        this.mode = getModeFromRouteType(getRouteType());
        
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyNameLong = agencyNameLong;
        this.agencyUrl = agencyUrl;
        this.agencyTimeZoneOffset = agencyTimeZoneOffset;
        
        this.serviceId = serviceId;
        
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripShortName;
    }

    public void setTripName(String tripShortName) {
    	this.tripShortName = tripShortName;
    }

	public String getTripHeadSign() {
		return tripHeadSign;
	}

	public void setTripHeadSign(String tripHeadSign) {
		this.tripHeadSign = tripHeadSign;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyNameLong() {
		return agencyNameLong;
	}

	public void setAgencyNameLong(String agencyNameLong) {
		this.agencyNameLong = agencyNameLong;
	}
	
	public String getAgencyUrl() {
		return agencyUrl;
	}

	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	public String getAgencyTimeZoneOffset() {
		return agencyTimeZoneOffset;
	}

	public void setAgencyTimeZoneOffset(String agencyTimeZoneOffset) {
		this.agencyTimeZoneOffset = agencyTimeZoneOffset;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public boolean checkTripAvailability(String date) {
		//TODO
		
		return false;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
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

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}



}
