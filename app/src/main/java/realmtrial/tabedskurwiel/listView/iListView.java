package realmtrial.tabedskurwiel.listView;

import java.util.List;

import realmtrial.tabedskurwiel.Data.Day;

/**
 * Created by mttx on 2017-05-03.
 */

public interface iListView {
    interface Model {
        List<Day> getFilteredByDate(iFilterQuery filterQuery);
        List<Day> getAll();
        int getFilteredByDateSize(iFilterQuery filterQuery);
        List<Day> transaction(final iFilterQuery query);
        Day getDay(long primaryKey);
    }
    interface Presenter{
        void makeSearch(iFilterQuery query);
        void updateResults(List<Day> filteredResults, String s);
        iListView.Model getModel();

    }
    interface View{
        iFilterQuery getFilterQuery();
        StatsAdapter getAdapter();
        void toaster(String string);


    }
}
