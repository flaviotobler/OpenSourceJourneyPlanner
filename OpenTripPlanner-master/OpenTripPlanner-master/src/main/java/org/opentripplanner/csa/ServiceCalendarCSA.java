package org.opentripplanner.csa;

public class ServiceCalendarCSA {
    private String serviceCalendarStartDate;
    private boolean serviceCalendarMo;
    private boolean serviceCalendarDi;
    private boolean serviceCalendarMi;
    private boolean serviceCalendarDo;
    private boolean serviceCalendarFr;
    private boolean serviceCalendarSa;
    private boolean serviceCalendarSo;
    private String serviceCalendarEndDate;
    
    public ServiceCalendarCSA(String serviceCalendarStartDate, boolean serviceCalendarMo, boolean serviceCalendarDi, boolean serviceCalendarMi,boolean serviceCalendarDo,
    		boolean serviceCalendarFr,boolean serviceCalendarSa, boolean serviceCalendarSo,String serviceCalendarEndDate ){
    	
    	this.serviceCalendarStartDate = serviceCalendarStartDate;
    	this.serviceCalendarMo = serviceCalendarMo;
    	this.serviceCalendarDi = serviceCalendarDi;
    	this.serviceCalendarMi = serviceCalendarMi;
    	this.serviceCalendarDo = serviceCalendarDo;
    	this.serviceCalendarFr = serviceCalendarFr;
    	this.serviceCalendarSa = serviceCalendarSa;
    	this.serviceCalendarSo = serviceCalendarSo;
    	this.serviceCalendarEndDate = serviceCalendarEndDate;
    	
    }

	public String getServiceCalendarStartDate() {
		return serviceCalendarStartDate;
	}

	public void setServiceCalendarStartDate(String serviceCalendarStartDate) {
		this.serviceCalendarStartDate = serviceCalendarStartDate;
	}

	public boolean isServiceCalendarMo() {
		return serviceCalendarMo;
	}

	public void setServiceCalendarMo(boolean serviceCalendarMo) {
		this.serviceCalendarMo = serviceCalendarMo;
	}

	public boolean isServiceCalendarDi() {
		return serviceCalendarDi;
	}

	public void setServiceCalendarDi(boolean serviceCalendarDi) {
		this.serviceCalendarDi = serviceCalendarDi;
	}

	public boolean isServiceCalendarMi() {
		return serviceCalendarMi;
	}

	public void setServiceCalendarMi(boolean serviceCalendarMi) {
		this.serviceCalendarMi = serviceCalendarMi;
	}

	public boolean isServiceCalendarDo() {
		return serviceCalendarDo;
	}

	public void setServiceCalendarDo(boolean serviceCalendarDo) {
		this.serviceCalendarDo = serviceCalendarDo;
	}

	public boolean isServiceCalendarFr() {
		return serviceCalendarFr;
	}

	public void setServiceCalendarFr(boolean serviceCalendarFr) {
		this.serviceCalendarFr = serviceCalendarFr;
	}

	public boolean isServiceCalendarSa() {
		return serviceCalendarSa;
	}

	public void setServiceCalendarSa(boolean serviceCalendarSa) {
		this.serviceCalendarSa = serviceCalendarSa;
	}

	public boolean isServiceCalendarSo() {
		return serviceCalendarSo;
	}

	public void setServiceCalendarSo(boolean serviceCalendarSo) {
		this.serviceCalendarSo = serviceCalendarSo;
	}

	public String getServiceCalendarEndDate() {
		return serviceCalendarEndDate;
	}

	public void setServiceCalendarEndDate(String serviceCalendarEndDate) {
		this.serviceCalendarEndDate = serviceCalendarEndDate;
	}

}
