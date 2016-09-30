package pe.davisapps.androidchat.chat.events;


import pe.davisapps.androidchat.chat.entities.ChatMessage;

/**
 * Created by ykro.
 */
public class ChatEvent {
    ChatMessage msg;

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMessage() {
        return msg;
    }
}
