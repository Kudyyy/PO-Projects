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
        BufferedReader in = new BufferedReader( new InputStreamReader(JSONurl.openStream()));

        String inputLine;
        String JSONString = "";
        while ((inputLine = in.readLine()) != null) {
            JSONString += inputLine;
        }
        in.close();
        return new JSONObject(JSONString);
    }

}
