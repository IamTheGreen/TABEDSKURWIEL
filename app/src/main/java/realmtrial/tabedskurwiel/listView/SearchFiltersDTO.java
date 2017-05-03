package realmtrial.tabedskurwiel.listView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mttx on 2017-05-03.
 */

public class SearchFiltersDTO implements iFilterQuery{
    private SimpleDateFormat tf;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    public String queryTag;

    @Override
    public void setFromDate(int year, int month, int day) {
        tf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        String chosenDate = year+"-"+month+"-"+day;
        try{
            this.fromDate = tf.parse(chosenDate);
        } catch (ParseException ex){
            this.fromDate = null;
        }
    }

    @Override
    public void setToDate(int year, int month, int day) {
        tf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        String chosenDate = year+"-"+month+"-"+day;
        try{
            this.toDate = tf.parse(chosenDate);
        } catch (ParseException ex){
            this.toDate = null;
        }
    }

    @Override
    public void setQueryTag(String string) {
        this.queryTag = string;
    }

    @Override
    public Date getFromDate() {
        return fromDate;
    }

    @Override
    public Date getToDate() {
        return toDate;
    }

    @Override
    public String getQueryTag() {
        return queryTag;
    }
}
