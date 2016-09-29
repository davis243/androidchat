package pe.davisapps.androidchat;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private Firebase dataReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    public final static String CONTACTS_PATH = "contacts";
    public final static String FIREBASE_URL = "https://todolist-6d704.firebaseio.com/";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {

        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper(){
        dataReference = new Firebase(FIREBASE_URL);
    }

    public Firebase getDataReference() {

        return dataReference;
    }

    public String getAuthUserEmail() {
        AuthData authData = dataReference.getAuth();
        String email = null;
        if (authData != null) {
            Map<String,Object> provideData = authData.getProviderData();
            email = provideData.get("email").toString();
        }
        return email;
    }
    public Firebase getUserReference(String email){
        Firebase userReference = null;
        if(email != null){
            String emailkey = email.replace(".","_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailkey);
        }
        return userReference;
    }
    public Firebase getMyUserReference(){

        return getUserReference(getAuthUserEmail());
    }
    public Firebase getContactsReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }
    public Firebase getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }
    public Firebase getOneContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }
    public Firebase getChatReference(String receiver)
    {
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if(keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
            return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online){
        if(getMyUserReference()!=null){
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online",online);
            getMyUserReference().updateChildren(updates);

        }

    }


    public void signOff(boolean online)
    {
        notifyContactsOfConnectionChange(false,true);
    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String email = child.getKey();
                        Firebase reference = getOneContactReference(email, myEmail);
                        reference.setValue(online);
                        if (signoff) {
                            dataReference.unauth();
                        }

                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {}

            });

    }




}
