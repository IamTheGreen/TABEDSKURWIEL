package realmtrial.tabedskurwiel.listView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import realmtrial.tabedskurwiel.Data.Day;

/**
 * Created by mttx on 2017-04-30.
 */

public class ListViewModel implements iListView.Model {
    private Realm realm;

    @Override
    public List<Day> getFilteredByDate(iFilterQuery filterQuery) {
        List<Day> daylist;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults results;

        if(filterQuery.getQueryTag() == null){
            results = realm.where(Day.class)
                    .between("date", filterQuery.getFromDate(), filterQuery.getToDate())
                    .findAll();
        } else{
            results = realm.where(Day.class)
                    .between("date", filterQuery.getFromDate(), filterQuery.getToDate())
                    .equalTo("midPoints.locationStart", filterQuery.getQueryTag())
                    .or()
                    .equalTo("midPoints.locationStop", filterQuery.getQueryTag())
                    .findAll();
        }

        daylist = realm.copyFromRealm(results);
        realm.commitTransaction();
        realm.close();
        return daylist;
    }

    @Override
    public int getFilteredByDateSize(iFilterQuery filterQuery) {
        List<Day> daylist;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults results;
        if(filterQuery.getQueryTag() == null){
            results = realm.where(Day.class)
                    .between("date", filterQuery.getFromDate(), filterQuery.getToDate())
                    .findAll();
        } else{
            results = realm.where(Day.class)
                    .between("date", filterQuery.getFromDate(), filterQuery.getToDate())
                    .equalTo("midPoints.locationStart", filterQuery.getQueryTag(), Case.INSENSITIVE)
                    .or()
                    .equalTo("midPoints.locationStop", filterQuery.getQueryTag(),Case.INSENSITIVE)
                    .findAll();
        }

        daylist = realm.copyFromRealm(results);
        realm.commitTransaction();
        realm.close();
        return daylist.size();
    }


    public List<Day> getAll() {
        List<Day> daylist = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        daylist = realm.copyFromRealm(realm.where(Day.class).findAll());
        realm.commitTransaction();
        realm.close();
        return daylist;
    }
    @Override
    public List<Day> transaction(final iFilterQuery query){
        final List<Day> listDay = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               RealmResults results = realm.where(Day.class).equalTo("midPoints,locationStart",query.getQueryTag()).findAll();
                listDay.addAll(realm.copyFromRealm(results));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
        return listDay;
    }

    public Day getDay(long primaryKey){
        Day day;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        day = realm.where(Day.class).equalTo("id",primaryKey).findFirst();
        realm.commitTransaction();
        realm.close();
        return day;
    }
}
