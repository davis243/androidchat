package pe.davisapps.androidchat.addcontact;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;




import pe.davisapps.androidchat.addcontact.events.AddContactEvent;
import pe.davisapps.androidchat.contactlist.entities.User;
import pe.davisapps.androidchat.domain.FirebaseHelper;
import pe.davisapps.androidchat.lib.EventBus;
import pe.davisapps.androidchat.lib.GreenRobotEventBus;


/**
 * Created by ykro.
 */
public class AddContactRepositoryImpl implements AddContactRepository {
    @Override
    public void addContact(final String email) {
        final String key = email.replace(".","_");

        FirebaseHelper helper = FirebaseHelper.getInstance();
        final Firebase userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                AddContactEvent event = new AddContactEvent();
                if (user != null) {
                    boolean online = user.isOnline();
                    FirebaseHelper helper = FirebaseHelper.getInstance();

                    Firebase userContactsReference = helper.getMyContactsReference();
                    userContactsReference.child(key).setValue(online);

                    String currentUserEmailKey = helper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".","_");
                    Firebase reverseUserContactsReference = helper.getContactsReference(email);
                    reverseUserContactsReference.child(currentUserEmailKey).setValue(true);
                } else {
                    event.setError(true);
                }
                EventBus eventBus = GreenRobotEventBus.getInstance();
                eventBus.post(event);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }
}
