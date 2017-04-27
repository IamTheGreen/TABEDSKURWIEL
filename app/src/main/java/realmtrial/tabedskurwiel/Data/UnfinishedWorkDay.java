package realmtrial.tabedskurwiel.Data;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import realmtrial.tabedskurwiel.adding.Days;

/**
 * Created by mttx on 2017-04-21.
 */

public class UnfinishedWorkDay extends RealmObject implements Days {
    @PrimaryKey
    private long id;
    private Date date;
    private Date createdAt;
    private RealmList<Route> routeList;
    private TmpRoute tmpRoute;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TmpRoute getTmpRoute() {
        return tmpRoute;
    }

    public void setTmpRoute(TmpRoute tmpRoute) {
        this.tmpRoute = tmpRoute;
    }

    private boolean isFinished;

    public UnfinishedWorkDay(){
        this.id = 0;
        this.date = new Date();
        this.createdAt = new Date();
        this.routeList = new RealmList<>();
        this.tmpRoute = new TmpRoute();
        this.isFinished = false;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public long getId() {
        return id;
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
        return ""+id;
    }
}