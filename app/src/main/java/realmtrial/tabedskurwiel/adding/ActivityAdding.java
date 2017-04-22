package realmtrial.tabedskurwiel.adding;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import realmtrial.tabedskurwiel.Data.Randomizer;
import realmtrial.tabedskurwiel.Data.Route;
import realmtrial.tabedskurwiel.Data.WorkDay;
import realmtrial.tabedskurwiel.MyAdapter;
import realmtrial.tabedskurwiel.R;

public class ActivityAdding extends AppCompatActivity implements iAddingMvp.View {
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private WorkDay workDayHolder = null;
    private final Randomizer random = new Randomizer();
    private RecyclerView recyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.adding_recycler_container);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        presenter = new AddingPresenter(this, new AddingModel());
        workDayHolder = new WorkDay();
        initializeDateField();
        initializeRecyclerView();
    }

    void initializeDateField() {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date.setText(mDay + "." + (mMonth + 1) + "." + mYear);
    }

    void initializeRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter mAdapter = new MyAdapter(random.randomizedRoutes());
        //set adapter
        recyclerView.setAdapter(mAdapter);
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
        buildRoute();
    }

    Route buildRoute() {
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
            workDayHolder.getRouteList().add(new Route(routeId));
        }
        return null;
    }
}


















