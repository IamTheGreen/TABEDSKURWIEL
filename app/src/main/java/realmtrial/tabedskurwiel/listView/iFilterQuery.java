package realmtrial.tabedskurwiel.listView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by mttx on 2017-05-03.
 */

public interface iFilterQuery {
    void setFromDate(int year, int month, int day);
    void setToDate(int year, int month, int day);
    void setQueryTag(String string);

    Date getFromDate();
    Date getToDate();
    String getQueryTag();
}
