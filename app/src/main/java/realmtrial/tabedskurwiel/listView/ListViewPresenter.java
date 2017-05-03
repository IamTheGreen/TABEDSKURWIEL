package realmtrial.tabedskurwiel.listView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import realmtrial.tabedskurwiel.Data.Day;

/**
 * Created by mttx on 2017-04-30.
 */

public class ListViewPresenter implements iListView.Presenter {
    private iListView.Model model;
    private iListView.View view;

    public iListView.Model getModel() {
        return model;
    }

    public void setModel(iListView.Model model) {
        this.model = model;
    }

    public ListViewPresenter(iListView.Model model, iListView.View view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void makeSearch(iFilterQuery query) {
//        List<Day> queired = new ArrayList<>();
//        queired = model.transaction(query);
////        view.getAdapter().setWorkDayList(queired);
////        view.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void updateResults(List<Day> filteredResults, String s){
//        view.getAdapter().setWorkDayList(filteredResults);
        view.getAdapter().notifyDataSetChanged();
        view.toaster(s);

        //view.getAdapter().setWorkDayList(model.getFilteredByDate(view.getFilterQuery()));
    }
}
