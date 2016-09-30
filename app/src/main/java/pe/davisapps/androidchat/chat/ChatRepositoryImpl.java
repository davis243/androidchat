package pe.davisapps.androidchat.chat;



import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;



import pe.davisapps.androidchat.chat.entities.ChatMessage;
import pe.davisapps.androidchat.chat.events.ChatEvent;
import pe.davisapps.androidchat.domain.FirebaseHelper;
import pe.davisapps.androidchat.lib.EventBus;
import pe.davisapps.androidchat.lib.GreenRobotEventBus;


/**
 * Created by ykro.
 */
public class ChatRepositoryImpl implements ChatRepository {
    private String receiver;
    private FirebaseHelper helper;
    private ChildEventListener chatEventListener;

    public ChatRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void subscribeForChatUpates() {
        if (chatEventListener == null) {
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();
                    msgSender = msgSender.replace("_",".");

                    String currentUserEmail = helper.getAuthUserEmail();
                    chatMessage.setSentByMe(msgSender.equals(currentUserEmail));

                    ChatEvent chatEvent = new ChatEvent(chatMessage);
                    EventBus eventBus = GreenRobotEventBus.getInstance();
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }


            };
            helper.getChatReference(receiver).addChildEventListener(chatEventListener);
        }
    }

    @Override
    public void unSubscribeForChatUpates() {
        if (chatEventListener != null) {
            helper.getChatReference(receiver).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void destroyChatListener() {
        chatEventListener = null;
    }

    @Override
    public void sendMessage(String msg) {
        String keySender = helper.getAuthUserEmail().replace(".","_");
        ChatMessage chatMessage = new ChatMessage(keySender, msg);
        Firebase chatsReference = helper.getChatReference(receiver);
        chatsReference.push().setValue(chatMessage);
    }

    public void changeUserConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }

}
