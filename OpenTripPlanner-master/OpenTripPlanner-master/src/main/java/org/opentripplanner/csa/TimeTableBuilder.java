package org.opentripplanner.csa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.onebusaway.csv_entities.EntityHandler;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;

import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
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


/**
 * This class gets the Information from the GTFS and generates the important objects that are needed for the Connection Scan Algorithm.
 * With the TimeTableStoreImpl we can getAllEntitiesForType(class xxx) so we can read the for example all the routes etc.
 * Now with the information we read we can generate all the need Classes for the with the information for the CSA.
 * 
 * (TripsCSA,StopCSA,FootpathCSA,ConnectionCSA)
 * @author Christian
 *
 */
public class TimeTableBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimeTableBuilder.class);
    
    EntityHandler counter = new TimeTableEntityCounter();
    
    Set<String> agencyIdsSeen = Sets.newHashSet(); 
    
    /**
     * temporary assistant stoplist
     */
    ArrayList<StopCSA> stoplist = new ArrayList<StopCSA>();  
    
    /**
     * temporary assistant triplist
     */
    ArrayList<TripCSA> triplist = new ArrayList<TripCSA>(); 

    int nextAgencyId = 1; // used for generating agency IDs to resolve ID conflicts
    
    
    /**
     * constructor for the TimeTableBuilder
     */
    public TimeTableBuilder(){
    	
    }
    
    /**
     * This methode opens a store to load in the gtfs information with relations (dao). Then with the 
     * @param gtfsBundle as object has the information where the gtfs is
     * @throws IOException
     */
    public void loadGtfs(GtfsBundle gtfsBundle)throws IOException {
    	
    	TimeTable tt = new TimeTable();
        
        GtfsMutableRelationalDao dao = new GtfsRelationalDaoImpl();
        
        TimeTableStoreImpl ttstore = new TimeTableStoreImpl(dao);
        ttstore.open();
        
        LOG.info("--------------TimetableStore opening -----------------------");
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
            
            AgencyAndId sAAId = stop.getId();
            
            StopCSA tempstop = new StopCSA(stopId,stopName,cordLatitude,cordLongitude,sAAId);
            LOG.info("generate Stop ----> "+"StopId: "+stopId +"  StopName: "+stopName +"  Latitude: "+cordLatitude+"  Longitude: " +cordLongitude +" "+ tempstop);
            stoplist.add(tempstop);
            tt.addStop(tempstop);

            //TimeTable ADD FOOTPATH
            long duration = 300; 
            FootpathCSA tempfootpath = new FootpathCSA(tempstop,tempstop,300); 
            LOG.info("generate Footpath ----> "+"departureStop: "+ tempstop.getName() +"  arrivalStop: "+tempstop.getName() +"  duration: "+ duration+"  "+ tempfootpath);
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

            AgencyAndId tAAId = trip.getId(); 
            AgencyAndId rAAId = trip.getRoute().getId();
            
            TripCSA temptrip = new TripCSA(tripId,tripShortName,tripHeadSign,routeId,routeShortName,routeDesc,routeType,agencyId,agencyName,agencyNameLong,agencyUrl,agencyTimeZoneOffset,serviceId, tAAId, rAAId);
            LOG.info("generate Trip ----> "+"TripId: "+tripId +"  TripShortName: "+tripShortName +"  TripHeadSign:  "+tripHeadSign+ "  RouteId:  "+routeId+"  RouteShortName  "+routeShortName+"  RouteDesc "+routeDesc+"  RouteType: "+routeType);
            LOG.info("further parameters  ----> "+"AgencyId: "+agencyId +"  AgencyName:  "+agencyName+ " AgencyNameLong:  "+agencyNameLong+ "  AgencyUrl: "+agencyUrl+"  AgencyTimeZoneOffset: "+agencyTimeZoneOffset+"  ServiceId:  "+serviceId+"  "+temptrip);
            triplist.add(temptrip);
            tt.addTrip(temptrip);              
        }
        
        for (ServiceCalendar serviceCalendar : ttstore.getAllEntitiesForType(ServiceCalendar.class)) {
        	//ADD toTrips-serviceCalendar
        	String currentServiceId = serviceCalendar.getServiceId().getId(); 
        	String ServiceCalendarStartDate = serviceCalendar.getStartDate().getAsString(); 
        	String ServiceCalendarEndDate = serviceCalendar.getEndDate().getAsString();
        	
            boolean serviceCalendarMo = false;
            boolean serviceCalendarTu = false;
            boolean serviceCalendarWe = false;
            boolean serviceCalendarTh = false;
            boolean serviceCalendarFr = false;
            boolean serviceCalendarSa = false;
            boolean serviceCalendarSu = false;
        	
            int intserviceCalendarMo = serviceCalendar.getMonday();
            if(intserviceCalendarMo == 1) serviceCalendarMo=true;
            
            int intserviceCalendarTu = serviceCalendar.getTuesday();
            if(intserviceCalendarTu == 1) serviceCalendarTu=true;
            
            int intserviceCalendarWe = serviceCalendar.getWednesday();
            if(intserviceCalendarWe == 1) serviceCalendarWe=true;
            
            int intserviceCalendarTh = serviceCalendar.getThursday();
            if(intserviceCalendarTh == 1) serviceCalendarTh=true;
            
            int intserviceCalendarFr = serviceCalendar.getFriday();
            if(intserviceCalendarFr == 1) serviceCalendarFr=true;
            
            int intserviceCalendarSa = serviceCalendar.getSaturday();
            if(intserviceCalendarSa == 1) serviceCalendarSa=true;
            
            int intserviceCalendarSu = serviceCalendar.getSunday();
            if(intserviceCalendarSu == 1) serviceCalendarSu=true;
            
        	for(int i = 0; i<triplist.size(); i++) {
				TripCSA temptrip = triplist.get(i);
				if(temptrip.getServiceId() == currentServiceId) {//suche Trip
					LOG.info("----> generate ServiceCalendar for Trip:   "+temptrip+"  ServiceId:  "+currentServiceId+" ServiceCalendarStartDate: " + ServiceCalendarStartDate+
							"  ServiceCalendarEndDate: "+ServiceCalendarEndDate+" DayAvailability: "+serviceCalendarMo+","+serviceCalendarTu+","+serviceCalendarWe+","+serviceCalendarTh+","+
							serviceCalendarFr+","+serviceCalendarSa+","+serviceCalendarSu);
					
					ServiceCalendarCSA scCSA = new ServiceCalendarCSA(ServiceCalendarStartDate,serviceCalendarMo,serviceCalendarTu,serviceCalendarWe,
							serviceCalendarTh,serviceCalendarFr,serviceCalendarSa,serviceCalendarSu,ServiceCalendarEndDate);
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
				if(temptrip.getServiceId() == currentServiceId) {//looking for trip
					LOG.info("----> generate ServiceCalendarDate for Trip:   "+temptrip+"  ServiceId:  "+currentServiceId+"  serviceCalendarDate: " +
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
					LOG.info("CONNECTION FOUND: "+concounter +"      from: "+ lastStop + "---->to: "+ currentStop);			
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
											
											LOG.info("generate Connection ----> "+"StopCSA departureStop: "+templaststop +"  StopCSA arrivalStop: "+tempcurrentstop +"  DepartureStopTime:  "+lastStopTime+ "  ArrivalStopTime:  "+currentArrivalStopTime+"  TripCSA trip: "+temptrip);
											
											ConnectionCSA tempconnection = new ConnectionCSA (templaststop,tempcurrentstop,lastStopTime,currentArrivalStopTime,temptrip);
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