package org.opentripplanner.csa;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.glassfish.grizzly.http.server.Request;
import org.opentripplanner.api.model.AbsoluteDirection;
import org.opentripplanner.api.model.Itinerary;
import org.opentripplanner.api.model.Leg;
import org.opentripplanner.api.model.Place;
import org.opentripplanner.api.model.RelativeDirection;
import org.opentripplanner.api.model.TripPlan;
import org.opentripplanner.api.model.WalkStep;
import org.opentripplanner.api.resource.CoordinateArrayListSequence;
import com.vividsolutions.jts.geom.Coordinate;
import org.opentripplanner.common.geometry.GeometryUtils;
import org.opentripplanner.routing.core.RoutingRequest;
import org.opentripplanner.util.PolylineEncoder;
import org.opentripplanner.util.model.EncodedPolylineBean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vividsolutions.jts.geom.Geometry;

/**
 * The CSA gives back his results in form of Journeys. The Website needs the Result in form of a TripPlan
 * with Itinerarys. This Class converts Journeys to a TripPlan.
 * 
 * @author flavio
 */
public class JourneyToTripPlanConverter {
    
    /**
     * Converts Journeys into a TripPlan. The request is needed to get the Date which is not saved in the Journeys.
     * 
     * @param journeys
     * @param request
     * @return plan
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static TripPlan generatePlan(Set<Journey> journeys, RoutingRequest request) throws JsonGenerationException, JsonMappingException, IOException
    {
        TripPlan plan = getPlanInfo(request);
        Date datum = plan.date;
        
        
        for(Journey journey: journeys)
        {
            Itinerary itinerary = new Itinerary();     
            FootpathCSA startpath = journey.getStartPath();
            Leg startLeg = legFromFP(startpath, datum);
            if(startLeg.distance != 0)
            {
                itinerary.addLeg(startLeg);
            }
            itinerary.startTime = startLeg.startTime;
            itinerary.endTime = startLeg.endTime;
            itinerary.transitTime = 0;
            itinerary.walkTime = (long) startLeg.duration;
            itinerary.waitingTime = 0;
            itinerary.walkDistance = startLeg.distance;
            itinerary.transfers = -1;
       
            
            for(JourneyPointer jp: journey.getJourneyPointers())
            {
                
                LegCSA legcsa = jp.getLeg();
                Leg leg = legFromLeg(legcsa, datum);
                itinerary.addLeg(leg);
                itinerary.transitTime = itinerary.transitTime + (long) leg.duration;
                itinerary.waitingTime = itinerary.waitingTime + ((leg.startTime.getTimeInMillis() - itinerary.endTime.getTimeInMillis())/1000);
                itinerary.transfers = itinerary.transfers + 1;
                
                FootpathCSA footpath = jp.getFootpath();
                Leg walkLeg = legFromFP(footpath, datum);
                if(walkLeg.distance != 0)
                {
                    itinerary.addLeg(walkLeg);
                }
                
                itinerary.endTime = walkLeg.endTime;
                itinerary.walkTime = itinerary.walkTime + (long) walkLeg.duration;
                itinerary.walkDistance = itinerary.walkDistance + walkLeg.distance;
                
                
            }
            itinerary.duration = itinerary.transitTime + itinerary.walkTime + itinerary.waitingTime;
            if(itinerary.transfers <= 0){
                itinerary.transfers = 0;
            }
        }
        
        return plan;
    }
    

    private static TripPlan getPlanInfo(RoutingRequest request) {
        TripPlan plan = new TripPlan();
        plan.date = getPlanDate(request);
        plan.from = getStartPlace(request);
        plan.to = getEndPlace(request);
        return null;
    }


    private static Place getEndPlace(RoutingRequest request) {
        Place to = new Place();
        to.name = request.to.name;
        to.lon = request.to.lng;
        to.lat = request.to.lat;
        return to;
    }


    private static Place getStartPlace(RoutingRequest request) {
        Place from = new Place();
        from.name = request.from.name;
        from.lon = request.from.lng;
        from.lat = request.from.lat;
        return from;
    }


    private static Date getPlanDate(RoutingRequest request) {
        Date date = new Date();
        date = request.getDateTime();
        return date;
    }


    private static Leg legFromLeg(LegCSA legcsa, Date datum) {
        Leg leg = new Leg();
        ConnectionCSA center = legcsa.getEnter();
        int year = datum.getYear();
        int month = datum.getMonth();
        int day = datum.getDate();
        int hour = center.gethDepartureTime();
        int minute = center.getMinDepartureTime();     
        leg.startTime = new GregorianCalendar(year,month,day,hour,minute);
        ConnectionCSA cexit = legcsa.getExit();
        hour = cexit.gethArrivalTime();
        minute = cexit.getMinArrivalTime();
        leg.endTime = new GregorianCalendar(year,month,day,hour,minute);
        TripCSA trip = cexit.getTrip();
        
        leg.mode = trip.getMode();
        leg.route = trip.getRoute();
        leg.agencyName = trip.getAgencyName();
        leg.agencyUrl = trip.getAgencyUrl();
        leg.agencyTimeZoneOffset = getTimeZone(trip.getAgencyTimeZoneOffset()); //getTimeZone form the Date (Summertime)
        leg.routeType = trip.getRouteType();
        leg.routeId = trip.getRouteId();
        leg.tripShortName = trip.getRouteShortName();
        leg.headsign = trip.getTripHeadSign();
        leg.agencyId = trip.getAgencyId();
        leg.tripId = trip.getTripId();
        leg.routeShortName = trip.getRouteShortName();
        
        leg.serviceDate = getDate(leg.startTime); //getDate from request
        leg.duration = getDuration(leg.startTime, leg.endTime);
        leg.transitLeg = true;
        
        
        leg.from = getPlace(center.getDepartureStop());
        leg.to = getPlace(cexit.getArrivalStop());
        Coordinate startCor = new Coordinate(leg.from.lon, leg.from.lat, 0);
        Coordinate endCor = new Coordinate(leg.to.lon, leg.to.lat, 0);
        CoordinateArrayListSequence coordinates = new CoordinateArrayListSequence();
        Geometry geometry = GeometryUtils.getGeometryFactory().createLineString(coordinates);
        leg.legGeometry = PolylineEncoder.createEncodings(geometry);
        return leg;
    }

    private static int getTimeZone(String timeZone) {
        ZoneOffset zone = ZoneOffset.of(timeZone);
        return zone.getTotalSeconds()*1000;
    }


    private static String getDate(Calendar dateTime) {
        int year = dateTime.get(Calendar.YEAR);
        int month = dateTime.get(Calendar.MONTH);
        int day = dateTime.get(Calendar.DAY_OF_MONTH);
        String sD = ""+year+month+day;
        return sD;
    }


    private static double getDuration(Calendar start, Calendar end) {
        double duration = ((end.getTimeInMillis() - start.getTimeInMillis())/1000);
        return duration;
    }

    private static Leg legFromFP(FootpathCSA footpath, Date datum) {
        Leg leg = new Leg();
        leg.distance = getDistance(footpath);
        leg.agencyTimeZoneOffset = getTimeZone(); // Timezoneoffset für schweitzer system machen bei walk?
        //leg.startTime = todo
        //leg.endTime = todo
        leg.mode = "WALK";
        leg.from = getPlace(footpath.getDepartureStop());
        leg.to = getPlace(footpath.getArrivalStop());
        // Achtung vieleicht Fehler
        Coordinate startCor = new Coordinate(leg.from.lon, leg.from.lat, 0);
        Coordinate endCor = new Coordinate(leg.to.lon, leg.to.lat, 0);
        CoordinateArrayListSequence coordinates = new CoordinateArrayListSequence();
        Geometry geometry = GeometryUtils.getGeometryFactory().createLineString(coordinates);
        leg.legGeometry = PolylineEncoder.createEncodings(geometry);
        WalkStep walkStep = new WalkStep();
        walkStep.distance = leg.distance;
        walkStep.streetName = "" + footpath.getDepartureStop().getName() + " => " + footpath.getArrivalStop().getName();
        walkStep.absoluteDirection = getAbsoluteDirection(footpath);
        walkStep.relativeDirection = RelativeDirection.DEPART;
        walkStep.lon = footpath.getDepartureStop().getLon();
        walkStep.lat = footpath.getDepartureStop().getLat();
        List<WalkStep> walkSteps = new ArrayList<WalkStep>();
        walkSteps.add(walkStep);
        leg.walkSteps = walkSteps;
        return leg;
    }


    private static int getTimeZone() {
        
        return 7200000;
    }

    /**
     * 
     * @param footpath
     * @return
     */
    private static AbsoluteDirection getAbsoluteDirection(FootpathCSA footpath) {
        double lat1 = footpath.getDepartureStop().getLat();
        double lat2 = footpath.getArrivalStop().getLat();
        double lon1 = footpath.getDepartureStop().getLon();
        double lon2 = footpath.getArrivalStop().getLon();
        double radians = Math.atan2(lon2-lon1, lat2-lat1);
        double compassReading = radians * (180 / Math.PI);
        int coordIndex = (int) Math.round(compassReading / 45);
        if(coordIndex < 0)
        {
            coordIndex = coordIndex + 8;
        }
        AbsoluteDirection aDir = null;
        switch (coordIndex) {
            case 0: aDir = AbsoluteDirection.NORTH;
                    break;
            case 1: aDir = AbsoluteDirection.NORTHEAST;
                    break;
            case 2: aDir = AbsoluteDirection.EAST;
                    break;
            case 3: aDir = AbsoluteDirection.SOUTHEAST;
                    break;
            case 4: aDir = AbsoluteDirection.SOUTH;
                    break;
            case 5: aDir = AbsoluteDirection.SOUTHWEST;
                    break;
            case 6: aDir = AbsoluteDirection.WEST;
                    break;
            case 7: aDir = AbsoluteDirection.NORTHWEST;
                    break;
            case 8: aDir = AbsoluteDirection.NORTH;
        }
        return aDir;
    }


    private static double getDistance(FootpathCSA footpath) {
        double lat1 = footpath.getDepartureStop().getLat();
        double lat2 = footpath.getArrivalStop().getLat();
        double lon1 = footpath.getDepartureStop().getLon();
        double lon2 = footpath.getArrivalStop().getLon();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344 / 1000;
        return dist;
    }
    
    
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    public static Place getPlace(StopCSA stop){
        Place place = new Place();
        place.name = stop.getName();
        place.lon = stop.getLon();
        place.lat = stop.getLat();
        
        return place;
    }
    
    
    


}
