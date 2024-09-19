package com.papercanary.freeearn;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.papercanary.freeearn.SpeedXGame.SpeedXActivity;

public class MainActivity extends AppCompatActivity {

    int userCoins = 0;
    private Button adButton, redeemBtn, speedXBtn;
    private TextView eCoins;
    private final String TAG = "MainActivity";
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String COIN_COUNT_KEY = "coinCount";
    SharedPreferences sharedPreferences;

    @Override
    protected void onResume() {
        super.onResume();
        initializeUserCoins();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adButton = findViewById(R.id.adClickButton);
        eCoins = findViewById(R.id.earnedCoins);
        redeemBtn = findViewById(R.id.redeemButton);
        speedXBtn = findViewById(R.id.speedXButton);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        initializeUserCoins();


        // Set up the adButton click listener
        adButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        speedXBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpeedXActivity.class);
                startActivity(intent);
            }
        });

        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RedeemActivity.class);
                intent.putExtra("eCoins", userCoins);
                startActivity(intent);
            }
        });
    }

    private void initializeUserCoins(){
        userCoins = loadCoins();
        eCoins.setText(Integer.toString(userCoins));
    }

    private int loadCoins(){
        return sharedPreferences.getInt(COIN_COUNT_KEY, 0);
    }

    private void saveCoins(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COIN_COUNT_KEY, userCoins);
        editor.apply();

        eCoins.setText(Integer.toString(userCoins));
    }

}
