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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ELoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
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
        final EditText inputPhoneNumber = findViewById(R.id.inputPhoneNumber);
        final EditText inputPassword = findViewById(R.id.inputPassword );
        final Button buttonLogin = findViewById(R.id.buttonLogin);

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
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

                                    Intent intent =new Intent(ELoginActivity.this,IntroActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
//                    // if(mcustomToken)
//                    fAuth.signInWithCustomToken(mcustomToken)
//                            .addOnCompleteListener(ELoginActivity.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//
//
//                                    } else {
//
//                                    }
//                                }
//                            });

//                databaseReference
//                        .addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                     String getpass = "+91"+inputPassword.getText().toString();
//                                     String checkpass = snapshot.child("Password").getValue().toString();
//                                       if (getpass.equals(checkpass)) {
//                                           Toast.makeText(ELoginActivity.this, "+91"+inputPhoneNumber, Toast.LENGTH_SHORT).show();
//                                           Intent intent =new Intent(ELoginActivity.this,IntroActivity.class);
//                                           startActivity(intent);
//                                       }
//
//                                           Toast.makeText(ELoginActivity.this, "hey please enter correct data", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                Toast.makeText(ELoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                }
            }
        });

    }
}