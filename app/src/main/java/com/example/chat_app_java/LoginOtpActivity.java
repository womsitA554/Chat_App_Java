package com.example.chat_app_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chat_app_java.utils.AndroidUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOtpActivity extends AppCompatActivity {
    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    EditText etOtp;
    TextView tvResend;
    Button btnNext;
    ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_otp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        etOtp = findViewById(R.id.login_otp);
        btnNext = findViewById(R.id.btnNext);
        tvResend = findViewById(R.id.resendOtp);
        progressBar = findViewById(R.id.login_progress_bar);

        // Get phone number from intent
        phoneNumber = getIntent().getStringExtra("phone");
        Toast.makeText(LoginOtpActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();

        // Send OTP
        sendOtp(phoneNumber, false);

        // Set initial state of progress bar
        setInProgressBar(false);

        // Button click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = etOtp.getText().toString();
                if (!enteredOtp.isEmpty()) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enteredOtp);
                    signIn(credential);
                    setInProgressBar(true);
                } else {
                    AndroidUtils.ShowToast(getApplicationContext(), "Please enter the OTP");
                }
            }
        });

        // Resend OTP listener (uncomment if needed)
        // tvResend.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         sendOtp(phoneNumber, true);
        //     }
        // });
    }

    void sendOtp(String phoneNumber, boolean isResend) {
        setInProgressBar(true);
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                        setInProgressBar(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AndroidUtils.ShowToast(getApplicationContext(), "OTP verification failed: " + e.getMessage());
                        setInProgressBar(false);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationCode = s;
                        resendingToken = forceResendingToken;
                        AndroidUtils.ShowToast(getApplicationContext(), "OTP sent successfully");
                        setInProgressBar(false);
                    }
                });
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void setInProgressBar(boolean inProgressBar) {
        if (inProgressBar) {
            progressBar.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }
    }

    void signIn(PhoneAuthCredential phoneAuthCredential) {
        setInProgressBar(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgressBar(false);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginOtpActivity.this, LoginUserNameActivity.class);
                    intent.putExtra("phone", phoneNumber);
                    startActivity(intent);
                } else {
                    AndroidUtils.ShowToast(getApplicationContext(), "OTP verification failed: " + task.getException().getMessage());
                }
            }
        });
    }

    void startResendTimer() {
        tvResend.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    timeoutSeconds--;
                    tvResend.setText("Resend OTP in " + timeoutSeconds + " seconds");
                    if (timeoutSeconds <= 0) {
                        timeoutSeconds = 60L;
                        timer.cancel();
                        tvResend.setEnabled(true);
                    }
                });
            }
        }, 0, 1000);
    }
}
