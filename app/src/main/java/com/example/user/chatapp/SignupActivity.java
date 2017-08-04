package com.example.user.chatapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {
    private EditText et_email_signup;
    private EditText et_username_signup;
    private EditText et_password_signup;
    private EditText et_repassword_signup;
    private Button bt_signup_signup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_email_signup = (EditText) findViewById(R.id.et_email_signup);
        et_username_signup = (EditText) findViewById(R.id.et_usermane_signup);
        et_password_signup = (EditText) findViewById(R.id.et_password_signup);
        et_repassword_signup = (EditText) findViewById(R.id.et_repassword_signup);
        bt_signup_signup = (Button) findViewById(R.id.bt_signup_signup);
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }
            }
        };

        bt_signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    protected void signUp() {
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference usersRef = mDatabase.getReference("users");
        final String username = et_username_signup.getText().toString();
        String email = et_email_signup.getText().toString();
        String password = et_password_signup.getText().toString();
        String repassword = et_repassword_signup.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            Toast.makeText(SignupActivity.this, "Fields is Empty", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                    } else {
                        UserData user = new UserData(username);
                        usersRef.child(mAuth.getCurrentUser().getUid()).setValue(user);
                    }
                }
            });
        }

    }
}


class UserData{
    public String username;

    public UserData() {

    }

    public UserData(String username) {
        this.username = username;
    }

}

