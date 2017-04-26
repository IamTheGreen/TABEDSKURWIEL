package realmtrial.tabedskurwiel.adding;


import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;

/**
 * Created by mttx on 2017-04-21.
 */

public class AddingPresenter implements iAddingMvp.Presenter{
    private iAddingMvp.View mainView;
    private iAddingMvp.Model model;

    public AddingPresenter(iAddingMvp.View mainView, iAddingMvp.Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void pushEntryToModel(UnfinishedWorkDay unfinishedWorkDay){
        model.addOrUpdate(unfinishedWorkDay);
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































