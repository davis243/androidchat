package pe.davisapps.androidchat.chat;


import pe.davisapps.androidchat.chat.events.ChatEvent;

/**
 * Created by ykro.
 */
public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);

    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);


}
