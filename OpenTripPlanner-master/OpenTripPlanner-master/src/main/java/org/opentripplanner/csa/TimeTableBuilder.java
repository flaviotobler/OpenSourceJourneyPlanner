package org.opentripplanner.csa;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.csv_entities.EntityHandler;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;

import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.FareAttribute;
import org.onebusaway.gtfs.model.Pathway;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;

import org.onebusaway.gtfs.serialization.GtfsReader;

import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;


//inputs aus org.opentripplanner
import org.opentripplanner.graph_builder.model.GtfsBundle;
import org.opentripplanner.graph_builder.module.GtfsFeedId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.common.collect.Sets; 


public class TimeTableBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimeTableBuilder.class);
    
    EntityHandler counter = new TimeTableEntityCounter();
    
    Set<String> agencyIdsSeen = Sets.newHashSet(); 
    
    ArrayList<StopCSA> stoplist = new ArrayList<StopCSA>();  
    
    ArrayList<TripCSA> triplist = new ArrayList<TripCSA>(); 

    int nextAgencyId = 1; // used for generating agency IDs to resolve ID conflicts
    
    
    
    public TimeTableBuilder(){
    	
    }
    
    public void loadGtfs(GtfsBundle gtfsBundle)throws IOException {
    	
    	TimeTable tt = new TimeTable();
        
        GtfsMutableRelationalDao dao = new GtfsRelationalDaoImpl();
        
        TimeTableStoreImpl ttstore = new TimeTableStoreImpl(dao);
        ttstore.open();
        
        LOG.info("--------------TimetableStore oeffnen -----------------------");
        LOG.info("reading {}", gtfsBundle.toString());

        GtfsFeedId gtfsFeedId = gtfsBundle.getFeedId();
        LOG.info("reading FeedId {}" , gtfsFeedId.getId());
        
        
        GtfsReader reader = new GtfsReader();
        reader.setInputSource(gtfsBundle.getCsvInputSource());
        reader.setEntityStore(ttstore);
        reader.setInternStrings(true);
        reader.setDefaultAgencyId(gtfsFeedId.getId());
        
        if (LOG.isDebugEnabled())
            reader.addEntityHandler(counter);

        for (Class<?> entityClass : reader.getEntityClasses()) {
            LOG.info("reading entities: " + entityClass.getName());
            reader.readEntities(entityClass);
            ttstore.flush();

            if (entityClass == Agency.class) {
                for (Agency agency : reader.getAgencies()) {
                    String agencyId = agency.getId();
                    String agencyname = agency.getName();
                    LOG.info("This Agency has the ID {}", agencyId);
                    LOG.info("Agency has Name {}", agencyname);
                    
                    // Somehow, when the agency's id field is missing, OBA replaces it with the agency's name.
                    // TODO Figure out how and why this is happening.
                    if (agencyId == null || agencyIdsSeen.contains(gtfsFeedId.getId() + agencyId)) {
                        // Loop in case generated name is already in use.
                        String generatedAgencyId = null;
                        while (generatedAgencyId == null || agencyIdsSeen.contains(generatedAgencyId)) {
                            generatedAgencyId = "F" + nextAgencyId;
                            nextAgencyId++;
                        }
                        LOG.warn("The agency ID '{}' was already seen, or I think it's bad. Replacing with '{}'.", agencyId, generatedAgencyId);
                        reader.addAgencyIdMapping(agencyId, generatedAgencyId); // NULL key should work
                        agency.setId(generatedAgencyId);
                        agencyId = generatedAgencyId;
                    }
                    if (agencyId != null) agencyIdsSeen.add(gtfsFeedId.getId() + agencyId);
                }
            }
        }
        
        LOG.info("readerDefaultAgencyId: " + reader.getDefaultAgencyId());
        LOG.info("readergetAgencies: " + reader.getAgencies());
        LOG.info("readerEntityClasses: " + reader.getEntityClasses());
        LOG.info("mystoregetAllEntitiesForType: " + ttstore.getAllEntitiesForType(Route.class));
        

        for (Stop stop : ttstore.getAllEntitiesForType(Stop.class)) {
        	//TimeTable ADD STOPS
        	String stopId = stop.getId().getId();
            String stopName = stop.getName();
            double cordLongitude = stop.getLon();
            double cordLatitude = stop.getLat();
            
          
            StopCSA tempstop = new StopCSA(stopId,stopName,cordLatitude,cordLongitude);
            LOG.info("Erzeuge Stop ----> "+"StopId: "+stopId +"  StopName: "+stopName +"  Latitude: "+cordLatitude+"  Longitude: " +cordLongitude +" "+ tempstop);
            stoplist.add(tempstop);
            tt.addStop(tempstop);
           
        	
            //TimeTable ADD FOOTPATH
            long duration = 300; 
            FootpathCSA tempfootpath = new FootpathCSA(tempstop,tempstop,300); 
            LOG.info("Erzeuge Footpath ----> "+"departureStop: "+ tempstop.getName() +"  arrivalStop: "+tempstop.getName() +"  duration: "+ duration+"  "+ tempfootpath);
            tt.addFootpaths(tempfootpath);
            
            
            
        }
        for (Trip trip : ttstore.getAllEntitiesForType(Trip.class)) {
        	//TimeTable ADD Trips
            String tripId = trip.getId().getId();
            String tripShortName = trip.getTripShortName();
            String tripHeadSign = trip.getTripHeadsign();
            
            String routeId = trip.getRoute().getId().getId();
            String routeShortName = trip.getRoute().getShortName();
            String routeDesc = trip.getRoute().getDesc();
            int routeType = trip.getRoute().getType();
            
            String agencyId = trip.getId().getAgencyId();
            String agencyName = trip.getRoute().getAgency().getName();
            String agencyNameLong = trip.getRoute().getAgency().getLang();
            String agencyUrl = trip.getRoute().getAgency().getUrl();
            String agencyTimeZoneOffset = trip.getRoute().getAgency().getTimezone();
            
            String serviceId = trip.getServiceId().getId();
            
            //String serviceId = trip.
            
            //AgencyAndId agencyAndId = trip.getId(); 
            
            
            TripCSA temptrip = new TripCSA(tripId,tripShortName,tripHeadSign,routeId,routeShortName,routeDesc,routeType,agencyId,agencyName,agencyNameLong,agencyUrl,agencyTimeZoneOffset,serviceId);
            LOG.info("Erzeuge Trip ----> "+"TripId: "+tripId +"  TripShortName: "+tripShortName +"  TripHeadSign:  "+tripHeadSign+ "  RouteId:  "+routeId+"  RouteShortName  "+routeShortName+"  RouteDesc "+routeDesc+"  RouteType: "+routeType);
            LOG.info("weitere Parameter  ----> "+"AgencyId: "+agencyId +"  AgencyName:  "+agencyName+ " AgencyNameLong:  "+agencyNameLong+ "  AgencyUrl: "+agencyUrl+"  AgencyTimeZoneOffset: "+agencyTimeZoneOffset+"  ServiceId:  "+serviceId+"  "+temptrip);
            triplist.add(temptrip);
            tt.addTrip(temptrip);     
            
            

            
        }
        for (ServiceCalendar serviceCalendar : ttstore.getAllEntitiesForType(ServiceCalendar.class)) {
        	//ADD toTrips-serviceCalendar
        	String currentServiceId = serviceCalendar.getServiceId().getId(); 
        	String ServiceCalendarStartDate = serviceCalendar.getStartDate().getAsString(); 
        	String ServiceCalendarEndDate = serviceCalendar.getEndDate().getAsString();
        	
        	
            boolean serviceCalendarMo = false;
            boolean serviceCalendarDi = false;
            boolean serviceCalendarMi = false;
            boolean serviceCalendarDo = false;
            boolean serviceCalendarFr = false;
            boolean serviceCalendarSa = false;
            boolean serviceCalendarSo = false;
        	
            int intserviceCalendarMo = serviceCalendar.getMonday();
            if(intserviceCalendarMo == 1) serviceCalendarMo=true;
            
            int intserviceCalendarDi = serviceCalendar.getTuesday();
            if(intserviceCalendarDi == 1) serviceCalendarDi=true;
            
            int intserviceCalendarMi = serviceCalendar.getWednesday();
            if(intserviceCalendarMi == 1) serviceCalendarMi=true;
            
            int intserviceCalendarDo = serviceCalendar.getThursday();
            if(intserviceCalendarDo == 1) serviceCalendarDo=true;
            
            int intserviceCalendarFr = serviceCalendar.getFriday();
            if(intserviceCalendarFr == 1) serviceCalendarFr=true;
            
            int intserviceCalendarSa = serviceCalendar.getSaturday();
            if(intserviceCalendarSa == 1) serviceCalendarSa=true;
            
            int intserviceCalendarSo = serviceCalendar.getSunday();
            if(intserviceCalendarSo == 1) serviceCalendarSo=true;
            
        	
        	
        	for(int i = 0; i<triplist.size(); i++) {
				TripCSA temptrip = triplist.get(i);
				if(temptrip.getServiceId() == currentServiceId) {//suche Trip
					LOG.info("----> Erzeuge ServiceCalendar fuer Trip:   "+temptrip+"  ServiceId:  "+currentServiceId+" ServiceCalendarStartDate: " + ServiceCalendarStartDate+
							"  ServiceCalendarEndDate: "+ServiceCalendarEndDate+" TagenVerfuegbar: "+serviceCalendarMo+","+serviceCalendarDi+","+serviceCalendarMi+","+serviceCalendarDo+","+
							serviceCalendarFr+","+serviceCalendarSa+","+serviceCalendarSo);
					
					ServiceCalendarCSA scCSA = new ServiceCalendarCSA(ServiceCalendarStartDate,serviceCalendarMo,serviceCalendarDi,serviceCalendarMi,
							serviceCalendarDo,serviceCalendarFr,serviceCalendarSa,serviceCalendarSo,ServiceCalendarEndDate);
					temptrip.serviceCalendars.add(scCSA);
					
				}
        	}
        	
        	
        }
        for (ServiceCalendarDate serviceCalendarDate : ttstore.getAllEntitiesForType(ServiceCalendarDate.class)) {
        	//ADD toTrips-serviceCalendarDate
            String currentServiceId = serviceCalendarDate.getServiceId().getId();
            
            String serviceCalendarDateDate = serviceCalendarDate.getDate().getAsString();
            boolean serviceCalendarDateExceptionType = false; 
            int intserviceCalendarDateExceptionType = serviceCalendarDate.getExceptionType();
            
            if(intserviceCalendarDateExceptionType == 1) {
            	serviceCalendarDateExceptionType = true; //added
            }else if(intserviceCalendarDateExceptionType == 2) {
            	serviceCalendarDateExceptionType = false; //removed
            }else {
            	LOG.error("WRONG ENTRY in GTFS");
            }
          
            
        	for(int i = 0; i<triplist.size(); i++) {
				TripCSA temptrip = triplist.get(i);
				if(temptrip.getServiceId() == currentServiceId) {//suche Trip
					LOG.info("----> Erzeuge ServiceCalendarDate fuer Trip:   "+temptrip+"  ServiceId:  "+currentServiceId+"  serviceCalendarDate: " +
							serviceCalendarDateDate+ "  serviceCalendarDateExceptionType: "+serviceCalendarDateExceptionType);  

					ServiceCalendarDateCSA scdCSA = new ServiceCalendarDateCSA(serviceCalendarDateDate,serviceCalendarDateExceptionType);
					temptrip.serviceCalendarDates.add(scdCSA);

				}
        	}
   
        }
        
        //TimeTable ADD Connections
        String lastTripId ="TEMPLATE-ID"; //departureStopid
        int lastStopSequence = 9999;
        int concounter = 0;
        String lastStop = "TEMPLATE-STOP"; //departureStop
        String lastStopId = "TEMPLATE-STOP-ID"; //departureStopID
        int lastStopTime = 0;  //departuretime
        for (StopTime stoptime : ttstore.getAllEntitiesForType(StopTime.class)) {
        	String currentTripId = stoptime.getTrip().getId().getId();
        	String currentStop = stoptime.getStop().getName();  //arrivalStop
        	String currentStopId = stoptime.getStop().getId().getId(); //arrivalStopid
        	int currentStopSequence = stoptime.getStopSequence();
        	
        	int currentArrivalStopTime = stoptime.getArrivalTime();
        	int currentDepartureStopTime = stoptime.getDepartureTime();
        	
        	if(currentTripId.equals(lastTripId)) { 
        		if(lastStopSequence<currentStopSequence) {
        			concounter++;
					LOG.info("CONNECTION FOUND: "+concounter +"      VON: "+ lastStop + "---->NACH: "+ currentStop);			
					for(int i = 0; i<triplist.size(); i++) {
						TripCSA temptrip = triplist.get(i);
						if(temptrip.getTripId() == currentTripId) {//lf Trip
							LOG.info("----> FOUND THE TRIP:  "+temptrip+"  TripId: " + temptrip.getTripId()+ "  TripHeadsign: "+temptrip.getTripHeadSign());
							
							for(int j = 0; j<stoplist.size(); j++) {
								StopCSA tempcurrentstop = stoplist.get(j);
								if(tempcurrentstop.getStopId() == currentStopId) {//lf current Stop
									LOG.info("----> FOUND THE CurrentStop:  "+tempcurrentstop+"  StopId: " + tempcurrentstop.getStopId()+"  StopName: "+tempcurrentstop.getName()); 
									
									
									for(int k = 0; k<stoplist.size(); k++) {
										StopCSA templaststop = stoplist.get(k);
										if(templaststop.getStopId() == lastStopId) {  //lf Last Stop
											LOG.info("-------> FOUND THE LastStop:  "+templaststop+"  StopId: " + templaststop.getStopId()+"  StopName: "+templaststop.getName()); 
											
											LOG.info("Erzeuge Connection ----> "+"StopCSA departureStop: "+templaststop +"  StopCSA arrivalStop: "+tempcurrentstop +"  DepartureStopTime:  "+lastStopTime+ "  ArrivalStopTime:  "+currentArrivalStopTime+"  TripCSA trip: "+temptrip);
											
											ConnectionCSA tempconnection = new ConnectionCSA (templaststop,tempcurrentstop,lastStopTime,currentArrivalStopTime,temptrip);
											//LOG.info("-->OBjekt pfad auf connection: "+ tempconnection);
											tt.addConnection(tempconnection);
											
										}
										
									}
									
									
									
								
								}
								
							}
							
							
							
							
						}
						
					}
					
        		}
        	}
        	lastStopSequence = currentStopSequence;
        	lastTripId = currentTripId;
        	lastStop = currentStop;
        	lastStopId = currentStopId;
        	lastStopTime = currentDepartureStopTime;
        }
        
        
        
        
        
        ttstore.close(); 
        LOG.info("--------------TimetableStore closed -----------------------");
        
        tt.showStops();
        tt.showTrips();
        tt.showConnections();
        tt.showFootPaths();
       
    }  
    
    
    

}