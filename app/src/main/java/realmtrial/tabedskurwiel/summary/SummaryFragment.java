package realmtrial.tabedskurwiel.summary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import realmtrial.tabedskurwiel.R;

/**
 * Created by mttx on 2017-04-17.
 */

public class SummaryFragment extends Fragment implements iSummary.View{
    private iSummary.Presenter presenter;


    @BindView(R.id.summary_total_distance)
    TextView totalDistance;
    @BindView(R.id.summary_total_hours) TextView totalHours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        presenter = new Presenter(new Model(),this);
        presenter.onFilterSelected(SummaryFilter.LASTMONTH);
        return rootView;
    }

    @Override
    public void updateTotalDistance(int totalDistance) {
        this.totalDistance.setText(totalDistance);
    }

    @Override
    public void updateTotalHours(int totalHours) {

    }
}
