package realmtrial.tabedskurwiel.Data;

/**
 * Created by mttx on 2017-04-18.
 */

public class Route {
    private int id;
    private String locationStart;
    private String locationStop;
    private int distance;
    private int hours;
    private int minutes;
    private int time;

    public Route() {
    }

    public Route(int id) {
        this.id = id;
    }

    public Route(int id, String locationStart, String locationStop, int distance, int hours, int minutes, int time) {
        this.id = id;
        this.locationStart = locationStart;
        this.locationStop = locationStop;
        this.distance = distance;
        this.hours = hours;
        this.minutes = minutes;
        this.time = time;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return id +". " +getLocationStart() +" - " + getLocationStop() + "- " + getDistance()+" km " + getHours() +":" +getMinutes() + " czasu pracy.";
    }
}

