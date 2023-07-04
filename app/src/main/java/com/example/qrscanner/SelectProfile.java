package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class SelectProfile extends AppCompatActivity {

    LinearLayout shipper;
    LinearLayout transporter;

    CheckBox checkBox1;
    CheckBox checkBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        shipper = findViewById(R.id.shipper);
        transporter = findViewById(R.id.transporter);
        checkBox1 = findViewById(R.id.check1);
        checkBox2 = findViewById(R.id.check2);


        shipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                } else {
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);
                }
            }
        });

        transporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox2.isChecked()) {
                    checkBox2.setChecked(false);
                } else {
                    checkBox2.setChecked(true);
                    checkBox1.setChecked(false);
                }
            }
        });
    }
}