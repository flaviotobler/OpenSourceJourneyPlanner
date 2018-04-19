package org.opentripplanner.csa;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.glassfish.grizzly.http.server.Request;
import org.opentripplanner.api.model.Itinerary;
import org.opentripplanner.api.model.Leg;
import org.opentripplanner.api.model.Place;
import org.opentripplanner.api.model.TripPlan;
import org.opentripplanner.api.resource.CoordinateArrayListSequence;
import com.vividsolutions.jts.geom.Coordinate;
import org.opentripplanner.common.geometry.GeometryUtils;
import org.opentripplanner.util.PolylineEncoder;
import org.opentripplanner.util.model.EncodedPolylineBean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vividsolutions.jts.geom.Geometry;

public class JourneyToTripPlanConverter {
    
    public static TripPlan generatePlan(Set<Journey> journeys, Request request) throws JsonGenerationException, JsonMappingException, IOException
    {
        TripPlan plan = getPlanInfo(request);
        
        
        for(Journey journey: journeys)
        {
            Itinerary itinerary = new Itinerary();
            //Todo Generate Itinerary Information
            
            Footpath startpath = journey.getStartPath();
            Leg startLeg = legFromFP(startpath);
            itinerary.addLeg(startLeg);
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
                Leg leg = legFromLeg(legcsa);
                itinerary.addLeg(leg);
                itinerary.transitTime = itinerary.transitTime + (long) leg.duration;
                itinerary.waitingTime = itinerary.waitingTime + ((leg.startTime.getTimeInMillis() - itinerary.endTime.getTimeInMillis())/1000);
                itinerary.transfers = itinerary.transfers + 1;
                
                Footpath footpath = jp.getFootpath();
                Leg walkLeg = legFromFP(footpath);
                itinerary.addLeg(walkLeg);
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
    

    private static TripPlan getPlanInfo(Request request) {
        TripPlan plan = new TripPlan();
        plan.date = getPlanDate(request);
        plan.from = getStartPlace(request);
        plan.to = getEndPlace(request);
        return null;
    }


    private static Place getEndPlace(Request request) {
        // TODO Auto-generated method stub
        return null;
    }


    private static Place getStartPlace(Request request) {
        // TODO Auto-generated method stub
        return null;
    }


    private static Date getPlanDate(Request request) {
        // TODO Auto-generated method stub
        return null;
    }


    private static Leg legFromLeg(LegCSA legcsa) {
        Leg leg = new Leg();
        Connection center = legcsa.getEnter();
        leg.startTime = center.getDepartureTime();
        Connection cexit = legcsa.getExit();
        leg.endTime = cexit.getArrivalTime();
        Trip trip = cexit.getTrip();
        
        leg.mode = trip.getMode();
        leg.route = trip.getRoute();
        leg.agencyName = trip.getAgencyName();
        leg.agencyUrl = trip.getAgencyUrl();
        leg.agencyTimeZoneOffset = getTimeZone(); //getTimeZone form the Date (Summertime)
        leg.routeType = trip.getRouteType();
        leg.routeId = trip.getRouteId();
        leg.tripShortName = trip.getShortName();
        leg.headsign = trip.getHeadsign();
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

    private static EncodedPolylineBean getLegGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    private static Leg legFromFP(Footpath startpath) {
        // TODO Auto-generated method stub
        return null;
    }


    public static Place getPlace(Stop stop){
        Place place = new Place();
        place.name = stop.getName();
        place.lon = stop.getLon();
        place.lat = stop.getLat();
        
        return place;
    }
    
    
    


}
