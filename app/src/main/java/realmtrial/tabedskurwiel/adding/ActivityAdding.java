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
import butterknife.OnTextChanged;
import io.realm.Realm;
import io.realm.RealmList;
import realmtrial.tabedskurwiel.AddingAdapter;
import realmtrial.tabedskurwiel.Data.Randomizer;
import realmtrial.tabedskurwiel.Data.Route;
import realmtrial.tabedskurwiel.Data.UnfinishedWorkDay;
import realmtrial.tabedskurwiel.Data.WorkDay;
import realmtrial.tabedskurwiel.R;

public class ActivityAdding extends AppCompatActivity implements iAddingMvp.View {
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private UnfinishedWorkDay unfinishedWorkDay = new UnfinishedWorkDay();
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
    @BindView(R.id.adding_checkbox_day_finished)
    CheckBox isFinished;
    @BindView(R.id.button)
    Button zapisz;
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
        initializeDateField();
        initializeRecyclerView();
        presenter = new AddingPresenter(this, new AddingModel());
    }

    private void unPackUnfinishedDay() {

        unfinishedWorkDay = (UnfinishedWorkDay) presenter.getUnfinishedEntry();
        try {
            route = unfinishedWorkDay.getRouteList().get(unfinishedWorkDay.getRouteList().size() - 1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            unfinishedWorkDay.getRouteList().add(new Route());
            route = unfinishedWorkDay.getRouteList().get(0);
        } finally {
            startLocation.setText(route.getLocationStart());
            stopLocation.setText(route.getLocationStop());
            distance.setText("" + route.getDistance());
            hours.setText("" + route.getHours());
            minutes.setText("" + route.getMinutes());
            if
                    (!hasInput(route.getLocationStart()) ||
                    !hasInput(route.getLocationStop()) ||
                    !hasInput(route.getDistance()) ||
                    !hasInput(route.getHours()) ||
                    !hasInput(route.getMinutes())) {
                try {
                    unfinishedWorkDay.getRouteList().remove(unfinishedWorkDay.getRouteList().size() - 1);
                } catch (IndexOutOfBoundsException ex) {}
            }
            adapter.setRouteList(unfinishedWorkDay.getRouteList());
            adapter.notifyDataSetChanged();
        }
    }

    private boolean hasInput(String string) {
        if (string.length() > 0) {
            return true;
        } else
            return false;
    }

    private boolean hasInput(int i) {
        if (String.valueOf(i).length() > 0) {
            return true;
        } else
            return false;
    }


    @Override
    public void onStart() {
        super.onStart();
        unPackUnfinishedDay();
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
        onSaveButtonClick();
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

    @OnClick(R.id.adding_date)
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
    void clearDb() {
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.deleteAll();
//        realm.commitTransaction();
//        realm.close();
//        adapter.notifyDataSetChanged();
        statusBar.setText(route.toString());
    }

    @OnTextChanged(R.id.adding_start_location)
    void saveStart() {
        route.setLocationStart(startLocation.getText().toString());
        statusBar.setText("" + route.getLocationStart() + " " + unfinishedWorkDay.getRouteList().size());

    }

    @Override
    @OnClick(R.id.button)
    public void onSaveButtonClick() {
        unfinishedWorkDay.getRouteList().add(route);
    }

    int parseNumericalEntry(EditText editText) {
        int parsedEntry = 0;
        try {
            parsedEntry = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException exp) {
            editText.setText("0");
            Toast.makeText(this, "Puste pole zastąpione zostaną zerami", Toast.LENGTH_SHORT).show();
        }
        return parsedEntry;
    }
}













