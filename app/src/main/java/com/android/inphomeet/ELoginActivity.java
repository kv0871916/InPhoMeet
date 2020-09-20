package com.android.inphomeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.connection.ConnectionAuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider;

import java.util.HashMap;

public class ELoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    MaterialCheckBox rememberMe;
    MaterialTextView forgetPassword;
    TextInputEditText inputPhoneNumber,inputPassword;
//    private String mCustomToken;
//
//    public String getmCustomToken() {
//        return mCustomToken;
//    }
//    String mcustomToken = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elogin);
        fAuth = FirebaseAuth.getInstance();
        final TextView CreateAccount = findViewById(R.id.CreateAccount);
        inputPhoneNumber = findViewById(R.id.inputPhoneNumber);
        inputPassword = findViewById(R.id.inputPassword );
        final Button buttonLogin = findViewById(R.id.buttonLogin);
        rememberMe = findViewById(R.id.remember_me);
        forgetPassword = findViewById(R.id.forget_password);
//        if(fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
//            finish();
//        }

        //check user is already created session
        SessionManger sessionManger = new SessionManger(ELoginActivity.this,SessionManger.SESSION_REMEMBERME);
        if(sessionManger.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManger.getRememberMeDetailsFromSession();
            inputPhoneNumber.setText(rememberMeDetails.get(SessionManger.KEY_SESSIONPHONENUMBER));
            inputPassword.setText(rememberMeDetails.get(SessionManger.KEY_SESSIONPASSWORD));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ELoginActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

       //String uid = databaseReference.getPath();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputPhoneNumber.getText().toString().trim().isEmpty() || inputPassword.getText().toString().trim().isEmpty()) {
                    inputPhoneNumber.setError(" Enter Correct Mobile Number");
                    inputPassword.setError(" Enter Correct Password ");

                } else if (inputPassword.length() > 12 || inputPassword.length() < 6) {
                    inputPassword.setError("Characters should be less than 12 and  more than 6");
                } else {
                    buttonLogin.setVisibility(View.VISIBLE);
                    final String getno = inputPhoneNumber.getText().toString();
                    final String pass = inputPassword.getText().toString().toLowerCase();

                    databaseReference.child(getno).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String getpass = snapshot.child("Password").getValue(String.class);
                                String getusername = snapshot.child("Username").getValue(String.class);
                                if(pass.equals(getpass)){
                                    Toast.makeText( ELoginActivity.this,"Welcome User "+getusername+" to InPho-Meet ", Toast.LENGTH_SHORT).show();
                                    //store data
                                    String _pass = snapshot.child("Password").getValue(String.class);
                                    String _user = snapshot.child("Username").getValue(String.class);
                                    String _name = snapshot.child("FullName").getValue(String.class);
                                    String _number = snapshot.child("PhoneNumber").getValue(String.class);
                                    String _gender = snapshot.child("Gender").getValue(String.class);
                                    if(rememberMe.isChecked()){
                                        SessionManger sessionManger = new SessionManger(ELoginActivity.this,SessionManger.SESSION_REMEMBERME);
                                        sessionManger.createRememberMeSession(_number,_pass);
                                    }
                                    //create shared preference
                                    SessionManger sessionManger = new SessionManger(ELoginActivity.this,SessionManger.SESSION_USERSESSION);
                                    sessionManger.createLoginSession(_name,_user,_gender,_number,_pass);

                                   // startActivity(new Intent(getApplicationContext(),NavigationheaderActivity.class));

                                    Intent intent =new Intent(getApplicationContext(),IntroActivity.class);
                                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(ELoginActivity.this, " Your entered password is wrong ", Toast.LENGTH_LONG).show();

                                }
                            }
                            else {
                                Toast.makeText(ELoginActivity.this, " Your no. +91-"+getno+" not exists ", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ELoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }
}