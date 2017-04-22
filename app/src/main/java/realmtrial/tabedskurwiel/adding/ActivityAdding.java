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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
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
    private Toolbar toolbar;

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
        workDayHolder = WorkDay.getInstance();
        initializeDateField();
        initializeRecyclerView();

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

    @Override
    @OnClick(R.id.button)
    public void onSaveButtonClick() {
        if(workDayHolder.isFinished()){
            buildWorkDay();
        } else {
            buildRoute();
        }
        startLocation.requestFocus();
    }

    Route buildRoute() {
        workDayHolder = WorkDay.getInstance();
        int routeId = 0;
        try{
            routeId = workDayHolder.getRouteList().get(workDayHolder.getRouteList().size()-1).getId()+1;
        } catch (NullPointerException ex){
            statusBar.setText("exception raised");
        } catch (ArrayIndexOutOfBoundsException ex){
            routeId = 1;
        }
        finally {
            statusBar.setText(""+routeId);

            Route route = new Route(routeId,
                    startLocation.getText().toString(),
                    stopLocation.getText().toString(),
                    parseNumericalEntry(distance),
                    parseNumericalEntry(hours),
                    parseNumericalEntry(minutes)
                    );
            workDayHolder.getRouteList().add(route);
            adapter.notifyDataSetChanged();
            resetFields();
        }
        return null;
    }

    WorkDay buildWorkDay(){
         AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Zakończyłeś dzień pracy. Wszystkie wpisy zostały zapisane");
        alert.setPositiveButton("Potwierdzam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                statusBar.setText("Dziękujemy za dodanie wpisu.");
            }
        });
        alert.create();
        alert.show();
        return null;
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

    void resetFields(){
        startLocation.setText("");
        stopLocation.setText("");
        distance.setText("");
        hours.setText("");
        minutes.setText("");
        statusBar.setText("Wpis dodany, pola wyzerowane");
    }
}













