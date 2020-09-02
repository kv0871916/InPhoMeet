package com.android.inphomeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ELoginActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elogin);
        fAuth = FirebaseAuth.getInstance();
        final TextView CreateAccount = findViewById(R.id.CreateAccount);
        final EditText inputEmail = findViewById(R.id.inputEmail);
        final EditText inputPassword = findViewById(R.id.inputPassword );
        final Button buttonLogin = findViewById(R.id.buttonLogin);
        final ProgressBar progressBar =findViewById(R.id.pro1);

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ELoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputEmail.getText().toString().trim().isEmpty()||inputPassword.getText().toString().trim().isEmpty()){
                    inputEmail.setError(" Enter Correct Email ");
                    inputPassword.setError(" Enter Correct Password ");

                }else if(inputPassword.length() >12 ||inputPassword.length() <6){
                    inputPassword.setError("Characters should be less than 12 and  more than 6");
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    buttonLogin.setVisibility(View.INVISIBLE);

                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();

                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ELoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                            }
                            else {
                                Toast.makeText(ELoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}