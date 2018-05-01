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


import com.google.common.collect.Sets;  //braucht es dich?


public class TimeTableBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimeTableBuilder.class);
    
    EntityHandler counter = new TimeTableEntityCounter();
    
    Set<String> agencyIdsSeen = Sets.newHashSet();  //holt library com.google.common.collect.Sets ??
    
    ArrayList<StopCSA> stoplist = new ArrayList<StopCSA>();  // temp liste
    
    ArrayList<TripCSA> triplist = new ArrayList<TripCSA>(); // temp liste

    int nextAgencyId = 1; // used for generating agency IDs to resolve ID conflicts
    
    //int nextAgencyId;
    
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
        
        for (ShapePoint shapePoint : ttstore.getAllEntitiesForType(ShapePoint.class)) {
          //  shapePoint.getShapeId().setAgencyId(reader.getDefaultAgencyId());
            LOG.info("shapePoint");
        }
        for (Route route : ttstore.getAllEntitiesForType(Route.class)) {
           // route.getId().setAgencyId(reader.getDefaultAgencyId());
            //generateRouteColor(route);
            LOG.info("RouteId {}. RouteShortName {}. RouteLongName {}.", route.getId(),route.getShortName(),route.getLongName());
        }
        for (Stop stop : ttstore.getAllEntitiesForType(Stop.class)) {
            //stop.getId().setAgencyId(reader.getDefaultAgencyId());
        	
        	
        	//TimeTable ADD STOPS
        	String stopId = stop.getId().getId();
            String stopName = stop.getName();
            double cordLongitude = stop.getLon();
            double cordLatitude = stop.getLat();
            LOG.info("Erzeuge Stop ----> "+"StopId: "+stopId +"  StopName: "+stopName +"  StopX: " +cordLongitude+ "  StopY: "+cordLatitude);
          
            StopCSA tempstop = new StopCSA(stopId,stopName,cordLongitude,cordLatitude);
            LOG.info("Erzeuge Stop ----> "+tempstop+"  StopId: "+tempstop.getStopId());
            stoplist.add(tempstop);
            tt.addStop(tempstop);
           
        	
            //TimeTable ADD FOOTPATH
            long duration = 300;//sekunden oder ms?
            LOG.info("Erzeuge Footpath ----> "+"departureStop: "+ tempstop.getName() +"  arrivalStop: "+tempstop.getName() +"  duration: "+ duration);
            Footpath tempfootpath = new Footpath(tempstop,tempstop,300); //referenz stimmt nicht wenn in temp liste ..
            tt.addFootpaths(tempfootpath);
            
            
            
        }
        for (Trip trip : ttstore.getAllEntitiesForType(Trip.class)) {
          //  trip.getId().setAgencyId(reader.getDefaultAgencyId());  
        	
        	//TimeTable ADD Trips
            String tripId = trip.getId().getId();
            String tripShortName = trip.getTripShortName();
            String tripHeadSign = trip.getTripHeadsign();
            
            String routeId = trip.getRoute().getId().getId();
            int routeType = trip.getRoute().getType();
            
            String agencyId = trip.getId().getAgencyId();
            String agencyName = trip.getRoute().getAgency().getName();
            String agencyNameLong = trip.getRoute().getAgency().getLang();
            String agencyUrl = trip.getRoute().getAgency().getUrl();
            String agencyTimeZoneOffset = trip.getRoute().getAgency().getTimezone();
            
            //AgencyAndId agencyAndId = trip.getId(); 
            
            LOG.info("Erzeuge Trip ----> "+"TripId: "+tripId +"  TripShortName: "+tripShortName +"  TripHeadSign:  "+tripHeadSign+ "  RouteId:  "+routeId+"  RouteType: "+routeType);
            LOG.info("weitere Parameter  ----> "+"AgencyId: "+agencyId +"  AgencyName:  "+agencyName+ " AgencyNameLong:  "+agencyNameLong+ "  AgencyUrl: "+agencyUrl+"  AgencyTimeZoneOffset: "+agencyTimeZoneOffset);
            TripCSA temptrip = new TripCSA(tripId,tripShortName,tripHeadSign,routeId,routeType,agencyId,agencyName,agencyNameLong,agencyUrl,agencyTimeZoneOffset);
            LOG.info("Erzeuge Trip ----> "+temptrip+"  TripId: "+temptrip.getTripId());
            triplist.add(temptrip);
            tt.addTrip(temptrip);     
            
            

            
        }
        for (ServiceCalendar serviceCalendar : ttstore.getAllEntitiesForType(ServiceCalendar.class)) {
            //serviceCalendar.getServiceId().setAgencyId(reader.getDefaultAgencyId());
            //LOG.info("ServiceId {}. StartDate {}. EndDate {}.", serviceCalendar.getServiceId(), serviceCalendar.getStartDate(), serviceCalendar.getEndDate());
        }
        for (ServiceCalendarDate serviceCalendarDate : ttstore.getAllEntitiesForType(ServiceCalendarDate.class)) {
           // serviceCalendarDate.getServiceId().setAgencyId(reader.getDefaultAgencyId());
            LOG.info("serviceCalendarDate");
        }
        for (FareAttribute fareAttribute : ttstore.getAllEntitiesForType(FareAttribute.class)) {
           // fareAttribute.getId().setAgencyId(reader.getDefaultAgencyId());
            LOG.info("fareAttribute");
        }
        for (Pathway pathway : ttstore.getAllEntitiesForType(Pathway.class)) {
           // pathway.getId().setAgencyId(reader.getDefaultAgencyId());
            LOG.info("pathway");
        }
        
        //TimeTable ADD Connections
        String lastTripId ="TEMPLATE-ID"; //departureStopid
        int lastStopSequence = 9999;
        int concounter = 0;
        String lastStop = "TEMPLATE-STOP"; //departureStop
        String lastStopId = "TEMPLATE-STOP-ID"; //departureStopID
        for (StopTime stoptime : ttstore.getAllEntitiesForType(StopTime.class)) {
        	String currentTripId = stoptime.getTrip().getId().getId();
        	String currentStop = stoptime.getStop().getName();  //arrivalStop
        	String currentStopId = stoptime.getStop().getId().getId(); //arrivalStopid
        	int currentStopSequence = stoptime.getStopSequence();
        	
        	if(currentTripId.equals(lastTripId)) { 
        		if(lastStopSequence<currentStopSequence) {
        			concounter++;
					LOG.info("CONNECTION FOUND: "+concounter +"      VON: "+ lastStop + "---->NACH: "+ currentStop);
					//TO DO GET INFOS REFERENZEN etc.
					//LOG.info("Triplist SIZE  "+triplist.size());
					//LOG.info("Stoplist SIZE  "+stoplist.size());
					
					
					for(int i = 0; i<triplist.size(); i++) {
						TripCSA trippels = triplist.get(i);
						if(trippels.getTripId() == currentTripId) {//suche Trip
							LOG.info("----> FOUND THE TRIP:  "+trippels+"  TripId: " + trippels.getTripId()+ "  TripHeadsign: "+trippels.getTripHeadSign());
							
							//uebergebe TRIP
							
							
							for(int j = 0; j<stoplist.size(); j++) {
								StopCSA currentstoppps = stoplist.get(j);
								if(currentstoppps.getStopId() == currentStopId) {//suche current Stop
									LOG.info("----> FOUND THE CurrentSTop:  "+currentstoppps+"  StopId: " + currentstoppps.getStopId()+"  StopName: "+currentstoppps.getName()); //current Stop
									
									//uebergebe currentStop
									
									
									for(int k = 0; k<stoplist.size(); k++) {
										StopCSA laststoppps = stoplist.get(k);
										if(laststoppps.getStopId() == lastStopId) {  //suche Last Stop
											LOG.info("-------> FOUND THE LastSTop:  "+laststoppps+"  StopId: " + laststoppps.getStopId()+"  StopName: "+laststoppps.getName()); //last Stop
											
											//uebergebe lastStop
											
											
											
											
											
											LOG.info("Erzeuge Connection ----> "+"StopCSA departureStop: "+laststoppps +"  StopCSA arrivalStop: "+currentstoppps +"  Calendar:  "+ "  Calendar:  "+"  TripCSA trip: "+trippels);
											
											Connection tempconnection = new Connection (laststoppps,currentstoppps,null,null,trippels);
											LOG.info("-->OBjekt pfad auf connection: "+ tempconnection);
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
        }
        
        
        
        
        
        ttstore.close(); 
        LOG.info("--------------TimetableStore closed -----------------------");
        
        tt.showStops();
        tt.showTrips();
        tt.showConnections();
        tt.showFootPaths();
       
    }  

}