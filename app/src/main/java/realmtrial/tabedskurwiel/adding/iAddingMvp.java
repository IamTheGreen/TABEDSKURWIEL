package realmtrial.tabedskurwiel.adding;

import io.realm.RealmList;
import realmtrial.tabedskurwiel.adding.NewData.Day;
import realmtrial.tabedskurwiel.adding.NewData.StartOrCLose;

/**
 * Created by mttx on 2017-04-21.
 */

public interface iAddingMvp {

    interface Presenter {
        void restoreOnLifeCycleEvent();
        void saveFinishedDay();
        void saveUnFinishedDay();
        void showClosingAlertDialog();
        void loadRandomData();
    }

    interface Model {
        void addOrUpdate(Day day);
        void addBunch(RealmList<Day> days);

        Day getLastEntry();
    }

    interface iView {
        void setCurrentDayTo(Day day);

        void refreshView();

        void updateView(String string);

        Day getCurrentDay();

        void showAlertDialog(final StartOrCLose startOrClose);
    }
}
