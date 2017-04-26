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
        Days getUnfinishedEntry();
    }

    interface Model {
        void addOrUpdate(Days day);
        UnfinishedWorkDay getUnfinishedEntry();
        long getPrimaryKey();
    }

    interface View {
        void onSaveButtonClick();
        void onStart();
        void onRestart();
        void updateView();
    }
}
