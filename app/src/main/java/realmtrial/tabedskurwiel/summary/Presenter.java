package realmtrial.tabedskurwiel.summary;

/**
 * Created by mttx on 2017-05-03.
 */

public class Presenter implements iSummary.Presenter{
    private iSummary.Model model;
    private iSummary.View view;

    public Presenter(iSummary.Model model, iSummary.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFilterSelected(SummaryFilter selectedFilter) {
        view.updateTotalDistance(model.getTotalDistance(selectedFilter));
        view.updateTotalHours(model.getTotalHours(selectedFilter));
    }
}
