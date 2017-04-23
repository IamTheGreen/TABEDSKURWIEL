package realmtrial.tabedskurwiel.adding;
import io.realm.Realm;
import realmtrial.tabedskurwiel.Data.WorkDay;


/**
 * Created by mttx on 2017-04-21.
 */

public class AddingPresenter implements iAddingMvp.Presenter{
    private iAddingMvp.View view;
    private iAddingMvp.Model model;

    public AddingPresenter(iAddingMvp.View view, iAddingMvp.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void updateModel(WorkDay workDay) {
        model.addOrUpdate(workDay);
    }

    @Override
    public void onCreate() {
        view.setWorkDayHolder(model.getUnfinishedEntry());
        view.updateView();
    }

    @Override
    public void onPause(WorkDay workDay){
        model.addOrUpdate(workDay);
    }
    @Override
    public long getNextPrimaryKey(){
        long key;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            key = realm.where(WorkDay.class).max("id").longValue() + 1;
        } catch(NullPointerException ex) {
            key = 0;
        }
        view.updateStatusBar(""+key);
        realm.commitTransaction();
        return key;
    }
}





























