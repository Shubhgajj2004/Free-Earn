package com.papercanary.freeearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RedeemActivity extends AppCompatActivity {

    int thresholdCoin = 20;
    TextView curCoin;
    EditText mobNo;
    Button redeemBtn;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String COIN_COUNT_KEY = "coinCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        curCoin = findViewById(R.id.curRedeemCoin);
        redeemBtn = findViewById(R.id.redeemMobButton);
        mobNo = findViewById(R.id.userMobileNo);

        int coins = getIntent().getIntExtra("eCoins", 0);

        curCoin.setText(Integer.toString(coins));

        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("Users");

                if(coins < thresholdCoin){
                    Toast.makeText(RedeemActivity.this, "Below Threshold", Toast.LENGTH_SHORT).show();
                } else{
                  myRef.child(mobNo.getText().toString()).setValue(coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                          SharedPreferences.Editor editor = sharedPreferences.edit();
                          editor.putInt(COIN_COUNT_KEY, 0);
                          editor.apply();

                          curCoin.setText(Integer.toString(0));
                          Toast.makeText(RedeemActivity.this, "Success", Toast.LENGTH_SHORT).show();
                      }
                  });
                }

            }
        });


    }
}