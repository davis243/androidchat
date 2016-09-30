package pe.davisapps.androidchat.contactlist.events;


import pe.davisapps.androidchat.contactlist.entities.User;

/**
 * Created by ykro.
 */
public class ContactListEvent {
    private User user;
    private int eventType;

    public final static int onContactAdded = 0;
    public final static int onContactChanged = 1;
    public final static int onContactRemoved = 2;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
