package pe.davisapps.androidchat.chat.ui;


import pe.davisapps.androidchat.chat.entities.ChatMessage;

/**
 * Created by ykro.
 */
public interface ChatView {
    void sendMessage();
    void onMessageReceived(ChatMessage msg);
}
