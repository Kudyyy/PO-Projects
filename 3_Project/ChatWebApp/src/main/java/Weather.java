import org.json.JSONObject;

/**
 * Created by daniel on 23.01.2017.
 */
public class Weather {

    private static String apiURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String apiKey = "&APPID=";

    public static String simpleForCity(String city) throws Exception {
        JSONObject weather = JSONCreator.getJSONOBjectForURL(apiURL + city + apiKey);
        Double temp;
        Double press;
        String foundCity;
        if (weather.has("main") && weather.has("name") && weather.getJSONObject("main").has("temp") && weather.getJSONObject("main").has("pressure")) {
            temp = weather.getJSONObject("main").getDouble("temp");
            press = weather.getJSONObject("main").getDouble("pressure");
            foundCity = weather.getString("name");
            return "Found weather for city " + foundCity + ", temperature there: "
                    + Double.toString( temp - 273.15) + "C, pressure: " + Double.toString(press) + "hPa";
        } else return "I didn't found weather for that city broo";

    }


}
