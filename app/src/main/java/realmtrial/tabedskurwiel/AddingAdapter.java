package realmtrial.tabedskurwiel;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import realmtrial.tabedskurwiel.Data.Route;
import realmtrial.tabedskurwiel.adding.NewData.Day;
import realmtrial.tabedskurwiel.adding.NewData.MidPoint;

@SuppressLint("InflateParams")
public class AddingAdapter extends RecyclerView.Adapter<AddingAdapter.ViewHolder> {
    private List<MidPoint> midPoints;

    public AddingAdapter(List<MidPoint> midPoints) {
        this.midPoints = midPoints;
    }

    public void setRouteList(List<MidPoint> midPoints) {
        this.midPoints = midPoints;
    }

    @Override
    public AddingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create view and viewholder
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.startLocation.setText(midPoints.get(position).toString());
    }

    // class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView startLocation;
        public TextView distance;


        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            startLocation = (TextView) itemLayoutView.findViewById(R.id.recycler_start_location);
        }
    }

    // Returns the size of the routeList
    @Override
    public int getItemCount() {
        return midPoints.size();
    }
}