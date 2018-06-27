package org.opentripplanner.csa;

/**
 * This class holds the general calendar for a trip
 * @author Christian
 *
 */
public class ServiceCalendarCSA implements java.io.Serializable {
	
    private String serviceCalendarStartDate;
    private int startDateYear;
    private int startDateMonth;
    private int startDateDay;
    
    private boolean serviceCalendarMo;
    private boolean serviceCalendarTu;
    private boolean serviceCalendarWe;
    private boolean serviceCalendarTh;
    private boolean serviceCalendarFr;
    private boolean serviceCalendarSa;
    private boolean serviceCalendarSu;
    
    private String serviceCalendarEndDate; 
    private int endDateYear;
    private int endDateMonth;
    private int endDateDay;
    
    /**
     * constructor for the SerciceCalendar.
     * @param serviceCalendarStartDate
     * @param serviceCalendarMo
     * @param serviceCalendarTu
     * @param serviceCalendarWe
     * @param serviceCalendarTh
     * @param serviceCalendarFr
     * @param serviceCalendarSa
     * @param serviceCalendarSu
     * @param serviceCalendarEndDate
     */
    public ServiceCalendarCSA(String serviceCalendarStartDate, boolean serviceCalendarMo, boolean serviceCalendarTu, boolean serviceCalendarWe,boolean serviceCalendarTh,
    		boolean serviceCalendarFr,boolean serviceCalendarSa, boolean serviceCalendarSu,String serviceCalendarEndDate ){
    	
    	this.serviceCalendarStartDate = serviceCalendarStartDate;
    	this.startDateYear = getYearFromGtfs(serviceCalendarStartDate);
    	this.startDateMonth = getMonthFromGtfs(serviceCalendarStartDate);
    	this.startDateDay = getDayFromGtfs(serviceCalendarStartDate);
    	
    	this.serviceCalendarMo = serviceCalendarMo;
    	this.serviceCalendarTu = serviceCalendarTu;
    	this.serviceCalendarWe = serviceCalendarWe;
    	this.serviceCalendarTh = serviceCalendarTh;
    	this.serviceCalendarFr = serviceCalendarFr;
    	this.serviceCalendarSa = serviceCalendarSa;
    	this.serviceCalendarSu = serviceCalendarSu;
    	
    	this.serviceCalendarEndDate = serviceCalendarEndDate;
    	this.endDateYear = getYearFromGtfs(serviceCalendarEndDate);
    	this.endDateMonth = getMonthFromGtfs(serviceCalendarEndDate);
    	this.endDateDay = getDayFromGtfs(serviceCalendarEndDate);
    	
    }

    /**
     * return the serviceCalendarStartDate from gtfs
     * @return serviceCalendarStartDate as String in format YYYYMMDD
     */
	public String getServiceCalendarStartDate() {
		return serviceCalendarStartDate;
	}

	/**
	 * sets the serviceCalendarStartDate 
	 * @param serviceCalendarStartDate as String in format YYYYMMDD
	 */
	public void setServiceCalendarStartDate(String serviceCalendarStartDate) {
		this.serviceCalendarStartDate = serviceCalendarStartDate;
	}

	/**
	 * returns true if the service is available on Monday
	 * @return serciceCalendarMo as boolean
	 */
	public boolean isServiceCalendarMo() {
		return serviceCalendarMo;
	}

	/**
	 * sets the service true or false on Monday
	 * @param serviceCalendarMo as boolean
	 */
	public void setServiceCalendarMo(boolean serviceCalendarMo) {
		this.serviceCalendarMo = serviceCalendarMo;
	}
	
	/**
	 * returns true if the service is available on Tuesday
	 * @return serviceCalendarTu as boolean
	 */
	public boolean isServiceCalendarTu() {
		return serviceCalendarTu;
	}

	/**
	 * sets the service true or false on Tuesday
	 * @param serviceCalendarTu as boolean
	 */
	public void setServiceCalendarTu(boolean serviceCalendarTu) {
		this.serviceCalendarTu = serviceCalendarTu;
	}

	/**
	 * returns true if the service is available on Wednesday
	 * @return serviceCalendarWe as boolean
	 */
	public boolean isServiceCalendarWe() {
		return serviceCalendarWe;
	}

	/**
	 * sets the service true or false on Wednesday
	 * @param serviceCalendarWe as boolean
	 */
	public void setServiceCalendarWe(boolean serviceCalendarWe) {
		this.serviceCalendarWe = serviceCalendarWe;
	}
	
	/**
	 * returns true if the service is available on Thursday
	 * @return serviceCalendarTh as boolean
	 */
	public boolean isServiceCalendarTh() {
		return serviceCalendarTh;
	}

	/**
	 * sets the service true or false on Thursday
	 * @param serviceCalendarTh as boolean
	 */
	public void setServiceCalendarTh(boolean serviceCalendarTh) {
		this.serviceCalendarTh = serviceCalendarTh;
	}

	/**
	 * returns true if the service is available on Friday
	 * @return serviceCalendarFr as boolean
	 */
	public boolean isServiceCalendarFr() {
		return serviceCalendarFr;
	}

	/**
	 * sets the service true or false on Friday
	 * @param serviceCalendarFr as boolean
	 */
	public void setServiceCalendarFr(boolean serviceCalendarFr) {
		this.serviceCalendarFr = serviceCalendarFr;
	}

	/**
	 * returns true if the service is available on Saturday
	 * @return serviceCalendarSa as boolean
	 */
	public boolean isServiceCalendarSa() {
		return serviceCalendarSa;
	}

	/**
	 * sets the service true of false on Saturday
	 * @param serviceCalendarSa as boolean
	 */
	public void setServiceCalendarSa(boolean serviceCalendarSa) {
		this.serviceCalendarSa = serviceCalendarSa;
	}

	/**
	 * returns true if the service is available on Sunday
	 * @return serviceCalendarSu as boolean
	 */
	public boolean isServiceCalendarSu() {
		return serviceCalendarSu;
	}

	/**
	 * sets the service true of false on Sunday
	 * @param serviceCalendarSu
	 */
	public void setServiceCalendarSu(boolean serviceCalendarSu) {
		this.serviceCalendarSu = serviceCalendarSu;
	}
	
	/**
	 * return the serviceCalendarEndDate from gtfs
	 * @return serviceCalendarEndDate as String in format YYYYMMDD
	 */
	public String getServiceCalendarEndDate() {
		return serviceCalendarEndDate;
	}

	/**
	 * sets the serviceCalendarEndDate 
	 * @param serviceCalendarEndDate as String in format YYYYMMDD
	 */
	public void setServiceCalendarEndDate(String serviceCalendarEndDate) {
		this.serviceCalendarEndDate = serviceCalendarEndDate;
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
	 * returns the year of the StartDate
	 * @return startDateYear as Integer
	 */
	public int getStartDateYear() {
		return startDateYear;
	}

	/**
	 * sets the year of the StartDate
	 * @param startDateYear as Integer
	 */
	public void setStartDateYear(int startDateYear) {
		this.startDateYear = startDateYear;
	}

	/**
	 * returns the month of the StartDate
	 * @return StartDateMonth as Integer
	 */
	public int getStartDateMonth() {
		return startDateMonth;
	}

	/**
	 * sets the month of the StartDate
	 * @param startDateMonth as Integer
	 */
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}

	/**
	 * returns the day of the StartDate
	 * @return startDateDay as Integer
	 */
	public int getStartDateDay() {
		return startDateDay;
	}

	/**
	 * sets the day of the StartDate
	 * @param startDateDay
	 */
	public void setStartDateDay(int startDateDay) {
		this.startDateDay = startDateDay;
	}

	/**
	 * returns the year of the EndDate
	 * @return endDateYear as Integer
	 */
	public int getEndDateYear() {
		return endDateYear;
	}

	/**
	 * sets the year of the EndDate
	 * @param endDateYear as Integer
	 */
	public void setEndDateYear(int endDateYear) {
		this.endDateYear = endDateYear;
	}

	/**
	 * returns the month of the EndDate
	 * @return endDateMonth as Integer
	 */
	public int getEndDateMonth() {
		return endDateMonth;
	}

	/**
	 * sets the month of the EndDate
	 * @param endDateMonth as Integer
	 */
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}

	/**
	 * returns the day of the EndDate
	 * @return endDateDay as Integer
	 */
	public int getEndDateDay() {
		return endDateDay;
	}

	/**
	 * sets the day of the EndDate
	 * @param endDateDay as Integer
	 */
	public void setEndDateDay(int endDateDay) {
		this.endDateDay = endDateDay;
	}

}
