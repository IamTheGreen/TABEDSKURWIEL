package realmtrial.tabedskurwiel.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mttx on 2017-04-21.
 */

public class WorkDay {
    private long id;
    private Date date;
    private List<Route>routeList;
    private boolean isFinished;

    public WorkDay(){
        this.routeList = new ArrayList<>();
        routeList.add(new Route(1));
//        routeList.add(new Route(2));
//        routeList.add(new Route(3));
//        routeList.add(new Route(4));
    }

    public long getId() {
        return id;
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

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = new ArrayList<>();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}