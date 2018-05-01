package org.opentripplanner.csa;

public class TripCSA {
    
    private String tripId;
    private String tripShortName;
    private String tripHeadSign;
    
    private String routeId;
    private int routeType;
    
    private String agencyId;
    private String agencyName;
    private String agencyNameLong;
    private String agencyUrl;
    private String agencyTimeZoneOffset;
    
    

    public TripCSA(String tripId, String tripShortName, String tripHeadSign, String routeId, int routeType, String agencyId, String agencyName, String agencyNameLong, String agencyUrl, String agencyTimeZoneOffset) {
        this.tripId = tripId;
        this.tripShortName = tripShortName;
        this.tripHeadSign = tripHeadSign;
        
        this.routeId = routeId;
        this.routeType = routeType;
        
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyNameLong = agencyNameLong;
        this.agencyUrl = agencyUrl;
        this.agencyTimeZoneOffset = agencyTimeZoneOffset;
        
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

}
