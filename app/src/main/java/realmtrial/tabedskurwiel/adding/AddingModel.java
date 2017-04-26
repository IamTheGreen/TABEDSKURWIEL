package realmtrial.tabedskurwiel.adding;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;
import realmtrial.tabedskurwiel.Data.WorkDay;

/**
 * Created by mttx on 2017-04-21.
 */

public class AddingModel implements iAddingMvp.Model {
    private Realm realm;
    private long primaryKeyIncremented;

    public AddingModel() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void addOrUpdate(Days day) {
        realm.beginTransaction();
        WorkDay workDay = (WorkDay) day;
        realm.copyToRealmOrUpdate(workDay);
        realm.commitTransaction();
    }

    public UnfinishedWorkDay getUnfinishedEntry(){
        UnfinishedWorkDay unfinishedWorkDay;
        realm.beginTransaction();
        UnfinishedWorkDay result = realm.where(UnfinishedWorkDay.class).findFirst();

        try{
            unfinishedWorkDay = realm.copyFromRealm(result);
        }catch (IllegalArgumentException ex){
            unfinishedWorkDay = new UnfinishedWorkDay();
//            unfinishedWorkDay.setId(getPrimaryKey());
        }
        realm.commitTransaction();
        return unfinishedWorkDay;
    }

    public List<WorkDay> getAllEntries(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults results = realm.where(WorkDay.class).findAll();
        List<WorkDay> lista = realm.copyFromRealm(results);
        realm.commitTransaction();
        return lista;
    }
    @Override
    public long getPrimaryKey(){
     //   Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        long primaryKey;
        primaryKey = (long) realm.where(WorkDay.class).max("id");
        realm.commitTransaction();
        return primaryKey;
    }
}
















