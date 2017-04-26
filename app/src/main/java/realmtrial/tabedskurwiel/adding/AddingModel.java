package realmtrial.tabedskurwiel.adding;

import java.util.Date;
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

    public AddingModel() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void addOrUpdate(UnfinishedWorkDay unfinishedWorkDay) {
        realm.beginTransaction();
        if(unfinishedWorkDay.isFinished()){
            long primaryKey;
            try{
                primaryKey = (long) realm.where(WorkDay.class).max("id")+1;
            }catch (NullPointerException ex){
                primaryKey = 1;
            }
            WorkDay workDay = new WorkDay();
            workDay.setId(primaryKey);
            workDay.setDate(new Date());
            workDay.setRouteList(unfinishedWorkDay.getRouteList());
            workDay.generateSummaryData();
            realm.copyToRealm(workDay);
        } else{
            realm.copyToRealmOrUpdate(unfinishedWorkDay);
        }
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
}
















