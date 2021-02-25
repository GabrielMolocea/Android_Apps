package com.gabriel.slimegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // boolean to see if game is muted or not
    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting application in full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // When play button is press game will start
        findViewById(R.id.playButton).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this,  GameActivity.class)));

        TextView highScoreText = findViewById(R.id.highScoreText);

        // Adding highest score to front of the screen
        final SharedPreferences preferences = getSharedPreferences("game", MODE_PRIVATE);
        highScoreText.setText("High Score: " + preferences.getInt("highscore", 0));

        // Setting volume to be on by default
        isMute = preferences.getBoolean("isMute", false);

        // Changing on demand when icon is press
        // from game game muted or not to reverse
        final ImageView volumeControl = findViewById(R.id.volumeControl);

        if (isMute) {
            volumeControl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        } else {
            volumeControl.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isMute", isMute);
        editor.apply();
    }
}