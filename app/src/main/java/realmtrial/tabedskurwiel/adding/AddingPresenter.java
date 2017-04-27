package realmtrial.tabedskurwiel.adding;


import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;
import realmtrial.tabedskurwiel.adding.NewData.Day;

/**
 * Created by mttx on 2017-04-21.
 */

public class AddingPresenter implements iAddingMvp.Presenter{
    private iAddingMvp.iView mainView;
    private iAddingMvp.Model model;

    public AddingPresenter(iAddingMvp.iView mainView, iAddingMvp.Model model) {
        this.mainView = mainView;
        this.model = model;
    }
    @Override
    public void makeTestData(){
        Day day = new Day();
        day.setLocationStop("not finish");
        day.setLocationStart("I am");
        day.setFinished(false);
        model.addOrUpdate(day);
    }
    @Override
    public void updateModel(Day day){
        model.addOrUpdate(day);
        onCreate();
    }

    @Override
    public void onCreate() {
        // Check is there existist unfinished object.
        mainView.assignDayToHolder(model.getLastEntry());
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
    public void updateView() {

    }

}































