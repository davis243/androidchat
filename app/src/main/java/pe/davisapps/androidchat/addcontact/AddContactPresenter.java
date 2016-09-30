package pe.davisapps.androidchat.addcontact;


import pe.davisapps.androidchat.addcontact.events.AddContactEvent;

/**
 * Created by ykro.
 */
public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}

