package org.opentripplanner.csa;

public class ServiceCalendarDateCSA {
	
	private String serviceCalendarDate;
	//in gtfs int added(1) or removed(2) trip
    private boolean serviceCalendarDateExceptionType;  // true=added und false=removed
    
    
    public ServiceCalendarDateCSA(String serviceCalendarDate, boolean serviceCalendarDateExceptionType) {
    	this.serviceCalendarDate = serviceCalendarDate;
    	this.serviceCalendarDateExceptionType = serviceCalendarDateExceptionType;
    	
    }

	public String getServiceCalendarDate() {
		return serviceCalendarDate;
	}

	public void setServiceCalendarDate(String serviceCalendarDate) {
		this.serviceCalendarDate = serviceCalendarDate;
	}

	public boolean getServiceCalendarDateExceptionType() {
		return serviceCalendarDateExceptionType;
	}

	public void setServiceCalendarDateExceptionType(boolean serviceCalendarDateExceptionType) {
		this.serviceCalendarDateExceptionType = serviceCalendarDateExceptionType;
	}
	
	

}
