import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by daniel on 23.01.2017.
 */

public class JSONCreator {

    public static JSONObject getJSONOBjectForURL(String url) throws Exception {
        URL JSONurl = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(JSONurl.openStream()));
        String inputLine;
        String JSONString = "";
        while ((inputLine = in.readLine()) != null) {
            JSONString += inputLine;
        }
        JSONObject main = new JSONObject(JSONString);
        in.close();
        return main;
    }
}
