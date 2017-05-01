package realmtrial.tabedskurwiel.adding.listView;

import java.util.Date;
import java.util.List;

import realmtrial.tabedskurwiel.adding.NewData.Day;

/**
 * Created by mttx on 2017-04-30.
 */

public interface ListView {
    interface Model{
        void getAllEnties();
        void getFilteredByDate(Date beginDate, Date endDate);
        void getFilteredByLocation(String string);
    }
    interface Presenter{}

    interface View{
        void updateView(List<Day> queryResults);

    }
}
