package realmtrial.tabedskurwiel.adding;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import realmtrial.tabedskurwiel.R;
import realmtrial.tabedskurwiel.Data.MidPoint;

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
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit,parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.firstLine.setText(midPoints.get(position).toAddingListView());
    }

    // class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstLine;
        public TextView secondLine;


        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            firstLine = (TextView) itemLayoutView.findViewById(R.id.recycler_first_line);
            secondLine = (TextView) itemLayoutView.findViewById(R.id.recycler_second_line);
        }
    }

    // Returns the size of the routeList
    @Override
    public int getItemCount() {
        return midPoints.size();
    }
}