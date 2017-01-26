import org.eclipse.jetty.websocket.api.Session;

/**
 * Created by daniel on 21.01.2017.
 */
public class User {
    private final Session session;
    private final String username;

    public User(Session ses,String name){
        session = ses;
        username = name;
    }

    public String getUsername() {
        return username;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        return this.session.equals(obj);
    }
}
