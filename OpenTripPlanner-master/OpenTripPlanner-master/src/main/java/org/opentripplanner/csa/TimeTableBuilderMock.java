package org.opentripplanner.csa;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTableBuilderMock {
    
    public TimeTable createTimeTable(){
        /*Stop testStop = new Stop("Heerbrugg Dornacherhof", 9.63119748849971, 47.4142968347706);
        Stop testStop2 = new Stop("Heerbrugg Bahnhof", 9.62778389041958, 47.4108621837542);
        Trip testTrip = new Trip(1, "DerBus");
        Calendar startZeit = new GregorianCalendar(2018,04,02,12,37);
        Calendar stopZeit = new GregorianCalendar(2018,04,02,12,42);
        Footpath testFootpath = new Footpath(testStop, testStop, 3);
        Footpath testFootpath2 = new Footpath(testStop2, testStop2, 5);
        Connection testConnection = new Connection(testStop, testStop2, startZeit, stopZeit, testTrip);
        TimeTable timeTable = new TimeTable();
        timeTable.addConnection(testConnection);
        timeTable.addStop(testStop);
        timeTable.addStop(testStop2);
        timeTable.addTrip(testTrip);
        timeTable.addFootpaths(testFootpath);
        timeTable.addFootpaths(testFootpath2);
        return timeTable;
        */
        
        StopCSA testStop = new StopCSA("8509991","Schaan Bahnhof", 9.50862236796454, 47.1682614512578);
        StopCSA testStop2 = new StopCSA("8509612","Bendern Post", 9.50272941969989, 47.2104330642043);
        TripCSA testTrip = new TripCSA(1, "DerBus");
        Calendar startZeit = new GregorianCalendar(2018,04,02,12,37);
        Calendar stopZeit = new GregorianCalendar(2018,04,02,12,42);
        Footpath testFootpath = new Footpath(testStop, testStop, 3);
        Footpath testFootpath2 = new Footpath(testStop2, testStop2, 5);
        Connection testConnection = new Connection(testStop, testStop2, startZeit, stopZeit, testTrip);
        TimeTable timeTable = new TimeTable();
        timeTable.addConnection(testConnection);
        timeTable.addStop(testStop);
        timeTable.addStop(testStop2);
        timeTable.addTrip(testTrip);
        timeTable.addFootpaths(testFootpath);
        timeTable.addFootpaths(testFootpath2);
        return timeTable; 
        
    }
}
