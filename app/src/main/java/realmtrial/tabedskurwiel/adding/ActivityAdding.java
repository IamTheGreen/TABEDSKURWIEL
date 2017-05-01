package realmtrial.tabedskurwiel.adding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.realm.Realm;
import realmtrial.tabedskurwiel.AddingAdapter;
import realmtrial.tabedskurwiel.R;
import realmtrial.tabedskurwiel.adding.NewData.Day;
import realmtrial.tabedskurwiel.adding.NewData.MidPoint;
import realmtrial.tabedskurwiel.adding.NewData.StartOrCLose;
import realmtrial.tabedskurwiel.adding.NewData.ValuePasser;
import android.view.View;


public class ActivityAdding extends AppCompatActivity implements iAddingMvp.iView{
    private iAddingMvp.Presenter presenter;
    private int mYear, mMonth, mDay,myHour,myMinute;
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
    @BindView(R.id.adding_button_saveDay)
    Button saveDay;
    @BindView(R.id.adding_button_saveMidPoint)
    Button saveMidPoint;
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
        presenter.restoreOnLifeCycleEvent();

    }

    @Override
    public void showAlertDialog(final StartOrCLose startOrClose){
        View current = getCurrentFocus();
        if (current != null) current.clearFocus();

        final ValuePasser values = new ValuePasser();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.adding_lifecycle_alert);
        final NumberPicker hourPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        final NumberPicker minutePicker = (NumberPicker) dialog.findViewById(R.id.numberPicker2);
        final TextView chosenTime = (TextView) dialog.findViewById(R.id.alert_dialog_timer);
        final TextView alertStatusBar = (TextView) dialog.findViewById(R.id.adding_alert_status_bar);
        Button confirmButton = (Button) dialog.findViewById(R.id.adding_alert_button_confirm);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setValue(myHour);

        minutePicker.setMinValue(00);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(myMinute);

        switch(startOrClose){
            case START: alertStatusBar.setText("Podaj czas ROZPOCZĘCIA");
                break;
            case CLOSE: alertStatusBar.setText("Podaj czas ZAKONCZENIA");
                break;
        }

        chosenTime.setText("Wybrano: "+values.getHour() +" : "+ values.getMinute());
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                values.setHour(newVal);
                chosenTime.setText("Wybrano: "+values.getHour() +" : "+ values.getMinute());
            }
        });

        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                values.setMinute(newVal);
                chosenTime.setText("Wybrano: "+values.getHour() +" : " +values.getMinute());
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                statusBar.setText("zapisano " + dayHolder.getStartHour() + dayHolder.getStartMinute()+ " END " + dayHolder.getEndHour() + dayHolder.getEndMinute());
                presenter.saveFinishedDay();
                presenter.restoreOnLifeCycleEvent();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(startOrClose){
                    case START:
                        dayHolder.setStartHour(hourPicker.getValue());
                        dayHolder.setStartMinute(minutePicker.getValue());
                        dayHolder.setStartTimeSet(true);
                        break;
                    case CLOSE:
                        dayHolder.setEndHour(hourPicker.getValue());
                        dayHolder.setEndMinute(minutePicker.getValue());
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.setRouteList(dayHolder.getMidPoints());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        presenter.restoreOnLifeCycleEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.restoreOnLifeCycleEvent();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.saveUnFinishedDay();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.saveUnFinishedDay();
    }

    private void initializeDateField() {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        myHour= c.get(Calendar.HOUR_OF_DAY);
        myMinute = c.get(Calendar.MINUTE);
        date.setText(mDay + "." + (mMonth + 1) + "." + mYear);
    }

    private void initializeRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dayHolder = new Day();
        adapter = new AddingAdapter(dayHolder.getMidPoints());
        //set adapter
        recyclerView.setAdapter(adapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void updateView(String string) {
        statusBar.setText(string);
    }

    @OnClick(R.id.adding_button_saveMidPoint)
    public void onAddStageButton() {
        presenter.loadRandomData();
//        getDataFromInput();
//        if (hasAllFieldsFilled()) {
//            dayHolder.getMidPoints().add(dayHolder.getTemporaryMidPoint());
//            dayHolder.setTemporaryMidPoint(new MidPoint());
//            presenter.saveUnFinishedDay();
//            adapter.setRouteList(dayHolder.getMidPoints());
//            adapter.notifyDataSetChanged();
//        } else {
//            presenter.saveUnFinishedDay();
//        }
    }

    @OnClick(R.id.adding_button_saveDay)
    void onAddDayButton(){
        if(isValidDay()){
            onAddStageButton();
            dayHolder.setFinished(true);
            presenter.showClosingAlertDialog();
           // presenter.saveFinishedDay();
          //  presenter.restoreOnLifeCycleEvent();
        } else {
            onAddStageButton();
            dayHolder.setFinished(false);
            presenter.saveUnFinishedDay();
        }
    }

    public boolean isValidDay() {
        if (hasAtLeastOneStage() && hasAllFieldsEmpty()) {
            return true;
        } else if (hasAtLeastOneStage() && hasAllFieldsFilled()) {
            return true;
        } else if (!hasAtLeastOneStage() && hasAllFieldsFilled()) {
            return true;
        } else if(!hasAtLeastOneStage() && hasAllFieldsEmpty()){
            return false;
        } else return false;
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

    public Day getCurrentDay(){
        return dayHolder;
    }

    @Override
    public void setCurrentDayTo(Day day) {
        dayHolder = day;
        refreshView();
    }

    private void getDataFromInput() {
        dayHolder.getTemporaryMidPoint().setLocationStart(startLocation.getText().toString());
        dayHolder.getTemporaryMidPoint().setLocationStop(stopLocation.getText().toString());
        dayHolder.getTemporaryMidPoint().setDistance(distance.getText().toString());
        dayHolder.getTemporaryMidPoint().setHours(hours.getText().toString());
        dayHolder.getTemporaryMidPoint().setMinutes(minutes.getText().toString());
        dayHolder.getTemporaryMidPoint().setMidPointId(dayHolder.getMidPoints().size() + 1);
        adapter.setRouteList(dayHolder.getMidPoints());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshView() {
        startLocation.setText(dayHolder.getTemporaryMidPoint().getLocationStart());
        stopLocation.setText(dayHolder.getTemporaryMidPoint().getLocationStop());
        distance.setText(dayHolder.getTemporaryMidPoint().getDistance());
        hours.setText(dayHolder.getTemporaryMidPoint().getHours());
        minutes.setText(dayHolder.getTemporaryMidPoint().getMinutes());
        adapter.setRouteList(dayHolder.getMidPoints());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.button_clear)
    void clearDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        adapter.notifyDataSetChanged();
    }

    @OnFocusChange(R.id.adding_start_location)
    public void setStartTime(boolean hasFocus){
        if(hasFocus){
            if(!dayHolder.isStartTimeSet()){
                showAlertDialog(StartOrCLose.START);
            }
        }
    }
}