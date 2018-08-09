package org.opentripplanner.csa;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import org.onebusaway.gtfs.model.*;
import org.opentripplanner.api.model.*;
import org.opentripplanner.common.geometry.DirectionUtils;
import org.opentripplanner.common.geometry.GeometryUtils;
import org.opentripplanner.common.geometry.PackedCoordinateSequence;
import org.opentripplanner.common.model.P2;
import org.opentripplanner.profile.BikeRentalStationInfo;
import org.opentripplanner.routing.alertpatch.Alert;
import org.opentripplanner.routing.alertpatch.AlertPatch;
import org.opentripplanner.routing.bike_rental.BikeRentalStation;
import org.opentripplanner.routing.core.*;
import org.opentripplanner.routing.edgetype.*;
import org.opentripplanner.routing.error.TrivialPathException;
import org.opentripplanner.routing.graph.Edge;
import org.opentripplanner.routing.graph.Graph;
import org.opentripplanner.routing.graph.Vertex;
import org.opentripplanner.routing.location.TemporaryStreetLocation;
import org.opentripplanner.routing.services.FareService;
import org.opentripplanner.routing.spt.GraphPath;
import org.opentripplanner.routing.trippattern.TripTimes;
import org.opentripplanner.routing.vertextype.*;
import org.opentripplanner.util.PolylineEncoder;
import org.opentripplanner.util.model.EncodedPolylineBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opentripplanner.api.model.Leg;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.opentripplanner.routing.core.TraverseMode;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Mockup function of the JourneyToTripPlanConverter. It serialites the journeys and gives back a fixed TripPlan-Object.
 * @author Flavio
 *
 */

public class JourneyToTripPlanConverterMock {
    
	/**
	 * Mockup function of the JourneyToTripPlanConverter. It serialites the journeys and gives back a fixed TripPlan-Object.
	 * @param journeys
	 * @return fixed TripPlan-Object
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
    public static TripPlan generatePlan(Set<Journey> journeys) throws JsonGenerationException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.writeValue(new File("journeysMockJSON.txt"),journeys);
        
        
        TripPlan plan = new TripPlan();
        int year = 118;
        int month = 4;
        int tag = 9;
        int hrs = 12;
        int min = 17;
        Date datum = new Date(year,month,tag,hrs,min);
        plan.date = datum;
        System.out.println(plan.date);
        

        Double lonf = 9.63119748849971;
        Double latf = 47.4142968347706;
        String namef = "stop Heerbrugg, Dornacherhof ";
        Double lont = 9.55651155576762;
        Double latt = 47.3742890246126;
        String namet = "stop Altstätten SG ";
        Place from = new Place();
        from.name = namef;
        from.lon = lonf;
        from.lat = latf;
        from.orig = namef;
        from.vertexType = VertexType.NORMAL;
        //from.stopId = new AgencyAndId("1","8578433");
        plan.from = from;
        Place to = new Place();
        to.name = namet;
        to.lon = lont;
        to.lat = latt;
        to.orig = namet;
        to.vertexType = VertexType.NORMAL;
        //to.stopId = new AgencyAndId("1","8574137");
        plan.to = to;
        
        Itinerary itinerary = new Itinerary();
        long dauer = 349;
        itinerary.duration = dauer;

        
        Calendar startZeit = new GregorianCalendar(2018,4,9,12,17);
        Calendar stopZeit = new GregorianCalendar(2018,4,9,12,22,49);
        itinerary.startTime = startZeit;
        itinerary.endTime = stopZeit;
        itinerary.transitTime = 0;
        itinerary.walkTime = 360;
        itinerary.waitingTime = 0;
        itinerary.walkDistance = 460.26001370630223;
        itinerary.transfers = 0;
        
        Leg leg= new Leg();
        leg.startTime = startZeit;
        leg.endTime = stopZeit;
        leg.distance = 460.26001370630223;
        leg.mode = "WALK";
        leg.route = "";
        //leg.agencyName = "Bus Ostschweiz (Rheintal)";
        //leg.agencyUrl = "http://www.sbb.ch/";
        //leg.agencyTimeZoneOffset = 7200000;
        //leg.routeType = 700;
        AgencyAndId rId = new AgencyAndId("1","79-351-j18-1");
        //leg.routeId = rId;
        //leg.tripShortName = "31089";
        //leg.headsign = "Heerbrugg, Bahnhof";
        //leg.agencyId = "138";
        AgencyAndId tId = new AgencyAndId("1","15.TA.79-351-j18-1.1.R");
        //leg.tripId = tId;
        //leg.serviceDate = "20180409";
        Place f = new Place();
        f.name = "Heerbrugg, Dornacherhof";
        AgencyAndId sId = new AgencyAndId("1","8578433");
        //f.stopId = sId;
        f.lon = lonf;
        f.lat = latf;
        f.departure = startZeit;
        f.orig = "stop Heerbrugg, Dornacherhof ";
        //f.stopIndex = 6;
        //f.stopSequence = 7;
        f.vertexType = VertexType.NORMAL;
        leg.from = f;
        Place t = new Place();
        t.name = "Heerbrugg, Bahnhof";
        AgencyAndId sId2 = new AgencyAndId("1","8574137");
        //t.stopId = sId2;
        t.lon = 9.62778389041958;
        t.lat = 47.4108621837542;
        t.arrival = stopZeit;
        t.orig = "stop Heerbrugg, Bahnhof ";
        //t.stopIndex = 7;
        //t.stopSequence = 8;
        t.vertexType = VertexType.NORMAL;
        leg.to = t;
        EncodedPolylineBean legGeo = new EncodedPolylineBean();
        legGeo.setPoints("irk`H}axy@lThT");
        legGeo.setLength(2);
        leg.legGeometry = legGeo;
        //leg.routeShortName = "351";
        leg.rentedBike = false;
        leg.duration = 349.0;
        leg.transitLeg = false;
        WalkStep walkStep = new WalkStep();
        walkStep.distance = 460.26001370630223;
        walkStep.relativeDirection = RelativeDirection.DEPART;
        walkStep.streetName = "Heerbrugg, Dornacherhof => Heerbrugg, Bahnhof";
        walkStep.absoluteDirection = AbsoluteDirection.SOUTHWEST;
        walkStep.lon = 9.63119748849972;
        walkStep.lat = 47.4142968347706;
        List<WalkStep> walkSteps = new ArrayList<WalkStep>();
        walkSteps.add(walkStep);
        leg.walkSteps = walkSteps;
        itinerary.addLeg(leg);
        
        plan.addItinerary(itinerary);
        
        Itinerary itinerary2 = new Itinerary();
        dauer = 300;
        itinerary2.duration = dauer;
        startZeit = new GregorianCalendar(2018,4,9,12,37);
        stopZeit = new GregorianCalendar(2018,4,9,12,42);
        itinerary2.startTime = startZeit;
        itinerary2.endTime = stopZeit;
        itinerary2.transitTime = dauer;
        
        Leg leg2 = new Leg();
        leg2.startTime = startZeit;
        leg2.endTime = stopZeit;
        leg2.distance = 460.26001370630223;
        leg2.mode = "BUS";
        leg2.route = "304";
        leg2.agencyName = "Bus Ostschweiz (Rheintal)";
        leg2.agencyUrl = "http://www.sbb.ch/";
        leg2.agencyTimeZoneOffset = 3600000;
        leg2.routeType = 700;
        rId = new AgencyAndId("1","16-304-j18-1");
        leg2.routeId = rId;
        leg2.tripShortName = "14065";
        leg2.headsign = "Heerbrugg, Bahnhof";
        leg2.agencyId = "138";
        tId = new AgencyAndId("1","64.TA.16-304-j18-1.3.R");
        leg2.tripId = tId;
        leg2.serviceDate = "20180409";
        f = new Place();
        f.name = "Heerbrugg, Dornacherhof";
        sId = new AgencyAndId("1","8578433");
        f.stopId = sId;
        f.lon = lonf;
        f.lat = latf;
        f.departure = startZeit;
        f.orig = "stop Heerbrugg, Dornacherhof ";
        f.stopIndex = 15;
        f.stopSequence = 16;
        f.vertexType = VertexType.TRANSIT;
        leg2.from = f;
        t = new Place();
        t.name = "Heerbrugg, Bahnhof";
        sId2 = new AgencyAndId("1","8574137");
        t.stopId = sId2;
        t.lon = lont;
        t.lat = latt;
        t.arrival = stopZeit;
        t.orig = "stop Heerbrugg, Bahnhof ";
        t.stopIndex = 16;
        t.stopSequence = 17;
        t.vertexType = VertexType.TRANSIT;
        leg2.to = t;
        legGeo = new EncodedPolylineBean();
        legGeo.setPoints("irk`H}axy@lThT");
        legGeo.setLength(2);
        leg2.legGeometry = legGeo;
        leg2.routeShortName = "304";
        leg2.rentedBike = false;
        leg2.duration = 300.0;
        leg2.transitLeg = true;
        itinerary2.addLeg(leg2);
        plan.addItinerary(itinerary2);
        
        Itinerary itinerary3 = new Itinerary();
        dauer = 240;
        itinerary3.duration = dauer;
        startZeit = new GregorianCalendar(2018,4,9,12,54);
        stopZeit = new GregorianCalendar(2018,4,9,12,58);
        itinerary3.startTime = startZeit;
        itinerary3.endTime = stopZeit;
        itinerary3.transitTime = dauer;
        
        Leg leg3 = new Leg();
        leg3.startTime = startZeit;
        leg3.endTime = stopZeit;
        leg3.distance = 460.26001370630223;
        leg3.mode = "BUS";
        leg3.route = "351";
        leg3.agencyName = "Bus Ostschweiz (Rheintal)";
        leg3.agencyUrl = "http://www.sbb.ch/";
        leg3.agencyTimeZoneOffset = 7200000;
        leg3.routeType = 700;
        rId = new AgencyAndId("1","79-351-j18-1");
        leg3.routeId = rId;
        leg3.tripShortName = "31093";
        leg3.headsign = "Heerbrugg, Bahnhof";
        leg3.agencyId = "138";
        tId = new AgencyAndId("1","33.TA.79-351-j18-1.2.R");
        leg3.tripId = tId;
        leg3.serviceDate = "20180409";
        f = new Place();
        f.name = "Heerbrugg, Dornacherhof";
        sId = new AgencyAndId("1","8578433");
        f.stopId = sId;
        f.lon = lonf;
        f.lat = latf;
        f.departure = startZeit;
        f.orig = "stop Heerbrugg, Dornacherhof ";
        f.stopIndex = 19;
        f.stopSequence = 20;
        f.vertexType = VertexType.TRANSIT;
        leg3.from = f;
        t = new Place();
        t.name = "Heerbrugg, Bahnhof";
        sId2 = new AgencyAndId("1","8574137");
        t.stopId = sId2;
        t.lon = lont;
        t.lat = latt;
        t.arrival = stopZeit;
        t.orig = "stop Heerbrugg, Bahnhof ";
        t.stopIndex = 20;
        t.stopSequence = 21;
        t.vertexType = VertexType.TRANSIT;
        leg3.to = t;
        legGeo = new EncodedPolylineBean();
        legGeo.setPoints("irk`H}axy@lThT");
        legGeo.setLength(2);
        leg3.legGeometry = legGeo;
        leg3.routeShortName = "351";
        leg3.rentedBike = false;
        leg3.duration = 240.0;
        leg3.transitLeg = true;
        itinerary3.addLeg(leg3);
        plan.addItinerary(itinerary3);
        
        
        return plan;
    }
}
