package realmtrial.tabedskurwiel.adding;

import realmtrial.tabedskurwiel.Data.Route;

/**
 * Created by mttx on 2017-04-21.
 */

public interface iAddingMvp {

    interface Presenter{
        void updateModel(Route route);
        void updateView(String string);
    }
    interface Model{

    }
    interface View{
       void onSaveButtonClick();
    }
}
