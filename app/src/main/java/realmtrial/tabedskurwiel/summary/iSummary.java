package realmtrial.tabedskurwiel.summary;

/**
 * Created by mttx on 2017-05-03.
 */

public interface iSummary {
    interface Model{
        int getTotalDistance(SummaryFilter selectedFilter);
        int getTotalHours(SummaryFilter selectedFilter);
    }
    interface Presenter{
        void onStart();
        void onFilterSelected(SummaryFilter selectedFilter);
    }
    interface View{
        void updateTotalDistance(int totalDistance);
        void updateTotalHours(int totalHours);
    }
}
