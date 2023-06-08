package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button next;
    EditText phone;

    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check login state
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(MainActivity.this,ScanQR.class));
            finish();
        } else {

            next = findViewById(R.id.authbtn);
            phone = findViewById(R.id.phoneno);
            ccp = (CountryCodePicker) findViewById(R.id.ccp);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(phone.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    } else if (phone.getText().toString().length() != 10 || containsNonDigit(phone.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        String phone = ccp.getSelectedCountryCodeWithPlus().replace(" ", "") + MainActivity.this.phone.getText().toString();
                        Intent intent = new Intent(MainActivity.this, VerifyOTP.class);
                        intent.putExtra("mobile", phone);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public static boolean containsNonDigit(String str) {

        // Convert the string to a char array.
        char[] chars = str.toCharArray();

        // Loop through the char array and check if any character is not a digit.
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return true;
            }
        }

        // If no character is not a digit, then the string only contains digits.
        return false;
    }
}