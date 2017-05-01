package realmtrial.tabedskurwiel.adding;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import realmtrial.tabedskurwiel.adding.NewData.Day;

/**
 * Created by mttx on 2017-04-21.
 */

public class AddingModel implements iAddingMvp.Model {
    private Realm realm;
    private Day day;

    public AddingModel() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void addOrUpdate(Day day) {
        long nextPrimaryKey;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        try{
            nextPrimaryKey = (long) realm.where(Day.class).max("id")+1;
        }catch (NullPointerException ex){
            nextPrimaryKey = 1;
        }

        if(day.getId()==0){
            day.setId(nextPrimaryKey);
        }

        realm.copyToRealmOrUpdate(day);
        realm.commitTransaction();
        realm.close();
    }

    public void addBunch(RealmList<Day> days){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(days);
        realm.commitTransaction();
        realm.close();
    }

    public Day getLastEntry(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try{
            day = realm.copyFromRealm(realm.where(Day.class).equalTo("isFinished",false).findFirst());
        } catch (IllegalArgumentException ex){
            day = new Day();
        }
        realm.commitTransaction();
        realm.close();
        return day;
    }

    public List<Day> getFullDayList(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults results = realm.where(Day.class).equalTo("isFinished",true).findAll();
        List<Day> days = new ArrayList<>();
        days.addAll(realm.copyFromRealm(results));
        realm.commitTransaction();
        realm.close();
        return days;
    }
}
















