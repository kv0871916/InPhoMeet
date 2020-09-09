package com.android.inphomeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class NewUserActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    String verificationId;
    DatabaseReference databaseReference;
    String gender="";
    private EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        final TextView loginhere = findViewById(R.id.loginhere);
        final EditText inputFname = findViewById(R.id.inputFname);
        final EditText inputMobile = findViewById(R.id.inputMobile);
        final EditText inputnewUsername = findViewById(R.id.inputnewUsername);
        final EditText inputnewPassword = findViewById(R.id.inputnewPassword);
        final Button buttonFLName = findViewById(R.id.buttonFLName);
        final RadioButton male = findViewById(R.id.male);
        final RadioButton female = findViewById(R.id.female);
        final RadioButton other = findViewById(R.id.other);

        fAuth = FirebaseAuth.getInstance();
        verificationId = getIntent().getStringExtra("verificationId");
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
         inputCode3 = findViewById(R.id.inputCode3);
         inputCode4 = findViewById(R.id.inputCode4);
         inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        final Button buttonOTPVerify= findViewById(R.id.buttonOTPVerify);
        setupOTPInputs();
        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(NewUserActivity.this,SendOTPActivity.class);
                startActivity(intent);
            }
        });
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        buttonFLName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFname.getText().toString().trim().isEmpty()
                        || inputMobile.getText().toString().trim().isEmpty()
                        || inputnewUsername.toString().trim().isEmpty()
                        || inputnewPassword.toString().trim().isEmpty()
                ) {
                    Toast.makeText(NewUserActivity.this, "Please Details Correctly", Toast.LENGTH_SHORT).show();
                }if (inputnewPassword.length() < 6
                        || inputnewPassword.length() > 12) {
                    inputnewPassword.setError("Password should be more than 12 and less than 6 Characters");
                }

                buttonFLName.setVisibility(View.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        NewUserActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                buttonFLName.setVisibility(View.GONE);
                                buttonOTPVerify.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                buttonFLName.setVisibility(View.GONE);
                                buttonOTPVerify.setVisibility(View.VISIBLE);
                                Toast.makeText(NewUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId =newVerificationId;

                                buttonFLName.setVisibility(View.GONE);
                                buttonOTPVerify.setVisibility(View.VISIBLE);
                                Toast.makeText(NewUserActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
        });
        buttonOTPVerify.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   final String fullName = inputFname.getText().toString();
                                                   final String username = inputnewUsername.getText().toString().toLowerCase();
                                                   final String phoneNumber = "+91"+inputMobile.getText().toString();
                                                   final String password = inputnewPassword.getText().toString();
                                                   if (male.isChecked()) {
                                                       gender = "Male";

                                                   }
                                                   if (female.isChecked()) {
                                                       gender = "Female";

                                                   }
                                                   if (other.isChecked()) {
                                                       gender = "Other's";
                                                   }

                                                   if (inputFname.getText().toString().trim().isEmpty()
                                                           || inputMobile.getText().toString().trim().isEmpty()
                                                           || inputnewUsername.toString().trim().isEmpty()
                                                           || inputnewPassword.toString().trim().isEmpty()
                                                   ) {

                                                       Toast.makeText(NewUserActivity.this, "Please Details Correctly", Toast.LENGTH_SHORT).show();
                                                   }
                                                   if (inputnewPassword.length() < 6
                                                           || inputnewPassword.length() > 12) {
                                                       inputnewPassword.setError("Password should be more than 12 and less than 6 Characters");
                                                   }

                                                   if (inputCode1.getText().toString().trim().isEmpty()
                                                           || inputCode2.getText().toString().trim().isEmpty()
                                                           || inputCode3.getText().toString().trim().isEmpty()
                                                           || inputCode4.getText().toString().trim().isEmpty()
                                                           || inputCode5.getText().toString().trim().isEmpty()
                                                           || inputCode6.getText().toString().trim().isEmpty()) {
                                                       Toast.makeText(NewUserActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                                                       return;
                                                   }
                                                   String code = inputCode1.getText().toString() +
                                                           inputCode2.getText().toString() +
                                                           inputCode3.getText().toString() +
                                                           inputCode4.getText().toString() +
                                                           inputCode5.getText().toString() +
                                                           inputCode6.getText().toString();
                                                   if (verificationId != null) {

                                                       buttonOTPVerify.setVisibility(View.INVISIBLE);
                                                       final PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                                               verificationId,
                                                               code
                                                       );

                                                       FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<AuthResult> task) {

                                                                       buttonFLName.setVisibility(View.GONE);
                                                                       buttonOTPVerify.setVisibility(View.VISIBLE);
                                                                       if (task.isSuccessful()) {
                                                                           Users information = new Users(

                                                                                   fullName,
                                                                                   username,
                                                                                   phoneNumber,
                                                                                   gender,
                                                                                   password
                                                                           );
                                                                           FirebaseDatabase.getInstance().getReference("Users")
                                                                                   .child(phoneNumber)
                                                                                   .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                               @Override
                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                   Toast.makeText(NewUserActivity.this, "User's have been Registered", Toast.LENGTH_SHORT).show();
                                                                                   Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                                                                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                   startActivity(intent);
                                                                               }
                                                                           });
                                                                       } else {
                                                                           Toast.makeText(NewUserActivity.this, "The verification code entered was invalid ", Toast.LENGTH_SHORT).show();
                                                                       }
                                                                   }
                                                               });


                                                       FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<AuthResult> task) {

                                                                       buttonFLName.setVisibility(View.GONE);
                                                                       buttonOTPVerify.setVisibility(View.VISIBLE);
                                                                       if (task.isSuccessful()) {
                                                                           Users information = new Users(

                                                                                   fullName,
                                                                                   username,
                                                                                   phoneNumber,
                                                                                   gender,
                                                                                   password
                                                                           );
                                                                           FirebaseDatabase.getInstance().getReference("Users")
                                                                                   .child(phoneNumber)
                                                                                   .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                               @Override
                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                   Toast.makeText(NewUserActivity.this, "User's have been Registered", Toast.LENGTH_SHORT).show();
                                                                                   Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                                                                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                   startActivity(intent);
                                                                               }
                                                                           });
                                                                       }
                                                                           Toast.makeText(NewUserActivity.this, "The verification code entered was invalid ", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });
                                                   }
                                               }


                                           });
        findViewById(R.id.textresendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        NewUserActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                buttonFLName.setVisibility(View.GONE);
                                buttonOTPVerify.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                buttonFLName.setVisibility(View.GONE);
                                buttonOTPVerify.setVisibility(View.VISIBLE);
                                Toast.makeText(NewUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId =newVerificationId;
                                buttonFLName.setVisibility(View.GONE);
                                buttonOTPVerify.setVisibility(View.VISIBLE);
                                Toast.makeText(NewUserActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }
    private void setupOTPInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}