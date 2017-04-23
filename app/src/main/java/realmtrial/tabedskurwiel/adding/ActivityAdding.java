package realmtrial.tabedskurwiel.adding;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.realm.Realm;
import io.realm.RealmList;
import realmtrial.tabedskurwiel.AddingAdapter;
import realmtrial.tabedskurwiel.Data.Randomizer;
import realmtrial.tabedskurwiel.Data.Route;
import realmtrial.tabedskurwiel.Data.WorkDay;
import realmtrial.tabedskurwiel.R;

public class ActivityAdding extends AppCompatActivity implements iAddingMvp.View {
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private WorkDay workDayHolder;
    private final Randomizer random = new Randomizer();
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
    @BindView(R.id.adding_checkbox_day_finished)
    CheckBox isFinished;
    @BindView(R.id.button) Button zapisz;
    @BindView(R.id.button_clear)
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.adding_recycler_container);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isFinished = (CheckBox) findViewById(R.id.adding_checkbox_day_finished);
        presenter = new AddingPresenter(this, new AddingModel());
        presenter.onCreate();
        statusBar.setClickable(true);

        isFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    workDayHolder.setFinished(true);
                } else {
                    workDayHolder.setFinished(false);
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        initializeDateField();
        initializeRecyclerView();
    }



    @Override
    protected void onResume() {
        super.onResume();
        presenter.onCreate();
    }

    @Override
    public void onPause(){
        super.onPause();
        onSaveButtonClick();
    }

    void initializeDateField() {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date.setText(mDay + "." + (mMonth + 1) + "." + mYear);
    }

    void initializeRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddingAdapter(workDayHolder.getRouteList());
        //set adapter
        recyclerView.setAdapter(adapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnFocusChange(R.id.adding_date)
    void changeDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    @OnClick(R.id.button_clear)
    void clearDb(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    @OnClick(R.id.button)
    public void onSaveButtonClick() {
        Route route = new Route();
        route.setLocationStart(startLocation.getText().toString());
        route.setLocationStop(stopLocation.getText().toString());
        route.setDistance(parseNumericalEntry(distance));
        route.setHours(parseNumericalEntry(hours));
        route.setMinutes(parseNumericalEntry(minutes));
        route.setId(workDayHolder.getRouteList().size());

        workDayHolder.getRouteList().add(route);
        presenter.updateModel(workDayHolder);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setWorkDayHolder(WorkDay workDay) {
        this.workDayHolder = workDay;
    }

    @Override
    public WorkDay buildData() {
        return null;
    }

    @Override
    public void updateView() {
        Route route;
        try{
           route = workDayHolder.getRouteList().get(workDayHolder.getRouteList().size()-1);
        } catch (ArrayIndexOutOfBoundsException ex){
            route = new Route();
        }

        startLocation.setText(route.getLocationStart());
        stopLocation.setText(route.getLocationStop());
        distance.setText(""+route.getDistance());
        hours.setText(""+route.getHours());
        minutes.setText(""+route.getMinutes());
        adapter.setRouteList(workDayHolder.getRouteList());
        adapter.notifyDataSetChanged();
    }
    @Override
    public void updateStatusBar(String s) {
        statusBar.setText(s);
    }

    int parseNumericalEntry(EditText editText){
        int parsedEntry = 0;
        try{
            parsedEntry = Integer.parseInt(editText.getText().toString());
        }catch (NumberFormatException exp){
            editText.setText("0");
            Toast.makeText(this, "Puste pole zastąpione zostaną zerami", Toast.LENGTH_SHORT).show();
        }
        return parsedEntry;
    }
}













