package realmtrial.tabedskurwiel.Data;

/**
 * Created by mttx on 2017-04-18.
 */
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mttx on 2017-04-17.
 */

public class Randomizer {
    private List<Route> routeList = new ArrayList<>();
    private Random random = new Random();
    private int id = 0;

    public List<Route> randomizedRoutes() {

        List<String> strings = new ArrayList<>();

        strings.add( "Wrocław");
        strings.add( "Poznań");
        strings.add( "Gdynia");
        strings.add( "Terespol");
        strings.add( "Kraków");
        strings.add( "Tychy");
        strings.add( "Gliwice");

        for (int i = 0; i < 50; i++) {
            Route route = new Route();
            for (int z = 0; z < random.nextInt(6) + 1; z++) {

                route.setLocationStart(strings.get(random.nextInt(6)));
                route.setLocationStop(strings.get(random.nextInt(6)));
                route.setDistance(random.nextInt(500) + 150);
                route.setHours(random.nextInt(10)+2);
                route.setMinutes(random.nextInt(59)+15);
            }
            id++;
            route.setId(id);
            routeList.add(route);
        }
        return routeList;
    }
}



























