package com.android.inphomeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
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

public class ELoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elogin);
        fAuth = FirebaseAuth.getInstance();
        final TextView CreateAccount = findViewById(R.id.CreateAccount);
        final EditText inputUsername = findViewById(R.id.inputUsername);
        final EditText inputPassword = findViewById(R.id.inputPassword );
        final Button buttonLogin = findViewById(R.id.buttonLogin);
        final ProgressBar progressBar =findViewById(R.id.pro1);

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



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputUsername.getText().toString().trim().isEmpty() || inputPassword.getText().toString().trim().isEmpty()) {
                    inputUsername.setError(" Enter Correct Username ");
                    inputPassword.setError(" Enter Correct Password ");

                } else if (inputPassword.length() > 12 || inputPassword.length() < 6) {
                    inputPassword.setError("Characters should be less than 12 and  more than 6");
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonLogin.setVisibility(View.INVISIBLE);

                final String username = inputUsername.getText().toString();
                final String pass = inputPassword.getText().toString();

                databaseReference.child(username)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String password = snapshot.child("Password")
                                            .getValue(String.class);
                                    if (password.equals(pass)) {
                                        Intent intent =new Intent(ELoginActivity.this,IntroActivity.class);
                                        Pair[] pairs = new Pair[1];

                                        pairs[0] = new Pair<View,String>(findViewById(R.id.buttonLogin),"SIGN IN");

                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                            Toast.makeText(ELoginActivity.this, "Welcome"+ username.trim() +"to InPhoMeet", Toast.LENGTH_SHORT).show();
                                            ActivityOptions options  = ActivityOptions.makeSceneTransitionAnimation(ELoginActivity.this,pairs);
                                            startActivity(intent,options.toBundle());
                                        }
                                        else{
                                            Toast.makeText(ELoginActivity.this, "Welcome"+ username.trim() +"to InPhoMeet", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        }

                                    }
                                    else{
                                        Toast.makeText(ELoginActivity.this, "hey please enter correct data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }

        });

    }
}