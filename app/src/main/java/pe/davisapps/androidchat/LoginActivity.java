package pe.davisapps.androidchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.firebase.client.Firebase;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.btnSignin)
    Button btnSignIn;
    @Bind(R.id.btnSignup)           Button btnSignUp;
    @Bind(R.id.editTxtEmail)
    EditText inputEmail;
    @Bind(R.id.editTxtPassword)     EditText inputPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnSignin)
    public void handleSignin(){
        Log.e("AndroidChat",inputEmail.getText().toString());
    }
}
