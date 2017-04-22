package realmtrial.tabedskurwiel;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import realmtrial.tabedskurwiel.Data.Randomizer;

/**
 * Created by mttx on 2017-04-17.
 */

public class Frag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_recycler_view, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        final Randomizer random = new Randomizer();

        FruitModel fruitsData[] = { new FruitModel("Apple"),
                new FruitModel("Banana"),
                new FruitModel("Orange"),
                new FruitModel("Pineapple"),
                new FruitModel("Mango"),
                new FruitModel("Watermelon"),
                new FruitModel("Strawberry"),
                new FruitModel("Grapes"),
                new FruitModel("Jackfruit"),
                new FruitModel("Carrot"),  new FruitModel("Orange"),
                new FruitModel("Pineapple"),
                new FruitModel("Mango"),
                new FruitModel("Watermelon"),
                new FruitModel("Strawberry"),
                new FruitModel("Grapes"),
                new FruitModel("Jackfruit"),
                new FruitModel("Carrot"),
                new FruitModel("Orange"),
                new FruitModel("Pineapple"),
                new FruitModel("Mango"),
                new FruitModel("Watermelon"),
                new FruitModel("Strawberry"),
                new FruitModel("Grapes"),
                new FruitModel("Jackfruit"),
                new FruitModel("Carrot"),
                new FruitModel("Fig")};

        //set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AddingAdapter mAdapter = new AddingAdapter(random.randomizedRoutes());
        //set adapter
        recyclerView.setAdapter(mAdapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                // Do not draw the divider
//            }
//        });

        return rootView;
    }
}
