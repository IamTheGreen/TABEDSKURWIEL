package realmtrial.tabedskurwiel.summary;

import io.realm.Realm;
import realmtrial.tabedskurwiel.Data.Day;

/**
 * Created by mttx on 2017-05-03.
 */

public class Model implements iSummary.Model{

    private Realm realm;

    @Override
    public int getTotalDistance(SummaryFilter selectedFilter) {

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        int totalDistance = (int) realm.where(Day.class).sum("midPoints.distance");
        return totalDistance;
    }

    @Override
    public int getTotalHours(SummaryFilter selectedFilter) {
        return 0;
    }
}
