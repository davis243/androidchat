package pe.davisapps.androidchat.contactlist.ui;


import pe.davisapps.androidchat.contactlist.entities.User;

/**
 * Created by ykro.
 */
public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
