package realmtrial.tabedskurwiel.listView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import realmtrial.tabedskurwiel.Data.Day;
import realmtrial.tabedskurwiel.Data.MidPoint;
import realmtrial.tabedskurwiel.R;
import realmtrial.tabedskurwiel.adding.AddingModel;

import static android.R.attr.format;

/**
 * Created by mttx on 2017-04-17.
 */

public class FullListFragment extends Fragment implements iListView.View{
    private iListView.Presenter presenter;
    private RecyclerView recyclerView;
    private int mYear, mMonth, mDay,myHour,myMinute;
    private StatsAdapter adapter;
    private View rootView;
    private ConstraintLayout filterSettingContainer;
    private ConstraintLayout itemDetailContainer;
    private SearchFiltersDTO filterQuery;
    private Button filterButton;
    private DatePickerDialog datePickerDialog;
    private Calendar c;
    private Realm realm;
    private String locationFilter;
    private RealmResults<Day> realmResults;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public StatsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(StatsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public SearchFiltersDTO getFilterQuery() {
        return filterQuery;
    }

    @BindView(R.id.filter_button_confrim) Button confirm;
    @BindView(R.id.filter_button_cancel) Button cancel;
    @BindView(R.id.stats_filter_button) Button showMore;
    @BindView(R.id.filter_startDate)
    EditText etFromDay;
    @BindView(R.id.filter_endDate) EditText etToDay;
    @BindView(R.id.filter_searchView)
    android.widget.SearchView searchView;
    @BindView(R.id.filter_status_bar) TextView statusBar;
    TextView textView;

    @BindView(R.id.stats_item_detail_title) TextView itemTitle;
    @BindView(R.id.stats_item_detail_header) TextView itemHeader;
    @BindView(R.id.stats_item_detail_1conten_line) TextView item1ContentLine;
    @BindView(R.id.stats_item_detail_2content_line) TextView item2ContentLine;
    @BindView(R.id.stats_item_detail_stages) TextView itemStages;
    @BindView(R.id.stats_item_detail_summary) TextView itemSummary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_recycler_view, container, false);

        presenter = new ListViewPresenter(new ListViewModel(),this);



        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realmResults = realm.where(Day.class).findAll();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StatsAdapter(realmResults);
        realm.commitTransaction();



        //set adapter
        recyclerView.setAdapter(adapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterQuery = new SearchFiltersDTO();


     //   textView = (TextView) rootView.findViewById(R.id.textView);

        filterSettingContainer = (ConstraintLayout) rootView.findViewById(R.id.stats_filter_container);
        itemDetailContainer = (ConstraintLayout) rootView.findViewById(R.id.stats_detail_item_container);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
              //  textView.setText( position+"|"+addingModel.getFullDayList().get(position).getId() +addingModel.getFullDayList().get(position).toStrongs()+ "|" + view.getId());
                StringBuilder stingBuilder = new StringBuilder();
                StringBuilder distanceAndTime = new StringBuilder();
                if(filterSettingContainer.isShown()){
                    filterSettingContainer.setVisibility(View.GONE);
                }
//                if(itemDetailContainer.isShown()){
//                    itemDetailContainer.setVisibility(View.GONE);
//                }
                itemDetailContainer.setVisibility(View.VISIBLE);
                Day detailedDay = presenter.getModel().getDay(realmResults.get(position).getId());
                String dateformatted;
                try{
                    dateformatted = format.format(detailedDay.getDate());
                } catch (NullPointerException ex ){
                    dateformatted = "nie podano";
                }
                itemTitle.setText(dateformatted +"-"+ detailedDay.getStartLocation() + " - " +detailedDay.getEndLocation());
                //int midP=

                for(MidPoint midPoint : detailedDay.getMidPoints()){
                    stingBuilder
                            .append(Integer.valueOf(midPoint.getMidPointId()+1)+ ". ")
                            .append(midPoint.getLocationStart()).append(" - ")
                            .append(midPoint.getLocationStop()+". \n");

                    if(midPoint.getMinutes().length()==1){
                        distanceAndTime
                                .append(midPoint.getDistance()+" KM ")
                                .append(midPoint.getHours() +":").append("0"+midPoint.getMinutes() + " \n");
                    } else {
                        distanceAndTime
                                .append(midPoint.getDistance()+" KM ")
                                .append(midPoint.getHours() +":").append(midPoint.getMinutes() + " \n");
                    }
                }


                itemStages.setText(stingBuilder.toString());
                item1ContentLine.setText(distanceAndTime.toString());
                itemSummary.setText("Podsumowanie : "+detailedDay.getTotalDistance()+ "KM " + detailedDay.getTotalTime());

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));

        ButterKnife.bind(this,rootView);
        itemdetailListener();
        initializeDateField();
        searchViewListener();
        return rootView;
    }

    void itemdetailListener(){
        itemDetailContainer.setClickable(true);
        itemDetailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemDetailContainer.isShown()){
                    itemDetailContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initializeDateField() {
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        myHour= c.get(Calendar.HOUR_OF_DAY);
        myMinute = c.get(Calendar.MINUTE);
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onPause(){
        super.onPause();
        realm.close();
    }

    @OnClick(R.id.stats_filter_button)
    void showFilterContainer(){

        if(filterSettingContainer.isShown()){
            if(itemDetailContainer.isShown()){
                itemDetailContainer.setVisibility(View.GONE);
            }
            filterSettingContainer.setVisibility(View.GONE);
        } else{
            filterSettingContainer.setVisibility(View.VISIBLE);
            itemDetailContainer.setVisibility(View.GONE);
        }



        if(!searchView.hasFocus()){
            searchView.requestFocus();
            searchView.performClick();
            searchView.setIconified(false);
        }
    }

    @OnClick(R.id.filter_button_confrim)
    void onConfirmButton(){
        statusBar.setText(searchView.getQuery());
        locationFilter = searchView.getQuery().toString();
        filterSettingContainer.setVisibility(View.GONE);
        filterQuery.setQueryTag(searchView.getQuery().toString());
        realm.beginTransaction();
        realmResults = realm.where(Day.class).equalTo("startLocation",locationFilter.trim(), Case.INSENSITIVE).findAll();
        adapter.setWorkDayList(realmResults);
        realm.commitTransaction();
        adapter.notifyDataSetChanged();
        presenter.makeSearch(filterQuery);

    }

    @OnFocusChange(R.id.filter_startDate)
    void fromDatePicker(boolean hasFocus) {
        if(hasFocus){
            datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etFromDay.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            filterQuery.setFromDate(year,monthOfYear+1,dayOfMonth);
                            Toast.makeText(getContext(), filterQuery.getFromDate().toString(), Toast.LENGTH_LONG).show();

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @OnFocusChange(R.id.filter_endDate)
    void toDatePicker(boolean hasFocus) {
        if(hasFocus){
            datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etToDay.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            filterQuery.setFromDate(year,monthOfYear+1,dayOfMonth);
                            Toast.makeText(getContext(), filterQuery.getFromDate().toString(), Toast.LENGTH_LONG).show();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
    @Override
    public void toaster(String string){
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
    }

    @OnFocusChange(R.id.filter_endDate)
    void tchangedate(boolean hasFocus) {
        if(hasFocus){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etToDay.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            filterQuery.setToDate(year,dayOfMonth,year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.setTitle("Wybierz datę końcową");
            datePickerDialog.setMessage("Wybierz datę końcową");
            datePickerDialog.show();
        }
    }

    void searchViewListener(){
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    filterButton.setText(searchView.getQueryHint().toString());
                }
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    void itemDetailContainerInit(){

    }









































    //ON CLICK ACTION

    public interface ClickListener{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}