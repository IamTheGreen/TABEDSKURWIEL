package realmtrial.tabedskurwiel.adding.NewData;

import io.realm.RealmObject;

/**
 * Created by mttx on 2017-04-27.
 */

public class MidPoint extends RealmObject {
    private String locationMidPoint;
    private String distance;
    private String hours;
    private String minutes;

    public String getLocationMidPoint() {
        return locationMidPoint;
    }

    public void setLocationMidPoint(String locationMidPoint) {
        this.locationMidPoint = locationMidPoint;
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
}
