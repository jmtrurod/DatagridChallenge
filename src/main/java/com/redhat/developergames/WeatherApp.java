package com.redhat.developergames;

import com.redhat.developergames.model.Weather;
import com.redhat.developergames.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@RestController
@EnableCaching
public class WeatherApp {

   @Autowired
   WeatherRepository weatherRepository;

   @GetMapping("/")
   public String index() {
      return "Greetings from Spring Boot with Data Grid!";
   }

   @GetMapping("/weather/{location}/{username}")
   public Object getByLocation(@PathVariable String location, @PathVariable String username) {
      Weather weather = weatherRepository.getByLocation(location, username);
      if (weather == null) {
         return String.format("Weather for location %s not found", location);
      }
      return weather;
   }

   @GetMapping("/latest/{username}")
   public String latestLocation(@PathVariable String username) {
      return weatherRepository.getLastLocation(username);
   }

   public static void main(String... args) {
      new SpringApplicationBuilder().sources(WeatherApp.class).run(args);
   }
}
