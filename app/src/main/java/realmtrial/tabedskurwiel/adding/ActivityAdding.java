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
import realmtrial.tabedskurwiel.adding.NewData.MidPoint;

public class ActivityAdding extends AppCompatActivity implements iAddingMvp.iView {
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private Day dayHolder;
    private RecyclerView recyclerView;
    private AddingAdapter adapter;
    int iterations = 0;

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
    Button saveDay;
    @BindView(R.id.adding_button_saveMidPoint)
    Button saveMidPoint;
    @BindView(R.id.adding_button_saveTemporary)
    Button saveTemporary;
    @BindView(R.id.button_clear)
    Button clearDataBase;


    @BindViews({R.id.adding_start_location, R.id.adding_stop_location, R.id.adding_distance, R.id.adding_minutes, R.id.adding_hours})
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
    public void onResume() {
        super.onResume();
        presenter.onCreate();
    }

    @OnClick(R.id.adding_button_saveMidPoint)
    public void onAddStageButton() {
        if (hasAllFieldsFilled()) {
            prepareObjectToSave();
            dayHolder.getMidPoints().add(dayHolder.getTemporaryMidPoint());
            dayHolder.setTemporaryMidPoint(new MidPoint());
            statusBar.setText("Dodano punkt trasy");
            saveInput();
        } else {
            prepareObjectToSave();
            saveInput();
            statusBar.setText("Zapisano nie dokończony punkt trasy");
        }
    }

    @OnClick(R.id.adding_button_saveDay)
    public void onAddDayButton() {
        if (hasAtLeastOneStage() && hasAllFieldsEmpty()) {
            statusBar.setText("Zakończyłeś okres rozliczeniowy");
            onAddStageButton();
            dayHolder.setFinished(true);
            saveInput();
        }
        else if (hasAtLeastOneStage() && hasAllFieldsFilled()) {
            statusBar.setText("Zakończyłeś okres rozliczeniowy");
            onAddStageButton();
            dayHolder.setFinished(true);
            saveInput();
        }
        else if (!hasAtLeastOneStage() && hasAllFieldsFilled()) {
            statusBar.setText("Zakończyłeś okres rozliczeniowy");
            onAddStageButton();
            dayHolder.setFinished(true);
            saveInput();
        }
        else {
            onAddStageButton();
            statusBar.setText("Zapisano tymaczasowy obiekt");
            dayHolder.setFinished(false);
            saveInput();
        }
    }

    void onSaver(){
        if(isValidDay()){
            dayHolder.setFinished(true);
            statusBar.setText("Zakończyłeś okres rozliczeniowy");
            onAddStageButton();
            saveInput();
        } else {
            dayHolder.setFinished(false);
            statusBar.setText("Zapisano tymaczasowy obiekt");
            onAddStageButton();
            saveInput();
        }
    }

    public boolean isValidDay(){
        if(hasAtLeastOneStage() && hasAllFieldsEmpty()){
            return true;
        } else if(hasAtLeastOneStage() && hasAllFieldsFilled()){
            return true;
        } else if(!hasAtLeastOneStage() && hasAllFieldsFilled()){
            return true;
        } else {
            return false;
        }
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

    @Override
    public void onDataRequested(Day day) {

    }

    public void saveInput() {
        presenter.updateModel(dayHolder);
        presenter.onCreate();
    }

    public boolean hasAtLeastOneStage() {
        boolean hasStage = false;
        if (dayHolder.getMidPoints().size() > 0) {
            hasStage = true;
        }
        return hasStage;
    }

    public boolean hasAllFieldsEmpty() {
        boolean isValidated = true;
        for (EditText editText : editTextList) {
            if (editText.length() != 0) {
                isValidated = false;
            }
        }
        return isValidated;
    }

    public boolean hasAllFieldsFilled() {
        boolean isValidated = true;
        for (EditText editText : editTextList) {
            if (editText.length() == 0) {
                isValidated = false;
            }
        }
        return isValidated;
    }

    public void prepareObjectToSave() {
        dayHolder.getTemporaryMidPoint().setLocationStart(startLocation.getText().toString());
        dayHolder.getTemporaryMidPoint().setLocationStop(stopLocation.getText().toString());
        dayHolder.getTemporaryMidPoint().setDistance(distance.getText().toString());
        dayHolder.getTemporaryMidPoint().setHours(hours.getText().toString());
        dayHolder.getTemporaryMidPoint().setMinutes(minutes.getText().toString());
        dayHolder.getTemporaryMidPoint().setMidPointId(dayHolder.getMidPoints().size() + 1);
        adapter.setRouteList(dayHolder.getMidPoints());
    }

    @OnClick(R.id.button_clear)
    void clearDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        adapter.notifyDataSetChanged();
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