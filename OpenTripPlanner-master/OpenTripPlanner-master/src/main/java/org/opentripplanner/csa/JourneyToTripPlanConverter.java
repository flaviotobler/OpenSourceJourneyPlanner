package org.opentripplanner.csa;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.glassfish.grizzly.http.server.Request;
import org.opentripplanner.api.model.Itinerary;
import org.opentripplanner.api.model.Leg;
import org.opentripplanner.api.model.Place;
import org.opentripplanner.api.model.TripPlan;
import org.opentripplanner.util.model.EncodedPolylineBean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JourneyToTripPlanConverter {
    
    public static TripPlan generatePlan(Set<Journey> journeys, Request request) throws JsonGenerationException, JsonMappingException, IOException
    {
        TripPlan plan = getPlanInfo(request);
        
        
        for(Journey journey: journeys)
        {
            Itinerary itinerary = getItineraryInfo(journey);
            //Todo Generate Itinerary Information
            
            Footpath startpath = journey.getStartPath();
            // Todo Generate leg and steps from footpath
            Leg startLeg = legFromFP(startpath);
            itinerary.addLeg(startLeg);
            
            for(JourneyPointer jp: journey.getJourneyPointers())
            {
                
                LegCSA legcsa = jp.getLeg();
                Leg leg = legFromLeg(legcsa);
                itinerary.addLeg(leg);
                
                Footpath footpath = jp.getFootpath();
                Leg walkLeg = legFromFP(startpath);
                itinerary.addLeg(walkLeg);
                
                
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
        
        leg.serviceDate = getDate(); //getDate from request
        leg.legGeometry = getLegGeometry();
        leg.duration = getDuration();
        leg.transitLeg = isTransit();
        
        
        leg.from = getPlace(center.getDepartureStop());
        leg.to = getPlace(cexit.getArrivalStop());
        return null;
    }

    private static String getDate() {
        // TODO Auto-generated method stub
        return null;
    }

    private static Boolean isTransit() {
        // TODO Auto-generated method stub
        return null;
    }

    private static double getDuration() {
        // TODO Auto-generated method stub
        return 0;
    }

    private static EncodedPolylineBean getLegGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    private static Leg legFromFP(Footpath startpath) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Itinerary getItineraryInfo(Journey journey) {
        Itinerary itinerary = new Itinerary();
        
        
        return itinerary;
    }

    public static Place getPlace(Stop stop){
        Place place = new Place();
        place.name = stop.getName();
        place.lon = stop.getLon();
        place.lat = stop.getLat();
        
        return place;
    }
    
    
    


}
