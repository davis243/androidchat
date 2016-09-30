package pe.davisapps.androidchat.addcontact.ui;

/**
 * Created by ykro.
 */
public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}
