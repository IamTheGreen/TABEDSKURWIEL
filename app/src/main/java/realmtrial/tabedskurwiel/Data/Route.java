package realmtrial.tabedskurwiel.Data;

import io.realm.RealmObject;

/**
 * Created by mttx on 2017-04-18.
 */

public class Route extends RealmObject {
    private int id;
    private String locationStart;
    private String locationStop;
    private int distance;
    private int hours;
    private int minutes;
    private boolean isComplete;

    public boolean isComplete() {
        if(locationStart.length()==0){isComplete=false;} else {isComplete=true;}
        if(locationStop.length()==0){isComplete=false;} else{isComplete=true;}
        if(String.valueOf(distance).length()==0){isComplete=false;}else{isComplete=true;}
        if(String.valueOf(hours).length()==0){isComplete=false;}else{isComplete=true;}
        if(String.valueOf(minutes).length()==0){isComplete=false;}else{isComplete=true;}
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Route() {
    }


    public Route(int id, String locationStart, String locationStop, int distance, int hours, int minutes) {
        this.id = id;
        this.locationStart = locationStart;
        this.locationStop = locationStop;
        this.distance = distance;
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationStart() {
        return locationStart;
    }

    public void setLocationStart(String locationStart) {
        this.locationStart = locationStart;
    }

    public String getLocationStop() {
        return locationStop;
    }

    public void setLocationStop(String locationStop) {
        this.locationStop = locationStop;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString(){
        return id +". " +getLocationStart() +" - " + getLocationStop() + "- " + getDistance()+" km " + getHours() +":" +getMinutes() + " czasu pracy.";
    }
}

