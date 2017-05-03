package realmtrial.tabedskurwiel.listView;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import realmtrial.tabedskurwiel.R;
import realmtrial.tabedskurwiel.Data.Day;
import realmtrial.tabedskurwiel.Data.MidPoint;

//@SuppressLint("InflateParams")
    public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private RealmResults<Day> workDayList;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public StatsAdapter(RealmResults<Day> workDayList) {
        this.workDayList = workDayList;
    }

    public void setWorkDayList(RealmResults<Day> workDayList) {
        this.workDayList = workDayList;
    }

    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String dateformatted;
        try{
            dateformatted = format.format(workDayList.get(position).getDate());
        } catch (NullPointerException ex ){
            dateformatted = "nie podano";
        }

        List<MidPoint> routeList = workDayList.get(position).getMidPoints();
        viewHolder.startLocation.setText(workDayList.get(position).toStrongs());
        viewHolder.secondLine.setText("Od: "+workDayList.get(position).getStartHour() + ":" + workDayList.get(position).getStartMinute()+
        " do" + workDayList.get(position).getEndHour() + ":" + workDayList.get(position).getEndHour()+"  " +dateformatted);

       // viewHolder.pinkbar.setVisibility(View.GONE);
    }

    // class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startLocation;
        public TextView secondLine;
        public static ConstraintLayout pinkbar;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            startLocation = (TextView) itemLayoutView.findViewById(R.id.recycler_first_line);
            secondLine = (TextView) itemLayoutView.findViewById(R.id.recycler_second_line);
            pinkbar = (ConstraintLayout) itemLayoutView.findViewById(R.id.listview_pink_bar);

        }

        public static void hidePinkbar(){
            pinkbar.setVisibility(View.GONE);

        }
        public static void showPinkbar(){
            pinkbar.setVisibility(View.VISIBLE);
        }
    }

    // Returns the size of the workDayList
    @Override
    public int getItemCount() {
        return workDayList.size();
    }
}