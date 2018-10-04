package hereforthebeer.sokoban;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import hereforthebeer.sokoban.Game.GameActivity;

public class quick_play extends AppCompatActivity {
    Button easyButton;
    Button mediumButton;
    Button hardButton;
    Button soloButton;
    Button challengeButton;
    int defaultButtonbackground;
    char difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_play);
        easyButton = (Button) findViewById(R.id.button_easy);
        mediumButton = (Button) findViewById(R.id.button_medium);
        hardButton = (Button) findViewById(R.id.button_hard);
        soloButton = (Button) findViewById(R.id.button_solo);
        challengeButton = (Button) findViewById(R.id.button_challenge);

        defaultButtonbackground = easyButton.getDrawingCacheBackgroundColor();
        easyButton.setBackgroundColor(Color.GRAY);
        mediumButton.setBackgroundColor(Color.LTGRAY);
        hardButton.setBackgroundColor(Color.GRAY);

        difficulty = 'M';
    }

    public void onBackTap(View v) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    public void onEasyTap(View v) {
        easyButton.setBackgroundColor(Color.LTGRAY);
        mediumButton.setBackgroundColor(Color.GRAY);
        hardButton.setBackgroundColor(Color.GRAY);
        difficulty = 'E';
    }

    public void onMediumTap(View v) {
        easyButton.setBackgroundColor(Color.GRAY);
        mediumButton.setBackgroundColor(Color.LTGRAY);
        hardButton.setBackgroundColor(Color.GRAY);
        difficulty = 'M';
    }

    public void onHardTap(View v) {
        easyButton.setBackgroundColor(Color.GRAY);
        mediumButton.setBackgroundColor(Color.GRAY);
        hardButton.setBackgroundColor(Color.LTGRAY);
        difficulty = 'H';
    }

    public void onSoloTap(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("LOAD", "random");
        intent.putExtra("DIFFICULTY", difficulty );
        intent.putExtra("AI", false);
        startActivity(new Intent(intent));
    }

    public void onChallengeTap(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("LOAD", "random");
        intent.putExtra("DIFFICULTY", difficulty );
        intent.putExtra("AI", true);
        startActivity(new Intent(intent));
    }


}
