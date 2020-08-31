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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final EditText inputFname = findViewById(R.id.inputFname);
        final EditText inputLname  = findViewById(R.id.inputLname);
        //final EditText inputnewEmail = findViewById(R.id.inputnewEmail;
        //final EditText inputnewPassword = findViewById(R.id.inputnewPassword;
        final Button buttonFLName = findViewById(R.id.buttonFLName);
        final ProgressBar progressBar =findViewById(R.id.pro1);

        buttonFLName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputFname.toString().trim().isEmpty()
                ||inputLname.toString().trim().isEmpty()
                //||inputnewEmail.toString().trim().isEmpty()
                //||inputnewPassword.toString().trim().isEmpty()
                ){

                    Toast.makeText(HomeActivity.this, "Please Details Correctly", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonFLName.setVisibility(View.INVISIBLE);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(HomeActivity.this,WelcomeActivity.class);
                    startActivity(intent);

                }else{
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(inputFname.toString().trim() + inputLname.toString().trim())
                            .build();
                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(HomeActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}