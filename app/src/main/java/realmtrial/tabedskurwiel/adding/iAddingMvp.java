package realmtrial.tabedskurwiel.adding;

import java.util.List;
import realmtrial.tabedskurwiel.Data.WorkDay;

/**
 * Created by mttx on 2017-04-21.
 */

public interface iAddingMvp {

    interface Presenter{
        void updateModel(WorkDay workDay);
        long getNextPrimaryKey();
        void onPause(WorkDay workDay);
        void onCreate();
    }
    interface Model{
        void addOrUpdate(WorkDay workDay);
        WorkDay getUnfinishedEntry();
    }
    interface View{
        void onSaveButtonClick();
        void setWorkDayHolder(WorkDay workDay);
        WorkDay buildData();
        void updateView();
        void updateStatusBar(String s);
    }
}
