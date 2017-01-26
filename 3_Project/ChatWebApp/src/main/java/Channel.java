

import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.Iterator;

import static spark.Spark.init;
import static spark.Spark.webSocket;
/**
 * Created by daniel on 21.01.2017.
 */
public class Channel {

    static int number = 0;

    private int numberOfChannel;
    private final String name;
    public ArrayList<User> users = new ArrayList<>();
    private String owner;

    public Channel(String channelName,String own){
        this.numberOfChannel = Channel.number;
        Channel.number += 1;
        this.name = channelName;
        this.owner = own;

    }

    @Override
    public String toString() {
        return name;
    }

    public int getNumberOfChannel() {
        return numberOfChannel;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public String getUser(Session ses) {
        for (User user : this.users){
            if (user.equals(ses)) return user.toString();
        }
        return "";
    }

    public void removeUser(Session userSes) {
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            if (it.next().equals(userSes)) {
                it.remove();
                break;
            }
        }
    }

    public boolean hasUser(Session userSes){
        for (User user: this.users){
            if(user.equals(userSes)) return true;
        }
        return false;
    }

    public String getOwner() {
        return owner;
    }
}
