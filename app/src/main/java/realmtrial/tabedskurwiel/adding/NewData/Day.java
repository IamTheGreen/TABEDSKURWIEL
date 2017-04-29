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
    private MidPoint temporaryMidPoint = new MidPoint();
    private RealmList<MidPoint> midPoints = new RealmList<>();
    private boolean isFinished = false;

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
        return "id: " + id + " z " + startLoc +" do " +endLoc + " Dystans: " + String.valueOf(totalDistance);
    }
}
