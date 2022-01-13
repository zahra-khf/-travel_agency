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
    private final LocalDate date = LocalDate.now();

    private void addToTemperatures(String date, double temps, ArrayList temperatures){
        HashMap<String, Object> temp = new HashMap<String, Object>();
        temp.put("date", date);
        temp.put("temperature", Math.round(temps));
        temperatures.add(temp.clone());
    }

    @GetMapping(path = "/api/temperature")
    Object getTemprature(@RequestParam String country) {
        try {
            ArrayList tempratureList = new ArrayList();
            HashMap<String, Object> response = new HashMap<String, Object>();
            HashMap<String, Object> temp = new HashMap<String, Object>();
            addToTemperatures(date.minusDays(1).toString(),temperatureService.getTemperature(country),tempratureList);
            addToTemperatures(date.minusDays(2).toString(),temperatureService.getTemperature(country),tempratureList);
            response.put("country", country);
            response.put("temperatures", tempratureList);
            return response;
        }catch (UnknownCountryException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
    }
}
