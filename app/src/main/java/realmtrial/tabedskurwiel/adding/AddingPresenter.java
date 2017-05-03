package realmtrial.tabedskurwiel.adding;
import realmtrial.tabedskurwiel.Randomizator;
import realmtrial.tabedskurwiel.Data.StartOrCLose;

/**
 * Created by mttx on 2017-04-21.
 */


public class AddingPresenter implements iAddingMvp.Presenter{
    private iAddingMvp.iView mainView;
    private iAddingMvp.Model model;
    private Randomizator randomizator = new Randomizator();

    public AddingPresenter(iAddingMvp.iView mainView, iAddingMvp.Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void restoreOnLifeCycleEvent() {
        mainView.setCurrentDayTo(model.getLastEntry());
        if(model.getLastEntry().getId()==0){
            mainView.updateView("Zaczynasz dzień, powodzonka,szerokości kolego.");
        } else {
            mainView.updateView("Kontynuujesz dzień, szerokości!");
        }
    }

    @Override
    public void saveFinishedDay() {
        model.addOrUpdate(mainView.getCurrentDay());
        mainView.updateView("Zakończono okres rozliczeniowy");
        mainView.refreshView();
    }

    @Override
    public void saveUnFinishedDay() {
        model.addOrUpdate(mainView.getCurrentDay());
        mainView.updateView("Zapisano tymczasową trasę");
        mainView.refreshView();
    }

    @Override
    public void showClosingAlertDialog(){
        mainView.showAlertDialog(StartOrCLose.CLOSE);
    }

    public void loadRandomData(){
        randomizator.addCities();
        mainView.updateView(""+randomizator.getRandomDays().size());
        model.addBunch(randomizator.getRandomDays());
    }
}