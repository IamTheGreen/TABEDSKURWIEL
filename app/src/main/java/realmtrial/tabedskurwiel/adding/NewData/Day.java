package realmtrial.tabedskurwiel.adding.NewData;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mttx on 2017-04-27.
 */

public class Day extends RealmObject {
    @PrimaryKey
    private long id = 0;
    private String locationStart;
    private String locationStop;
    private String distance;
    private String hours;
    private String minutes;
    private RealmList<MidPoint> midPoints = new RealmList<>();
    private boolean isFinished;

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

    public long getId() {
        return id;

    }

    public void setId(long id) {
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public RealmList<MidPoint> getMidPoints() {
        return midPoints;
    }

    public void setMidPoints(RealmList<MidPoint> midPoints) {
        this.midPoints = midPoints;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String toStrongs(){
        return "key:"+id +" / "+ locationStart+ "/ is Finished: " + isFinished();
    }
}
