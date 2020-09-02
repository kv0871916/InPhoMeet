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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView loginhere = findViewById(R.id.loginhere);
        final EditText inputFname = findViewById(R.id.inputFname);
        final EditText inputMobile = findViewById(R.id.inputMobile);
        final EditText inputnewEmail = findViewById(R.id.inputnewEmail);
        final EditText inputnewPassword = findViewById(R.id.inputnewPassword);
        final Button buttonFLName = findViewById(R.id.buttonFLName);
        final ProgressBar progressBar =findViewById(R.id.pro1);
        fAuth = FirebaseAuth.getInstance();

        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HomeActivity.this,ELoginActivity.class);
                startActivity(intent);
            }
        });
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }

        buttonFLName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFname.getText().toString().trim().isEmpty()
                        || inputMobile.getText().toString().trim().isEmpty()
                        || inputnewEmail.toString().trim().isEmpty()
                        || inputnewPassword.toString().trim().isEmpty()
                ) {

                    Toast.makeText(HomeActivity.this, "Please Details Correctly", Toast.LENGTH_SHORT).show();
                } else if (inputnewPassword.length() < 6
                        || inputnewPassword.length() > 12) {
                    inputnewPassword.setError("Password should be more than 12 and less than 6 Characters");
                }
                else
                    {
                progressBar.setVisibility(View.VISIBLE);
                buttonFLName.setVisibility(View.INVISIBLE);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = inputnewEmail.getText().toString().trim();
                String password = inputnewPassword.getText().toString().trim();

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HomeActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91" + inputMobile.getText().toString(),
                                    60,
                                    TimeUnit.SECONDS,
                                    HomeActivity.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            progressBar.setVisibility(View.GONE);
                                            buttonFLName.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            progressBar.setVisibility(View.GONE);
                                            buttonFLName.setVisibility(View.VISIBLE);
                                            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            progressBar.setVisibility(View.GONE);
                                            buttonFLName.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                            intent.putExtra("mobile", inputMobile.getText().toString());
                                            intent.putExtra("verificationId", verificationId);
                                            startActivity(intent);
                                        }
                                    }
                            );


                        }
                        else{
                            Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_LONG).show();

                        }

                    }
                });


//                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
//                            .setDisplayName(inputFname.toString().trim())
//                            .build();
//                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Intent intent = new Intent(HomeActivity.this,IntroActivity.class);
//                                startActivity(intent);
//                                Toast.makeText(HomeActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Toast.makeText(HomeActivity.this, "********", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
//                                startActivity(intent);
//                            }
//                        }
//                    });
                 }
                }


        });

    }
}