package realmtrial.tabedskurwiel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import realmtrial.tabedskurwiel.adding.NewData.Day;
import realmtrial.tabedskurwiel.adding.NewData.MidPoint;

/**
 * Created by mttx on 2017-05-01.
 */

public class Randomizator {
    private List<String> cities;
    private Random random = new Random();

    public Randomizator(){
        this.cities = new ArrayList<>();
        addCities();
    }

    public void addCities(){
        cities.add("Warszawa");
        cities.add("Kraków");
        cities.add("Poznań");
        cities.add("Toruń");
        cities.add("Gdańsk");
        cities.add("Katowice");
        cities.add("Kraków");
        cities.add("Tychy");
        cities.add("Gliwice");
        cities.add("Szczecin");
        cities.add("Konin");
        cities.add("Radom");
        cities.add("Kielce");
        cities.add("Wroclaw");
        cities.add("Bydgoszcz");
        cities.add("Żabia Wola");
        cities.add("Żarów");
        cities.add("MiędzyWodzie");

    }

    public RealmList<Day> getRandomDays(){
        RealmList<Day> days = new RealmList<>();

        int workDaysLimiter = 400;
        long dayId = 0;
        for(int k = 0;k<workDaysLimiter;k++){
            Day day = new Day();
            day.setId(k);
            dayId++;
            day.setMidPoints(getMidpoints());
            day.setFinished(true);
            days.add(day);
        }
        return days;
    }


    public RealmList<MidPoint> getMidpoints(){
        RealmList<MidPoint> midPoints = new RealmList<>();
        int randomLimiter = random.nextInt(5)+1;
        int midPointId = 0;
        int cityId = random.nextInt(cities.size());
        int nextCityId = cityId+1;
        int distance = random.nextInt(700)+100;

        for(int i=0;i<randomLimiter;i++){
            MidPoint midPoint = new MidPoint();
            midPoint.setMidPointId(midPointId);
            midPointId++;
            if(cityId>cities.size()-3){
                cityId=0;
            }
            midPoint.setLocationStart(cities.get(cityId));
            try{
                midPoint.setLocationStop(cities.get(nextCityId));
            } catch (IndexOutOfBoundsException ex){
                nextCityId = 0;
            } finally {
                midPoint.setLocationStop(cities.get(nextCityId));
            }

            midPoint.setDistance(String.valueOf(distance));
            midPoint.setHours(String.valueOf(random.nextInt(8)+1));
            midPoint.setMinutes(String.valueOf(random.nextInt(58)+1));
            midPoints.add(midPoint);
            cityId++;

        }
        return midPoints;
    }
}















