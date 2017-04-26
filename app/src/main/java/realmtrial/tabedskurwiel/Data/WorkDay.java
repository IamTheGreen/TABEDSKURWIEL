package realmtrial.tabedskurwiel.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import realmtrial.tabedskurwiel.adding.Days;

/**
 * Created by mttx on 2017-04-21.
 */

public class WorkDay extends RealmObject implements Days{
    @PrimaryKey
    private long id;
    private Date date;
    private Date createdAt;
    private RealmList<Route> routeList;
    private String dayStartLocation;
    private String dayEndLocation;
    private int totalDistance;
    private int totalTime;
    private boolean isFinished;

    public WorkDay(){
        this.id = 0;
        this.date = new Date();
        this.createdAt = new Date();
        this.routeList = new RealmList<>();
        this.isFinished = false;
    }

    public void generateSummaryData(){
        Route route;
        int minutes =0;
        for(RealmObject realm : routeList){
            route = (Route) realm;
            totalDistance +=route.getDistance();
            minutes  +=route.getMinutes();
            totalTime +=route.getHours();
        }
        dayStartLocation = routeList.get(0).getLocationStart();
        dayEndLocation = routeList.get(routeList.size()-1).getLocationStop();
        totalTime = totalTime + minutes/60;
    }

    public long getId() {
        return id;
    }

    public String getDayStartLocation() {
        return dayStartLocation;
    }

    public void setDayStartLocation(String dayStartLocation) {
        this.dayStartLocation = dayStartLocation;
    }

    public String getDayEndLocation() {
        return dayEndLocation;
    }

    public void setDayEndLocation(String dayEndLocation) {
        this.dayEndLocation = dayEndLocation;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public RealmList<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(RealmList<Route> routeList) {
        this.routeList = routeList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
    @Override
    public String toString(){
        return ""+id + dayStartLocation + " - " +dayEndLocation + "Dystans: "+totalDistance +"KM. - Czas Jazdy:"+ totalTime;
    }
}