package realmtrial.tabedskurwiel.adding;

import java.util.IllegalFormatCodePointException;

import io.realm.Realm;
import realmtrial.tabedskurwiel.Data.WorkDay;

/**
 * Created by mttx on 2017-04-21.
 */

public class AddingModel implements iAddingMvp.Model {
    private Realm realm;

    public AddingModel() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void addOrUpdate(WorkDay workDay) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(workDay);
        realm.commitTransaction();
    }

    public WorkDay getUnfinishedEntry(){
        WorkDay workDay;
        realm.beginTransaction();
        WorkDay result = realm.where(WorkDay.class).equalTo("isFinished",false).findFirst();

        try{
            workDay = realm.copyFromRealm(result);
        }catch (IllegalArgumentException ex){
            workDay = new WorkDay();
        }
        realm.commitTransaction();
        return workDay;
    }
}
















