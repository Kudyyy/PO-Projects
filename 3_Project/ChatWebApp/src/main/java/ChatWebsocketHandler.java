import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static j2html.TagCreator.*;

/**
 * Created by daniel on 21.01.2017.
 */

//    )*%#^#@$&(@#%^*^#))))

@WebSocket
public class ChatWebsocketHandler {

    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        LinkedList<HttpCookie>  cookies= new LinkedList<>(user.getUpgradeRequest().getCookies());
        String username ="No name";
        for (HttpCookie cookie: cookies) {
            System.out.println(cookie.getName());
            if (cookie.getName().equals("username")){
                username = cookie.getValue();
            }
        }

        System.out.println("NA KANALE  " + username);

        //Chat.userUsernameMap.put(user, username);
        //Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("WYSZEDL KTOS");
        for (Channel channel : Main.channels) {
            if (channel.hasUser(user)) {
                System.out.println(channel.users);
                String username = channel.getUser(user);
                channel.removeUser(user);
                System.out.println(channel.users);
                broadcastMessage("Server",channel.getNumberOfChannel(),username+" has left the chat");
                break;
            }
        }
        //String username = Chat.userUsernameMap.get(user);
        //Chat.userUsernameMap.remove(user);
        //Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.println(message);
        System.out.println(message.split("PoIooPokgTac").length);
        if(message.split("PoIooPokgTac").length ==3 && message.split("PoIooPokgTac")[2].equals("NEW")) {
            for (Channel channel : Main.channels) {
                if (channel.getNumberOfChannel() == Integer.parseInt(message.split("PoIooPokgTac")[1])) {
                    channel.addUser(new User(user, message.split("PoIooPokgTac")[0]));
                    broadcastMessage("Server",Integer.parseInt(message.split("PoIooPokgTac")[1]),message.split("PoIooPokgTac")[0]+" has joined the chat");
                    break;
                }
            }
        }
        else if(message.split("PoIooPokgTac").length ==3){
            broadcastMessage(message.split("PoIooPokgTac")[0],Integer.parseInt(message.split("PoIooPokgTac")[1]),message.split("PoIooPokgTac")[2]);
        }
    }


    private void broadcastMessage(String sender,int channelNumber, String message) {
        System.out.println(createHtmlMessageFromSender(sender,message));
        Main.channels.forEach(channel -> {
            if(channel.getNumberOfChannel() == channelNumber) {
                channel.users.forEach(user->{
                    try {
                        user.getSession().getRemote().sendString(String.valueOf(new JSONObject()
                                .put("userMessage", createHtmlMessageFromSender(sender, message))
                                .put("userlist", channel.users)));
                    }
                     catch (Exception e) {
                    e.printStackTrace();
                    }

                });
            }
        });
    }

    private String createHtmlMessageFromSender(String sender, String message) {
        return article().with(
                b(sender + " says:"),
                p(message),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }

}
