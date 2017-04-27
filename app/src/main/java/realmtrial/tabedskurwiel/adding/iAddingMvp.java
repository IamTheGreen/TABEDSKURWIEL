package realmtrial.tabedskurwiel.adding;

import java.util.List;

import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;
/**
 * Created by mttx on 2017-04-21.
 */

public interface iAddingMvp {

    interface Presenter {
        void onCreate();
        void onRestore(Days day);
        void onPause(Days day);
        void onSave(Days day);
        void updateView(Days days);
        UnfinishedWorkDay getUnfinishedEntry();
        void pushEntryToModel(UnfinishedWorkDay unfinishedWorkDay);
    }

    interface Model {
        void addOrUpdate(UnfinishedWorkDay unfinishedWorkDay);
        UnfinishedWorkDay getUnfinishedEntry();

    }

    interface iView {
        void onSaveButtonClick();
        void onStart();
        void onRestart();
        void updateView();
        void loadNotFinishedData(UnfinishedWorkDay day);
    }
}
