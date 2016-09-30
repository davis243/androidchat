package pe.davisapps.androidchat.contactlist;

/**
 * Created by ykro.
 */
public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}
