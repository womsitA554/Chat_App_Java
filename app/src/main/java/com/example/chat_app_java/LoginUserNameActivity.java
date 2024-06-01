package com.example.chat_app_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chat_app_java.model.UserModel;
import com.example.chat_app_java.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

public class LoginUserNameActivity extends AppCompatActivity {
    EditText etUserName;
    Button btnNext;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_user_name);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize your views here
        etUserName = findViewById(R.id.login_userName);
        progressBar = findViewById(R.id.login_progress_bar);
        btnNext = findViewById(R.id.btnNext);

        // Retrieve the phone number from the intent
        phoneNumber = getIntent().getStringExtra("phone");

        // Now that views are initialized, you can call getUserName and setInProgressBar
        getUserName();
        setInProgressBar(false);

        // Set up the click listener for the button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUsername();
            }
        });
    }


    void setUsername() {
        String username = etUserName.getText().toString();
        if (username.isEmpty() || username.length() < 3) {
            etUserName.setError("Username length should be at least 3 chars");
            return;
        }
        setInProgressBar(true);
        if (userModel!=null){
            userModel.setUsername(username);
        }else {
            userModel = new UserModel(phoneNumber, username, Timestamp.now(), FirebaseUtil.currentUserId());
        }

        FirebaseUtil.currentUserDetail().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgressBar(false);
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginUserNameActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getUserName() {
        setInProgressBar(true);
        FirebaseUtil.currentUserDetail().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgressBar(false);
                if (task.isSuccessful()) {
                    userModel = task.getResult().toObject(UserModel.class);
                    if (userModel != null){

                    }
                } else {

                }
            }
        });
    }

    void setInProgressBar(boolean inProgressBar) {
        if (progressBar == null || btnNext == null) {
            Log.e("LoginUserNameActivity", "ProgressBar or Button is not initialized");
            return;
        }

        if (inProgressBar) {
            progressBar.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }
    }

}