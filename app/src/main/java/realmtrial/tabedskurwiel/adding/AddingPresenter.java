package realmtrial.tabedskurwiel.adding;

import realmtrial.tabedskurwiel.Data.Route;

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
    public void updateModel(Route route) {

    }

    @Override
    public void updateView(String string) {

    }
}
