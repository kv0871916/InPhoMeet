package com.android.inphomeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p);

        final EditText inputMobile = findViewById(R.id.inputMobile);
        final Button buttonOTPSend = findViewById(R.id.buttonOTPSend);

        final ProgressBar progressBar =findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("PhoneNumber");
        buttonOTPSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendOTPActivity.this, "Enter Moblie Number", Toast.LENGTH_SHORT).show();
                    return;

                }
                final String number = "+91" +inputMobile.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                buttonOTPSend.setVisibility(View.INVISIBLE);
                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String no = snapshot.getValue().toString().trim();
                           if(no.equals(number)){
                               PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                       "+91" +inputMobile.getText().toString(),
                                       60,
                                       TimeUnit.SECONDS,
                                       SendOTPActivity.this,
                                       new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                           @Override
                                           public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                               progressBar.setVisibility(View.GONE);
                                               buttonOTPSend.setVisibility(View.VISIBLE);
                                           }

                                           @Override
                                           public void onVerificationFailed(@NonNull FirebaseException e) {
                                               progressBar.setVisibility(View.GONE);
                                               buttonOTPSend.setVisibility(View.VISIBLE);
                                               Toast.makeText(SendOTPActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                           }

                                           @Override
                                           public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                               progressBar.setVisibility(View.GONE);
                                               buttonOTPSend.setVisibility(View.VISIBLE);
                                               Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                               intent.putExtra("mobile", inputMobile.getText().toString());
                                               intent.putExtra("verificationId",verificationId);
                                               startActivity(intent);
                                           }
                                       }
                               );

                           }
                           else{
                               Toast.makeText(SendOTPActivity.this, "+91"+inputMobile+" not registered ", Toast.LENGTH_SHORT).show();
                               Intent intent= new Intent(SendOTPActivity.this,NewUserActivity.class);
                               startActivity(intent);
                           }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SendOTPActivity.this, "+91"+inputMobile+"not registered or "+ error.getMessage().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}