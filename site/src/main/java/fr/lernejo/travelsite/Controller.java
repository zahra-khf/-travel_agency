package fr.lernejo.travelsite;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.*;
import org.everit.json.schema.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.copyOf;

@RestController
public class Controller {
 final private Map<String, Object> user = new HashMap<String, Object>();
 @PostMapping (path="/api/inscription")
 Map<String,Object>inscri(@RequestBody Map<String,Object>body) throws Exception {
     JSONObject jSchema = new JSONObject(new JSONTokener(getClass().getResourceAsStream("/user.json")));
     JSONObject bodyJson = new JSONObject(body);
     Schema schema = SchemaLoader.load(jSchema);
     schema.validate(bodyJson);
     if (user.containsKey(bodyJson.get("userName")))
         throw new Exception("This user is already exist");
     user.put(String.valueOf(bodyJson.get("userName")), copyOf(body));
     return user;
 }
    @GetMapping(path = "/api/travels")
    ArrayList<Object> travels(@RequestParam String userName) {
        JSONObject user1 = new JSONObject(user);
        JSONObject selectedUserData = (JSONObject) user1.get(userName);
        ArrayList<Object> countries = new ArrayList<Object>();
        HashMap<String, Object> country = new HashMap<String, Object>();
        country.put("name","Tunisia");
        country.put("temperature",20.0f);
        countries.add(country);
        return countries;
    }
}
