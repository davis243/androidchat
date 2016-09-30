package pe.davisapps.androidchat.login;



import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DataSnapshot;




import pe.davisapps.androidchat.contactlist.entities.User;
import pe.davisapps.androidchat.domain.FirebaseHelper;
import pe.davisapps.androidchat.lib.EventBus;
import pe.davisapps.androidchat.lib.GreenRobotEventBus;


/**
 * Created by ykro.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper helper;
    private Firebase dataReference;
    private Firebase myUserReference;

    public LoginRepositoryImpl(){
        helper = FirebaseHelper.getInstance();
        dataReference = helper.getDataReference();
        myUserReference = helper.getMyUserReference();
    }

    @Override
    public void signUp(final String email, final String password) {
      /*  FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                       */ postEvent(LoginEvent.onSignUpSuccess);
                       // signIn(email, password);
                /*    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.onSignUpError, e.getMessage());
                    }
                });*/
    }

    @Override
    public void signIn(String email, String password) {
        dataReference.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {


                    myUserReference = helper.getMyUserReference();
                    myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            User currentUser = dataSnapshot.getValue(User.class);

                            if(currentUser == null){
                                String email=helper.getAuthUserEmail();
                                if(email != null){
                                    currentUser = new User();
                                    myUserReference.setValue(currentUser);
                                }
                            }
                            helper.changeUserConnectionStatus(User.ONLINE);
                            postEvent(LoginEvent.onSignUpSuccess);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
                        }

                    });

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                postEvent(LoginEvent.onSignInError,firebaseError.getMessage());

            }
        });
     /*   try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            myUserReference = helper.getMyUserReference();
                            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    initSignIn(snapshot);
                                }
                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                    postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            postEvent(LoginEvent.onSignInError, e.getMessage());
                        }
                    });
        } catch (Exception e) {
            postEvent(LoginEvent.onSignInError, e.getMessage());
        }*/
    }

    @Override
    public void checkAlreadyAuthenticated() {
       /* if (Firebase.getInstance().getCurrentUser() != null) {
            myUserReference = helper.getMyUserReference();
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    initSignIn(snapshot);
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
                }
            });
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }*/
    }

    private void registerNewUser() {
        String email = helper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User(email, true, null);
            myUserReference.setValue(currentUser);
        }
    }

    private void initSignIn(DataSnapshot snapshot){
        User currentUser = snapshot.getValue(User.class);

        if (currentUser == null) {
            registerNewUser();
        }
        helper.changeUserConnectionStatus(User.ONLINE);
        postEvent(LoginEvent.onSignInSuccess);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

}
