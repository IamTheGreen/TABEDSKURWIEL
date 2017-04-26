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
    public Days getUnfinishedEntry(){
        return model.getUnfinishedEntry();
    }

    @Override
    public void onCreate() {
        updateView(model.getUnfinishedEntry());
    }

    @Override
    public void onRestore(Days day) {

    }

    @Override
    public void onPause(Days day) {

    }

    @Override
    public void onSave(Days day) {

    }

    @Override
    public void updateView(Days days) {

    }
}































