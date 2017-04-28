package realmtrial.tabedskurwiel.adding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import realmtrial.tabedskurwiel.AddingAdapter;
import realmtrial.tabedskurwiel.R;
import realmtrial.tabedskurwiel.adding.NewData.Day;
import realmtrial.tabedskurwiel.adding.NewData.HELPER;
import realmtrial.tabedskurwiel.adding.NewData.MidPoint;

public class ActivityAdding extends AppCompatActivity implements iAddingMvp.iView {
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private Day dayHolder;
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
    @BindView(R.id.addinf_validation_fix) EditText validationfix;

    @BindView(R.id.adding_button_saveDay) Button saveDay;
    @BindView(R.id.adding_button_saveMidPoint) Button saveMidPoint;
    @BindView(R.id.adding_button_saveTemporary) Button saveTemporary;
    @BindView(R.id.button_clear) Button clearDataBase;


    @BindViews({R.id.adding_start_location,R.id.adding_stop_location,R.id.adding_distance,R.id.adding_minutes,R.id.adding_hours})
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
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onCreate();
        adapter.setRouteList(dayHolder.getMidPoints());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        presenter.onCreate();
    }

    @Override
    public void updateView() {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.onCreate();
    }

    @Override
    @OnClick(R.id.adding_button_saveMidPoint)
    public void onSaveMidPoint() {
      if(isValidatedMidPoint()){
          // zbuduj obiekt midpoint
          buildTemporaryDay(false);
          // dodaj gotowy punkt do listy puntków
          dayHolder.getMidPoints().add(dayHolder.getTemporaryMidPoint());
          // utworz nowy obiekt w miejsce starego
          dayHolder.setTemporaryMidPoint(new MidPoint());
          // zaaktualizuj widok
          presenter.updateModel(dayHolder);
          presenter.onCreate();

        } else {
            onSaveTemporary();
          presenter.updateModel(dayHolder);
          presenter.onCreate();
        }
    }

    @Override
    @OnClick(R.id.adding_button_saveDay)
    public void onSaveDay() {
        onSaveMidPoint();
        buildTemporaryDay(true);
        if(isValidatedDay()){
            presenter.updateModel(dayHolder);
            presenter.onCreate();
        } else {
            buildTemporaryDay(false);
            presenter.updateModel(dayHolder);
            presenter.onCreate();
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.adding_button_saveTemporary)
    public void onSaveTemporary(){
       // statusBar.setText("Dane zostaną przywrócone automatycznie!!");
        buildTemporaryDay(false);
    }

    @Override
    public void assignDayToHolder(Day day) {
        dayHolder = day;
        refreshView();
    }

    @Override
    public void refreshView() {
        startLocation.setText(dayHolder.getTemporaryMidPoint().getLocationStart());
        stopLocation.setText(dayHolder.getTemporaryMidPoint().getLocationStop());
        distance.setText(dayHolder.getTemporaryMidPoint().getDistance());
        hours.setText(dayHolder.getTemporaryMidPoint().getHours());
        minutes.setText(dayHolder.getTemporaryMidPoint().getMinutes());
        adapter.notifyDataSetChanged();
    }

    public void buildTemporaryDay(Boolean isFisnihed){
        dayHolder.getTemporaryMidPoint().setLocationStart(startLocation.getText().toString());
        dayHolder.getTemporaryMidPoint().setLocationStop(stopLocation.getText().toString());
        dayHolder.getTemporaryMidPoint().setDistance(distance.getText().toString());
        dayHolder.getTemporaryMidPoint().setHours(hours.getText().toString());
        dayHolder.getTemporaryMidPoint().setMinutes(minutes.getText().toString());
        dayHolder.getTemporaryMidPoint().setMidPointId(dayHolder.getMidPoints().size()+1);
        adapter.setRouteList(dayHolder.getMidPoints());
        dayHolder.setFinished(isFisnihed);
    }

    public boolean isValidatedMidPoint(){
        boolean isValidated = true;
        StringBuilder sb = new StringBuilder();
        for(EditText editText : editTextList){
         sb.append(editText.length() + ",");
            if(editText.length()==0){
                isValidated = false;
            }
        }
        statusBar.setText(sb.toString() + isValidated);
        return isValidated;
    }

    public boolean isValidatedDay() {
        boolean isValidated = true;
        if (dayHolder.getMidPoints().size() == 0) {
            isValidated = false;
            statusBar.setText("Dodaj conajmniej jeden etap podrózy");
        }  else
            {isValidated = true;}
    return isValidated;
    }



































    @OnClick(R.id.button_clear)
    void clearDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
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
        dayHolder = new Day();
        adapter = new AddingAdapter(dayHolder.getMidPoints());
        //set adapter
        recyclerView.setAdapter(adapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }















}



