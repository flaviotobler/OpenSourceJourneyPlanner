package org.opentripplanner.csa;

/**
 * This class holds the exception calendar for a trip
 * @author Christian
 *
 */
public class ServiceCalendarDateCSA {
	
	private String serviceCalendarDate;
	//in gtfs int added(1) or removed(2) trip
    private boolean serviceCalendarDateExceptionType; 

    
    private int exceptionYear;
    private int exceptionMonth;
    private int exceptionDay;
    
    
    /**
     * constructor for the SerciceCalendar.
     * @param serviceCalendarDate
     * @param serviceCalendarDateExceptionType
     */
    public ServiceCalendarDateCSA(String serviceCalendarDate, boolean serviceCalendarDateExceptionType) {
    	this.serviceCalendarDate = serviceCalendarDate;
    	this.serviceCalendarDateExceptionType = serviceCalendarDateExceptionType;
    	
    	this.exceptionYear = getYearFromGtfs(serviceCalendarDate);
    	this.exceptionMonth = getMonthFromGtfs(serviceCalendarDate);
    	this.exceptionDay = getDayFromGtfs(serviceCalendarDate);
    	
    }
    
    /**
     * returns the serviceCalendarDate 
     * @return serviceCalendarDate as String in YYYYMMDD format
     */
	public String getServiceCalendarDate() {
		return serviceCalendarDate;
	}

	/**
	 * sets the serviceCalendarDate
	 * @param serviceCalendarDate as String in YYYYMMDD format
	 */
	public void setServiceCalendarDate(String serviceCalendarDate) {
		this.serviceCalendarDate = serviceCalendarDate;
	}

	/**
	 * returns true if it is a added service of false if it is a removed service
	 * @return serviceCalendarDateExceptionType as boolean
	 */
	public boolean getServiceCalendarDateExceptionType() {
		return serviceCalendarDateExceptionType;
	}

	/**
	 * sets the serviceCalendarDateExceptionType true or false
	 * @param serviceCalendarDateExceptionType as boolean
	 */
	public void setServiceCalendarDateExceptionType(boolean serviceCalendarDateExceptionType) {
		this.serviceCalendarDateExceptionType = serviceCalendarDateExceptionType;
	}
	
	/**
	 * returns the year as Integer from a gtfs String
	 * @param serviceCalendarDate as String
	 * @return year as Integer
	 */
	public int getYearFromGtfs(String serviceCalendarDate) {
		
		String tempyear = serviceCalendarDate.substring(0, 4);

		int year = Integer.parseInt(tempyear);

		return year;
	}
	
	/**
	 * returns the month as Integer from gtfs String
	 * @param serviceCalendarDate as String
	 * @return month as Integer
	 */
	public int getMonthFromGtfs(String serviceCalendarDate) {
		
		String tempmonth = serviceCalendarDate.substring(4, 6);
		
		int month = Integer.parseInt(tempmonth);
	
		return month;
	}
	
	/**
	 * returns the day as Integer from gtfs String
	 * @param serviceCalendarDate as String
	 * @return day as Integer
	 */
	public int getDayFromGtfs(String serviceCalendarDate) {
		
		String tempday = serviceCalendarDate.substring(6, 8);
		
		int day = Integer.parseInt(tempday);

		return day;
	}

	/**
	 * returns the exceptionYear
	 * @return exceptionYear as Integer
	 */
	public int getExceptionYear() {
		return exceptionYear;
	}

	/**
	 * sets the exceptionYear
	 * @param exceptionYear as Integer
	 */
	public void setExceptionYear(int exceptionYear) {
		this.exceptionYear = exceptionYear;
	}
	
	/**
	 * returns the exceptionMonth
	 * @return exceptionMonth as Integer
	 */
	public int getExceptionMonth() {
		return exceptionMonth;
	}

	/**
	 * sets exceptionMonth
	 * @param exceptionMonth as Integer
	 */
	public void setExceptionMonth(int exceptionMonth) {
		this.exceptionMonth = exceptionMonth;
	}

	/**
	 * returns exceptionDay 
	 * @return exceptionDay as Integer
	 */
	public int getExceptionDay() {
		return exceptionDay;
	}

	/**
	 * sets the exceptionDay
	 * @param exceptionDay as Integer
	 */
	public void setExceptionDay(int exceptionDay) {
		this.exceptionDay = exceptionDay;
	}
	
	

}
