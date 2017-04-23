package realmtrial.tabedskurwiel.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mttx on 2017-04-21.
 */

public class WorkDay extends RealmObject{
    @PrimaryKey
    private long id;
    private Date date;
    private Date createdAt;
    private RealmList<Route> routeList;
    private boolean isFinished;

    public WorkDay(){
        this.id = 0;
        this.date = new Date();
        this.createdAt = new Date();
        this.routeList = new RealmList<>();
        this.isFinished = false;
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