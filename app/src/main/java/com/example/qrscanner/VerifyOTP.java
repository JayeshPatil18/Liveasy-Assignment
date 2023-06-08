package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    Button verify;
    TextView sentto;
    EditText editOTP;

    String otpid;
    FirebaseAuth mAuth;
    String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        phonenumber = getIntent().getStringExtra("mobile").toString();
        verify = findViewById(R.id.verifybtn);
        editOTP = findViewById(R.id.optcode);
        sentto = findViewById(R.id.sentto);
        mAuth=FirebaseAuth.getInstance();

        sentto.setText(phonenumber);

        initiateotp();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editOTP.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                else if(editOTP.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,editOTP.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void initiateotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(VerifyOTP.this,ScanQR.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"Sign In Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}