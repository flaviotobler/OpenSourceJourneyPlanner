package org.opentripplanner.csa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.onebusaway.csv_entities.EntityHandler;
import org.onebusaway.gtfs.impl.GenericDaoImpl;
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


//inputs aus org.opentripplanner
import org.opentripplanner.graph_builder.model.GtfsBundle;
import org.opentripplanner.graph_builder.module.GtfsFeedId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Sets; 


/**
 * This class gets the Information from the GTFS and generates the important objects that are needed for the Connection Scan Algorithm, 
 * and save them in a SerializedObjectFile. Alternativ the object can be loaded from the SerializedObjectFile.
 * To switch between the different modes you need to adjust the code in the class "GtfsModule"
 * in the Package "org.opentripplanner.graph_builder.module".
 * @author Christian
 *
 */
public class TimeTableBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimeTableBuilder.class);
    
    private static Set<ConnectionCSA> connectionsAscending = new TreeSet<ConnectionCSA>();
    
    private static Set<ConnectionCSA> connectionsDescending = new TreeSet<ConnectionCSA>();
    
    private static TimeTable tt = new TimeTable();

    /**
     * constructor for the TimeTableBuilder
     */
    public TimeTableBuilder(){
    	
    }
    
    /**
     * 
     * @return the TimeTable
     */
    public TimeTable getTimeTable(){
        return tt;
    }
    
    /**
     * This methode opens a store to load in the gtfs information with relations (dao). Then with the 
     * @param gtfsBundle as object has the information where the gtfs is
     * @throws IOException
     */
    public void loadFromGtfs(GtfsBundle gtfsBundle)throws JsonGenerationException, JsonMappingException, IOException {

        GenericDaoImpl store = new GenericDaoImpl();

        store.open(); 
        
        
        LOG.info("reading {}", gtfsBundle.toString());

        GtfsFeedId gtfsFeedId = gtfsBundle.getFeedId();
        LOG.info("reading FeedId {}" , gtfsFeedId.getId());
        
        
        GtfsReader reader = new GtfsReader();
        reader.setInputSource(gtfsBundle.getCsvInputSource());
        reader.setEntityStore(store);
      
        LOG.info("--------------Start reading -----------------------");
        reader.run();

        int stopcounter = 0;
	    LOG.info("ADD Stops and Footpath");
        for (Stop stop : store.getAllEntitiesForType(Stop.class)) {
        	//TimeTable ADD STOPS
            String stopName = stop.getName();
            double cordLongitude = stop.getLon();
            double cordLatitude = stop.getLat();
            
            AgencyAndId sAAId = stop.getId();
            
            StopCSA tempstop = new StopCSA(stopName,cordLatitude,cordLongitude,sAAId);
            //LOG.info("generate Stop ----> "+"StopId: "+tempstop.getsAAId().getId() +"  StopName: "+stopName +"  Latitude: "+cordLatitude+"  Longitude: " +cordLongitude +" "+ tempstop);
            tt.addStop(tempstop);
            stopcounter++;
            

            //TimeTable ADD FOOTPATH
            long duration = 60; 
            FootpathCSA tempfootpath = new FootpathCSA(tempstop,tempstop,duration); 
            //LOG.info("generate Footpath ----> "+"departureStop: "+ tempstop.getName() +"  arrivalStop: "+tempstop.getName() +"  duration: "+ duration+"  "+ tempfootpath);
            tt.addFootpaths(tempfootpath);       
        }
        LOG.info("Added "+stopcounter+" Stops and Footpath");
        
        
        int tripcounter = 0;
        LOG.info("ADD Trips");
        for (Trip trip : store.getAllEntitiesForType(Trip.class)) {
        	//TimeTable ADD Trips
            String tripShortName = trip.getTripShortName();
            String tripHeadSign = trip.getTripHeadsign();
            
            String routeShortName = trip.getRoute().getShortName();
            String routeDesc = trip.getRoute().getDesc();
            int routeType = trip.getRoute().getType();
            
            String agencyName = trip.getRoute().getAgency().getName();
            String agencyNameLong = trip.getRoute().getAgency().getLang();
            String agencyUrl = trip.getRoute().getAgency().getUrl();
            String agencyTimeZoneOffset = trip.getRoute().getAgency().getTimezone();
            
            String serviceId = trip.getServiceId().getId();

            AgencyAndId tAAId = trip.getId(); 
            AgencyAndId rAAId = trip.getRoute().getId();
            
            TripCSA temptrip = new TripCSA(tripShortName,tripHeadSign,routeShortName,routeDesc,routeType,agencyName,agencyNameLong,agencyUrl,agencyTimeZoneOffset,serviceId, tAAId, rAAId);
            //LOG.info("generate Trip ----> "+"TripId: "+temptrip.gettAAId().getId() +"  TripShortName: "+tripShortName +"  TripHeadSign:  "+tripHeadSign+ "  RouteId:  "+temptrip.getrAAId()+"  RouteShortName  "+routeShortName+"  RouteDesc "+routeDesc+"  RouteType: "+routeType);
            //LOG.info("further parameters  ----> "+"AgencyId: "+temptrip.gettAAId().getAgencyId()+"  AgencyName:  "+agencyName+ " AgencyNameLong:  "+agencyNameLong+ "  AgencyUrl: "+agencyUrl+"  AgencyTimeZoneOffset: "+agencyTimeZoneOffset+"  ServiceId:  "+serviceId+"  "+temptrip);
            tt.addTrip(temptrip);
            tripcounter++;
        }
        LOG.info("Added "+tripcounter+" Trips");
        
        /*
        int SCcounter = 0;
	    LOG.info("ADD ServiceCalendar");
        for (ServiceCalendar serviceCalendar : store.getAllEntitiesForType(ServiceCalendar.class)) {
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
            
            for(TripCSA temptrip : tt.getTrips()) {	
				if(temptrip.getServiceId().equals(currentServiceId)) {//suche Trip temptrip.getServiceId() == currentServiceId
					LOG.info("----> generate ServiceCalendar for Trip:   "+"TripID "+temptrip.gettAAId().getId()+"  REF "+temptrip+"  ServiceId:  "+currentServiceId+" ServiceCalendarStartDate: " + ServiceCalendarStartDate+"  ServiceCalendarEndDate: "+ServiceCalendarEndDate+" DayAvailability: "+serviceCalendarMo+","+serviceCalendarTu+","+serviceCalendarWe+","+serviceCalendarTh+","+serviceCalendarFr+","+serviceCalendarSa+","+serviceCalendarSu);
					
					ServiceCalendarCSA scCSA = new ServiceCalendarCSA(ServiceCalendarStartDate,serviceCalendarMo,serviceCalendarTu,serviceCalendarWe,
							serviceCalendarTh,serviceCalendarFr,serviceCalendarSa,serviceCalendarSu,ServiceCalendarEndDate);
					temptrip.serviceCalendars.add(scCSA);
					SCcounter++;
					
				}
        	}	
        }
        LOG.info("Added "+SCcounter+" ServiceCalendar");
        
        
        int SCDcounter = 0;
        LOG.info("ADD ServiceCalendarDates");
        for (ServiceCalendarDate serviceCalendarDate : store.getAllEntitiesForType(ServiceCalendarDate.class)) {
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

            for(TripCSA temptrip : tt.getTrips()) {	
				if(temptrip.getServiceId().equals(currentServiceId)) {//looking for trip (temptrip.getServiceId() == currentServiceId)
					LOG.info("----> generate ServiceCalendarDate for Trip:   "+"TripID "+temptrip.gettAAId().getId()+"  REF "+temptrip+"  ServiceId:  "+currentServiceId+"  serviceCalendarDate: " +serviceCalendarDateDate+ "  serviceCalendarDateExceptionType: "+serviceCalendarDateExceptionType);  

					ServiceCalendarDateCSA scdCSA = new ServiceCalendarDateCSA(serviceCalendarDateDate,serviceCalendarDateExceptionType);
					temptrip.serviceCalendarDates.add(scdCSA);
					SCDcounter++;

				}
        	}
   
        }
        LOG.info("Added "+SCDcounter+" ServiceCalendarDates");
        
        */
        
        LOG.info("ADD Connections");
        //TimeTable ADD Connections
        AgencyAndId tAAId1 = new AgencyAndId("999","TEMPLATE-currentTripID");
        TripCSA currentTrip = new TripCSA(tAAId1);
        
        AgencyAndId sAAId1 = new AgencyAndId("998","TEMPLATE-currentStopID");
        StopCSA currentStop = new StopCSA(sAAId1);
        
        AgencyAndId tAAId2 = new AgencyAndId("997","TEMPLATE-lastTripTRIPID");
        TripCSA lastTrip = new TripCSA(tAAId2);
        
        AgencyAndId sAAId2 = new AgencyAndId("996","TEMPLATE-lastStopID");
        StopCSA lastStop = new StopCSA(sAAId2);
        
        //weiteres
        String lastTripId ="TEMPLATE-ID"; //departureStopid
        int lastStopSequence = 9999;
        int concounter = 0;  
        int lastStopTime = 0;  //departuretime
        
        for (StopTime stoptime : store.getAllEntitiesForType(StopTime.class)) {
        	String currentTripId = stoptime.getTrip().getId().getId();
        	String currentStopId = stoptime.getStop().getId().getId(); //arrivalStopid
        	int currentStopSequence = stoptime.getStopSequence();
        	
        	int currentArrivalStopTime = stoptime.getArrivalTime();
        	int currentDepartureStopTime = stoptime.getDepartureTime();
        	
        	
        	if(lastTrip.gettAAId().getId().equals(currentTripId)) { //last trip same then...
				currentTrip = lastTrip;
			}else{
				
				for(TripCSA temptrip : tt.getTrips()) { 					
					if(temptrip.gettAAId().getId().equals(currentTripId)) {
						currentTrip = temptrip;
						break;
					}
				}	
			}
        	
			for(StopCSA tempstop : tt.getStops()) {
		    	if(tempstop.getsAAId().getId().equals(currentStopId)) {
		    		currentStop = tempstop;
		    		break;
		    	}	
			}	
			
			if(currentTripId.equals(lastTripId)) { 
	        	if(lastStopSequence<currentStopSequence) {
	        		ConnectionCSA tempconnection = new ConnectionCSA (lastStop,currentStop,lastStopTime,currentArrivalStopTime,currentTrip);
	        		//LOG.info("FOUND CONNECTION"+"FROM "+lastStop.getsAAId().getId()+"---->TO "+currentStop.getsAAId().getId()+"  with Trip "+currentTrip.gettAAId().getId());
	        		//LOG.info("generate Connection ----> "+"StopCSA departureStop: "+lastStop +"  StopCSA arrivalStop: "+currentStop +"  DepartureStopTime:  "+lastStopTime+ "  ArrivalStopTime:  "+currentArrivalStopTime+"  TripCSA trip: "+currentTrip);
					connectionsAscending.add(tempconnection);	
					concounter++;	
					if(concounter%10000 == 0) {
						LOG.info("Concounter: "+concounter);
					}
	        	}
			}
			lastStop = currentStop;
			lastTrip = currentTrip; 	
			
			lastStopSequence = currentStopSequence;
        	lastTripId = currentTripId;
        	lastStopTime = currentDepartureStopTime;
        }
        LOG.info("Added "+concounter+" Connections");
        
        createConnectionsDescending();

        tt.addConnectionsAscending(connectionsAscending);
        tt.addConnectionsDescending(connectionsDescending);
        
        tt.setConnectionsAscendingNonStatic(connectionsAscending);
        tt.setConnectionsDescendingNonStatic(connectionsDescending);
                       
        try {
        	LOG.info("Start Serialization...");
        	FileOutputStream fileOut = new FileOutputStream("timetable.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tt);
            out.close();
            fileOut.close();
            LOG.info("Serialized data is saved in timetable.ser");
        }catch(IOException i) {
        	i.printStackTrace();
        }
        
        
        
        

        store.close();  //brauchtes dich und sind dannach noch AgencyAndId objects noch erreichbar
        LOG.info("--------------TimetableStore closed -----------------------");
        /*
        tt.showStops();
        tt.showTrips();
        tt.showConnections();
        tt.showFootPaths();   
        
        System.out.println("-----getConnectionsAscending-----------");
        tt.showCon(tt.getConnectionsAscending());
        
        System.out.println("-----getConnectionsAscendingTest-----------");
        System.out.println(connectionsAscending.size());
        for(ConnectionCSA printcon:connectionsAscending){
            System.out.println("DepStop   "+printcon.getDepartureStop().getName()+"   DepartureTime "+printcon.gethDepartureTime()+":"+printcon.getMinDepartureTime()+":"+printcon.getsDepartureTime()+"  CON "+printcon);

        }
        
        System.out.println("-----getConnectionsDescending----------");
        //tt.showCon(tt.getConnectionsDescending());
        tt.showCon(connectionsAscending);
        System.out.println("---------------------------------------");
        
        for(ConnectionCSA con : tt.getConnectionsAscending()) {
        	System.out.println("FROM "+con.getDepartureStop().getName()+" to "+con.getArrivalStop().getName());
        	Calendar connectionDepTime = new GregorianCalendar(2018, 5, 19, con.gethDepartureTime(),con.getMinDepartureTime(),con.getsDepartureTime()); 
        	Calendar connectionArrTime = new GregorianCalendar(2018, 5, 19, con.gethArrivalTime(),con.getMinArrivalTime(),con.getsArrivalTime()); 
        	
        	System.out.println("ConnectionDepTime "+ connectionDepTime.getTimeInMillis());
        	System.out.println("ConnectionArrTime "+ connectionArrTime.getTimeInMillis());
        }
        
        */
        
    } 
    
    /**
     * loads the Data from SerializedObjectFile to create the TimeTable
     */
    public void loadFromSerializedObjectFile() throws JsonParseException, JsonMappingException, IOException{ //giveFilepath
    	try {
            FileInputStream fileIn = new FileInputStream("timetable.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tt = (TimeTable) in.readObject();
            in.close();
            fileIn.close();
    	}catch(IOException i) {
    		i.printStackTrace();
            return;
    	}catch(ClassNotFoundException c) {
    		LOG.info("timetable class not found");
            c.printStackTrace();
    	}
    	
    	tt.addConnectionsAscending(tt.getConnectionsAscendingNotStatic());
        tt.addConnectionsDescending(tt.getConnectionsDescendingNotStatic());
    	
        /*
        LOG.info("Show all Footpaths");
        for(FootpathCSA footpath :  tt.getFootpaths()){
            LOG.info("StopNameDeparture "+footpath.getDepartureStop().getName()+" StopNameArrival "+footpath.getArrivalStop().getName()+" Footpath.Duration "+footpath.getDuration());
            LOG.info("StopDepREF: "+footpath.getDepartureStop()+"  StopArrREF: "+footpath.getArrivalStop());
        }
    	
        LOG.info("Show all Stops");
        for(StopCSA stop :  tt.getStops()){
            LOG.info("StopId "+stop.getsAAId().getId()+" StopName "+stop.getName());
            LOG.info("--->StopRef: "+stop);
        }
        
        LOG.info("Show all Trips");
        for(TripCSA trip :  tt.getTrips()){
            LOG.info("TripId "+trip.gettAAId().getId()+" TripShortname "+trip.getTripShortName()+" TripHeadSign "+trip.getTripHeadSign());
            LOG.info("--->TripRef: "+trip+ "  tAAId: "+trip.gettAAId()+" rAAId: "+trip.getrAAId());
        }
    	
        LOG.info("Show all ConnectionsAscending");
        for(ConnectionCSA printcon :  tt.getConnectionsAscending()){
            LOG.info("DepStop   "+printcon.getDepartureStop().getName()+"   DepartureTime "+printcon.gethDepartureTime()+":"+printcon.getMinDepartureTime()+":"+printcon.getsDepartureTime()+"  CON "+printcon);
            LOG.info("--->ConRef:"+printcon+ "  StopDepRef:"+printcon.getDepartureStop()+"  StopArrRef:"+printcon.getArrivalStop());
        }
        LOG.info("Show all ConnectionsDescending");
        for(ConnectionCSA printcon :  tt.getConnectionsDescending()){
            LOG.info("DepStop   "+printcon.getDepartureStop().getName()+"   DepartureTime "+printcon.gethDepartureTime()+":"+printcon.getMinDepartureTime()+":"+printcon.getsDepartureTime()+"  CON "+printcon);
            LOG.info("--->ConRef:"+printcon+ "  StopDepRef:"+printcon.getDepartureStop()+"  StopArrRef:"+printcon.getArrivalStop());
        }  	
        */
    }
    
    /**
     * reverses the ascending Connections and filles them into the descending Connection-Set
     */
    public void createConnectionsDescending(){
        connectionsDescending = ((TreeSet<ConnectionCSA>)connectionsAscending).descendingSet();
    }
}