package realmtrial.tabedskurwiel.Data;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mttx on 2017-04-27.
 */

public class Day extends RealmObject {
    @PrimaryKey
    private long id = 0;
    private MidPoint temporaryMidPoint = new MidPoint();
    private RealmList<MidPoint> midPoints = new RealmList<>();
    private boolean isFinished = false;
    private int startHour;
    private int startMinute;

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    private int endHour;
    private int endMinute;
    private boolean startTimeSet = false;
    private String startLocation;
    private String endLocation;

    public boolean isStartTimeSet() {
        return startTimeSet;
    }

    public void setStartTimeSet(boolean startTimeSet) {
        this.startTimeSet = startTimeSet;
    }

    private Date date;

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MidPoint getTemporaryMidPoint() {
        return temporaryMidPoint;
    }

    public void setTemporaryMidPoint(MidPoint temporaryMidPoint) {
        this.temporaryMidPoint = temporaryMidPoint;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        String startLoc = "";
        String endLoc = "";
        try{
            startLoc = midPoints.get(0).getLocationStart();
           endLoc = midPoints.get(midPoints.size()-1).getLocationStop();
        }catch (
                IndexOutOfBoundsException ex
                ){}
        int totalDistance = 0;

        for(MidPoint midPoint :midPoints){
            totalDistance += Integer.valueOf(midPoint.getDistance()) ;
        }
        return  startLoc.toUpperCase() +" do " +endLoc.toUpperCase() + ", " + String.valueOf(totalDistance) + "KM.";
    }

    public String getTotalDistance(){
        int td = 0;
        for (MidPoint midPoint : midPoints){
            td += Integer.valueOf(midPoint.getDistance());
        }
        return String.valueOf(td);
    }

    public String getTotalTime(){
        int hours = 0;
        int minutes = 0;
        for (MidPoint midPoint : midPoints){
            hours += Integer.valueOf(midPoint.getHours());
            minutes += Integer.valueOf(midPoint.getMinutes());
        }

        int hoursFromMinutes = minutes/60;
        int restMinutes = minutes % 60;
        int calculatored = hours + hoursFromMinutes;


        return String.valueOf(calculatored) + ":" + (restMinutes < 10 ? "0" : "") + String.valueOf(restMinutes);
    }
}
