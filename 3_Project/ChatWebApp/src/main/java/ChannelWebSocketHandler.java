import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;
import static spark.Spark.*;

@WebSocket
public class ChannelWebSocketHandler {

    ArrayList<Session> usersSessions = new ArrayList<Session>();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {

        System.out.println("Connected");
        usersSessions.add(user);
        Main.channels.forEach(
                val->{
                    try {
                        user.getRemote().sendString(String.valueOf(new JSONObject()
                                .put("userMessage", createHtmlChannel(val.getOwner(),val.toString()))
                                .put("channelNumber",val.getNumberOfChannel())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

        );

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {

        System.out.println("Disconnected");
        usersSessions.remove(user);

    }

    @OnWebSocketMessage
    public void onMessage(Session session, String channelName) {

        LinkedList<HttpCookie> cookies= new LinkedList<>(session.getUpgradeRequest().getCookies());
        String username ="No name";
        for (HttpCookie cookie: cookies) {
            System.out.println(cookie.getName());
            if (cookie.getName().equals("username")){
                username = cookie.getValue();
            }
        }
        System.out.println(channelName);
        Main.channels.addLast(new Channel(channelName,username));
        usersSessions.forEach(
                ses->{
                    try {
                        ses.getRemote().sendString(String.valueOf(new JSONObject()
                                .put("userMessage", createHtmlChannel(Main.channels.getLast().getOwner() ,Main.channels.getLast().toString()))
                                .put("channelNumber",Main.channels.getLast().getNumberOfChannel())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

        );
    }

    private String createHtmlChannel(String username, String message) {
        return article().with(
                b(username+"'s channel"),
                p(message)
        ).render();
    }



}