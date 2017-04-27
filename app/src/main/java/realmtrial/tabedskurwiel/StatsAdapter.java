package realmtrial.tabedskurwiel;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import realmtrial.tabedskurwiel.Data.Route;
import realmtrial.tabedskurwiel.Data.WorkDay;
import realmtrial.tabedskurwiel.adding.NewData.Day;
import realmtrial.tabedskurwiel.adding.NewData.MidPoint;

//@SuppressLint("InflateParams")
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private List<Day> workDayList;

    public StatsAdapter(List<Day> workDayList) {
        this.workDayList = workDayList;
    }

    public void setWorkDayList(List<Day> workDayList) {
        this.workDayList = workDayList;
    }

    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create view and viewholder
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        List<MidPoint> routeList = workDayList.get(position).getMidPoints();

        viewHolder.startLocation.setText(workDayList.get(position).toStrongs());
    }

    // class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startLocation;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            startLocation = (TextView) itemLayoutView.findViewById(R.id.recycler_start_location);
        }
    }

    // Returns the size of the workDayList
    @Override
    public int getItemCount() {
        return workDayList.size();
    }
}