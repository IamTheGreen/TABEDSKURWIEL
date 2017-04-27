package realmtrial.tabedskurwiel.adding;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.realm.Realm;
import io.realm.RealmList;
import realmtrial.tabedskurwiel.AddingAdapter;
import realmtrial.tabedskurwiel.Data.Route;
import realmtrial.tabedskurwiel.Data.TmpRoute;
import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;
import realmtrial.tabedskurwiel.Data.WorkDay;
import realmtrial.tabedskurwiel.R;

public class ActivityAdding extends AppCompatActivity implements iAddingMvp.iView {
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private UnfinishedWorkDay unfinishedWorkDay = new UnfinishedWorkDay();
    private TmpRoute tmpRoute;
    private WorkDay workDayHolder;
    private Route route;
    private RecyclerView recyclerView;
    private AddingAdapter adapter;

    @BindView(R.id.adding_date)
    EditText date;
    @BindView(R.id.adding_start_location)
    EditText startLocation;
    @BindView(R.id.adding_stop_location)
    EditText stopLocation;
    @BindView(R.id.adding_distance)
    EditText distance;
    @BindView(R.id.adding_hours)
    EditText hours;
    @BindView(R.id.adding_minutes)
    EditText minutes;
    @BindView(R.id.adding_status_bar)
    TextView statusBar;
    @BindView(R.id.adding_button_saveDay)
    Button saveWorkDay;

    @BindView(R.id.button)
    Button zapisz;
    @BindView(R.id.button_clear)
    Button clear;
    @BindViews({R.id.adding_start_location,R.id.adding_stop_location,R.id.adding_distance,R.id.adding_hours,R.id.adding_minutes })
    List<EditText> editTextList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.adding_recycler_container);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeDateField();
        initializeRecyclerView();
        presenter = new AddingPresenter(this, new AddingModel());
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onCreate();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void updateView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void loadNotFinishedData(UnfinishedWorkDay day){
        unfinishedWorkDay = day;
        tmpRoute = unfinishedWorkDay.getTmpRoute();
        refreshViewWithLastEntry();
    }

    void refreshViewWithLastEntry(){
        startLocation.setText(unfinishedWorkDay.getTmpRoute().getLocationStart());
        stopLocation.setText(unfinishedWorkDay.getTmpRoute().getLocationStop());
        distance.setText(String.valueOf(unfinishedWorkDay.getTmpRoute().getDistance()));
        hours.setText(String.valueOf(unfinishedWorkDay.getTmpRoute().getHours()));
        minutes.setText(String.valueOf(unfinishedWorkDay.getTmpRoute().getMinutes()));
        adapter.setRouteList(unfinishedWorkDay.getRouteList());
        adapter.notifyDataSetChanged();
    }

    @Override
    @OnClick(R.id.button)
    public void onSaveButtonClick() {
        Route route = new Route();
        route.setLocationStart(unfinishedWorkDay.getTmpRoute().getLocationStart());
        route.setLocationStop(unfinishedWorkDay.getTmpRoute().getLocationStop());
        route.setDistance(unfinishedWorkDay.getTmpRoute().getDistance());
        route.setHours(unfinishedWorkDay.getTmpRoute().getHours());
        route.setMinutes(unfinishedWorkDay.getTmpRoute().getMinutes());
        unfinishedWorkDay.getRouteList().add(route);
    }

    @OnClick(R.id.adding_button_saveDay)
        public void onSaveDayClick() {
        onSaveButtonClick();
        unfinishedWorkDay.setFinished(true);
        presenter.pushEntryToModel(unfinishedWorkDay);
    }

    @OnFocusChange({R.id.adding_start_location,R.id.adding_stop_location,R.id.adding_distance,R.id.adding_hours,R.id.adding_minutes})
    void saveInputOnFocusChange(EditText editText){
        switch (editText.getId()){
            case R.id.adding_start_location: tmpRoute.setLocationStart(startLocation.getText().toString());break;
            case R.id.adding_stop_location:  tmpRoute.setLocationStop(stopLocation.getText().toString()); break;
            case R.id.adding_distance:       tmpRoute.setDistance(parseNumericalEntry(distance)); break;
            case R.id.adding_hours:          tmpRoute.setHours(parseNumericalEntry(hours)); break;
            case R.id.adding_minutes:        tmpRoute.setMinutes(parseNumericalEntry(minutes)); break;
        }
    }










































    @OnClick(R.id.button_clear)
    void clearDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
        adapter.notifyDataSetChanged();
    }


    int parseNumericalEntry(EditText editText) {
        int parsedEntry = 0;
        try {
            parsedEntry = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException exp) {
            editText.setText("");
            Toast.makeText(this, "Puste pole zastąpione zostaną zerami", Toast.LENGTH_SHORT).show();
        }
        return parsedEntry;
    }

    void initializeDateField() {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date.setText(mDay + "." + (mMonth + 1) + "." + mYear);
    }

    void initializeRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workDayHolder = new WorkDay();
        adapter = new AddingAdapter(unfinishedWorkDay.getRouteList());
        //set adapter
        recyclerView.setAdapter(adapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }















}



