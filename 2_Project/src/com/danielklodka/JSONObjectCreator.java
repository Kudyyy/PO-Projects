package com.danielklodka;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.*;
import org.apache.commons.lang3.*;
/**
 * Created by klodka on 2016-12-15.
 */
public class JSONObjectCreator {

    public static JSONObject getJSONOBjectForURL(String url) throws Exception {
        URL JSONurl = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(JSONurl.openStream()));
        String next;
        String inputLine;
        String JSONString = "";
        while ((inputLine = in.readLine()) != null) {
            JSONString += inputLine;
        }
        JSONObject main = new JSONObject(JSONString);
        in.close();
        if (!main.has("Links")) return main;
        while(!main.getJSONObject("Links").isNull("next")){
            //next = main.getJSONObject("Links").getString("next");
            JSONString = "";
            in = new BufferedReader(new InputStreamReader(JSONurl.openStream()));
            while ((inputLine = in.readLine()) != null) {
                JSONString += inputLine;
            }
            in.close();

            JSONObject page = new JSONObject(JSONString);
            JSONArray depArr = page.getJSONArray("Dataobject");
            for (int i = 0; i < depArr.length(); i++){
                main.getJSONArray("Dataobject").put(depArr.getJSONObject(i));
            }
            if (page.getJSONObject("Links").isNull("next")) break;
            next = page.getJSONObject("Links").getString("next");
           // System.out.println(next);
            JSONurl = new URL(next);
        }
        return main;
    }

}
