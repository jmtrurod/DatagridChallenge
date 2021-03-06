package com.redhat.developergames.repository;

import com.redhat.developergames.model.Weather;
import com.redhat.developergames.model.WeatherCondition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.infinispan.manager.EmbeddedCacheManager;

@Component
public class WeatherRepository {

    private final EmbeddedCacheManager cacheManager;

    @Autowired
    public WeatherRepository(EmbeddedCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

   List<String> locations = Arrays.asList(
         "paris",
         "london",
         "berlin",
         "madrid",
         "lisboa",
         "ibiza",
         "oslo",
         "stockholm",
         "lima",
         "tokyo");

   private Random random = new Random();

   public Weather getByLocation(String location, String username) {

    cacheManager.getCache("testCache").put(username, location);

        Weather weather = (Weather) cacheManager.getCache("testCache").get(location);
        if (weather == null) {
            try{
                weather = fetchWeather(location);
            } catch (Exception e) {
                weather = null;
            }
            cacheManager.getCache("testCache").put(location, weather);
        }
        return weather;
       
   }

   private Weather fetchWeather(String location) {
      // Emulates a slow service. Don't change this. Use Spring Boot Cache with Data Grid
      try {
         TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }

      if(!locations.contains(location)) {
         return null;
      }

      return new Weather(random.nextFloat() * 20f + 5f, WeatherCondition.values()[random.nextInt(10)], location);
   }

   public String getLastLocation(String username) {
       String lastLocation = (String) cacheManager.getCache("testCache").get(username);
       return lastLocation == null ? "There's no location for user " + username + " yet!" : lastLocation;
   }
}
