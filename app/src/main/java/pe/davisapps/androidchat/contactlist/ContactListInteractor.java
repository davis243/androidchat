package pe.davisapps.androidchat.contactlist;

/**
 * Created by ykro.
 */
public interface ContactListInteractor {
    void subscribeForContactEvents();
    void unSubscribeForContactEvents();
    void destroyContactListListener();
    void removeContact(String email);
}
