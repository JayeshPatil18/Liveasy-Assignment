package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    TextView resendotp;
    EditText editOTP1;
    EditText editOTP2;
    EditText editOTP3;
    EditText editOTP4;
    EditText editOTP5;
    EditText editOTP6;

    String otpid;
    FirebaseAuth mAuth;
    String phonenumber;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        phonenumber = getIntent().getStringExtra("mobile").toString();
        verify = findViewById(R.id.verifybtn);
        resendotp = findViewById(R.id.resendotp);
        sentto = findViewById(R.id.sentto);
        back = findViewById(R.id.back2);
        mAuth=FirebaseAuth.getInstance();

        sentto.setText(phonenumber);

        // Six digit EditTexts
        editOTP1 = findViewById(R.id.otpEditText1);
        editOTP2 = findViewById(R.id.otpEditText2);
        editOTP3 = findViewById(R.id.otpEditText3);
        editOTP4 = findViewById(R.id.otpEditText4);
        editOTP5 = findViewById(R.id.otpEditText5);
        editOTP6 = findViewById(R.id.otpEditText6);

        initiateotp();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String finalotp = editOTP1.getText().toString() + editOTP2.getText().toString() + editOTP3.getText().toString() + editOTP4.getText().toString() + editOTP5.getText().toString() + editOTP6.getText().toString();
                boolean isValid = true;
                if(editOTP1.getText().toString().isEmpty()) {
                    isValid = false;
                } else if(editOTP2.getText().toString().isEmpty()) {
                    isValid = false;
                } else if(editOTP3.getText().toString().isEmpty()) {
                    isValid = false;
                } else if(editOTP4.getText().toString().isEmpty()) {
                    isValid = false;
                } else if(editOTP5.getText().toString().isEmpty()) {
                    isValid = false;
                } else if(editOTP6.getText().toString().isEmpty()) {
                    isValid = false;
                }
                if(!isValid)
                    Toast.makeText(getApplicationContext(),"Fill all digits",Toast.LENGTH_LONG).show();
                else if(finalotp.length()!=6)
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,finalotp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateotp();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    editOTP2.requestFocus(); // Move focus to the next EditText
                }
            }
        });

        editOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    editOTP3.requestFocus(); // Move focus to the next EditText
                }
            }
        });

        editOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    editOTP4.requestFocus(); // Move focus to the next EditText
                }
            }
        });

        editOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    editOTP5.requestFocus(); // Move focus to the next EditText
                }
            }
        });

        editOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    editOTP6.requestFocus(); // Move focus to the next EditText
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
                            Intent intent = new Intent(VerifyOTP.this, SelectProfile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            // Save login state
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("phoneno", phonenumber);
                            editor.apply();

                        } else {
                            Toast.makeText(getApplicationContext(),"Invalid Code!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}