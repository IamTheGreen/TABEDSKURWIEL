package realmtrial.tabedskurwiel.adding.listView;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import realmtrial.tabedskurwiel.R;
import realmtrial.tabedskurwiel.Randomizator;
import realmtrial.tabedskurwiel.StatsAdapter;
import realmtrial.tabedskurwiel.adding.AddingModel;

/**
 * Created by mttx on 2017-04-17.
 */

public class Frag extends Fragment {
    private AddingModel addingModel;
    private RecyclerView recyclerView;
    private StatsAdapter adapter;

    @BindView(R.id.button2)
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_recycler_view, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addingModel = new AddingModel();
        adapter = new StatsAdapter(addingModel.getFullDayList());
        //set adapter
        recyclerView.setAdapter(adapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        TextView status = (TextView)rootView.findViewById(R.id.textView);
        status.setText(""+addingModel.getFullDayList().size() + "Rozmiar Listy");
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.button2)
    void loadData(){
        Randomizator randomizator = new Randomizator();
        addingModel.addBunch(randomizator.getRandomDays());
    }
}

