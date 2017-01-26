import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static j2html.TagCreator.*;

/**
 * Created by daniel on 21.01.2017.
 */

//    )*%#^#@$&(@#%^*^#))))

@WebSocket
public class BotWebSocketHandler {

    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws JSONException {
        try {
            user.getRemote().sendString(String.valueOf(new JSONObject()
                .put("userMessage", createHtmlMessageFromSender("Wise human",message))));
            if(message.matches("Weather for (.+)")){
                String city = message.split("Weather for ")[1];
                user.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender("Not so wise machine",Weather.simpleForCity(city)))));
            }
            else if (message.equals("Day of the week ?")){
                user.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender("Not so wise machine",getDay()))));
            }
            else if (message.equals("Hour ?")){
                user.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender("Not so wise machine",getTime()))));
            }
            else {
                user.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender("Not so wise machine","You Waaaaaat ?"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private String getDay(){
        String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday",
                "Friday", "Saturday" };
        Calendar now = Calendar.getInstance();
        return strDays[now.get(Calendar.DAY_OF_WEEK)-1];
    }

    private String getTime(){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(now.getTime());
    }

    private String createHtmlMessageFromSender(String sender, String message) {
        return article().with(
                b(sender + " says:"),
                p(message),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }

}
