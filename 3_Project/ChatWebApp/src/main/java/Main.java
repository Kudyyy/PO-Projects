import java.util.LinkedList;

import static spark.Spark.init;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

/**
 * Created by daniel on 21.01.2017.
 */


public class Main {

    static LinkedList<Channel> channels = new LinkedList<>();

    public static void main(String[] args) {
        staticFiles.location("/public");
        webSocket("/main", ChannelWebSocketHandler.class);
        webSocket("/channel",ChatWebsocketHandler.class);
        webSocket("/bot",BotWebSocketHandler.class);
        init();
    }

}
