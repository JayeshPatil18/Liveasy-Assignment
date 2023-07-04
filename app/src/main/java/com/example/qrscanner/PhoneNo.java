package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class PhoneNo extends AppCompatActivity {
    Button next;
    EditText phone;

    CountryCodePicker ccp;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_no);

            next = findViewById(R.id.authbtn);
            phone = findViewById(R.id.phoneno);
            back = findViewById(R.id.back1);
            ccp = (CountryCodePicker) findViewById(R.id.ccp);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(phone.getText().toString())) {
                        Toast.makeText(PhoneNo.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    } else if (phone.getText().toString().length() != 10 || containsNonDigit(phone.getText().toString())) {
                        Toast.makeText(PhoneNo.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        String phone = ccp.getSelectedCountryCodeWithPlus().replace(" ", "") + PhoneNo.this.phone.getText().toString();
                        Intent intent = new Intent(PhoneNo.this, VerifyOTP.class);
                        intent.putExtra("mobile", phone);
                        startActivity(intent);
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

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