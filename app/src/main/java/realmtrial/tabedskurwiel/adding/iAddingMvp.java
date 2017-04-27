package realmtrial.tabedskurwiel.adding;

import java.util.List;

import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;
import realmtrial.tabedskurwiel.Data.WorkDay;
import realmtrial.tabedskurwiel.adding.NewData.Day;

/**
 * Created by mttx on 2017-04-21.
 */

public interface iAddingMvp {

    interface Presenter {
        void onCreate();
        void onRestore(Days day);
        void onPause(Days day);
        void onSave(Days day);
        void updateView();
        void makeTestData();


        void updateModel(Day day);
    }

    interface Model {
        void addOrUpdate(Day day);
        Day getLastEntry();
    }

    interface iView {
        void onSaveButtonClick();
        void assignDayToHolder(Day day);
        void refreshView();
        void onStart();
        void onRestart();
        void updateView();
    }
}
