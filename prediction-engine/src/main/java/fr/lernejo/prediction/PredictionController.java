package fr.lernejo.prediction;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.copyOf;

@RestController
public class PredictionController {
    private final TemperatureService temperatureService = new TemperatureService();

    @GetMapping(path = "/api/temperatrue")
    Object getTemprature(@RequestParam String country) {
        try {
            double tempratureResult1 = temperatureService.getTemperature(country);
            double tempratureResult2 = temperatureService.getTemperature(country);
            ArrayList tempratureList = new ArrayList();
            LocalDate date = LocalDate.now();
            HashMap<String, Object> response = new HashMap<String, Object>();
            HashMap<String, Object> temp = new HashMap<String, Object>();
            temp.put("date", date.minusDays(1).toString());
            temp.put("temprature", tempratureResult1);
            tempratureList.add(temp.clone());
            temp.put("date", date.minusDays(2).toString());
            temp.put("temprature", tempratureResult2);
            tempratureList.add(temp.clone());
            response.put("country", country);
            response.put("tempratures", tempratureList);
            return response;
        }catch (UnknownCountryException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
    }
}
