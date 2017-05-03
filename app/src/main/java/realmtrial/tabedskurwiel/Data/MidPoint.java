package realmtrial.tabedskurwiel.Data;

import io.realm.RealmObject;

/**
 * Created by mttx on 2017-04-27.
 */

public class MidPoint extends RealmObject {
    private int midPointId;
    private String locationStart;
    private String locationStop;
    private String distance;
    private String hours;
    private String minutes;

    public int getMidPointId() {
        return midPointId;
    }
    public void setMidPointId(int midPointId) {
        this.midPointId = midPointId;
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


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }
    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String toAddingListView(){
        return midPointId +"." + locationStart.toUpperCase() + "-" + locationStop.toUpperCase() +".  "+ distance + "km.";
    }


}
