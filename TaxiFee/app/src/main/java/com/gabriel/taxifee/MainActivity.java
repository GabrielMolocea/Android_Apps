package com.gabriel.taxifee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity is responsible for initializing of objects
 */
public class MainActivity extends AppCompatActivity {

    // Initialization of objects
    private Button buttonDriver, buttonCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDriver = (Button) findViewById(R.id.driver);
        buttonCustomer = (Button) findViewById(R.id.customer);

        buttonDriver.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        });
    }
}