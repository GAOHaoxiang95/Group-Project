package hereforthebeer.sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import hereforthebeer.sokoban.Game.GameActivity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.FromFileMapCreator;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator.RandomGame;
import hereforthebeer.sokoban.Level.Tile;
import hereforthebeer.sokoban.Model.SokobanLevel;
import hereforthebeer.sokoban.Util.Audio.AndroidAudio;
import hereforthebeer.sokoban.Util.Audio.NullAudio;
import hereforthebeer.sokoban.Util.Log.ConsoleLog;
import hereforthebeer.sokoban.Util.ServiceLocator;


public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.testsound1);
        mediaPlayer.start();



        //Init service locator
        ServiceLocator.setLog(new ConsoleLog());
        ServiceLocator.setAudio(new NullAudio());
        ServiceLocator.setFileStoragePath(getExternalFilesDir(null).getAbsolutePath());

        //Add level drawables
        ServiceLocator.addDrawable("wall", ResourcesCompat.getDrawable(getResources(), R.drawable.wall2, null));
        ServiceLocator.addDrawable("goal", ResourcesCompat.getDrawable(getResources(), R.drawable.goal2, null));
        ServiceLocator.addDrawable("floor", ResourcesCompat.getDrawable(getResources(), R.drawable.floor2, null));

        //Add player drawables
        ServiceLocator.addDrawable("player", ResourcesCompat.getDrawable(getResources(), R.drawable.sokoban2, null));
        ServiceLocator.addDrawable("crate_player", ResourcesCompat.getDrawable(getResources(), R.drawable.cratetrans, null));

        //Add ai drawables
        ServiceLocator.addDrawable("ai", ResourcesCompat.getDrawable(getResources(), R.drawable.sokoban, null), 80);
        ServiceLocator.addDrawable("crate_ai", ResourcesCompat.getDrawable(getResources(), R.drawable.cratetrans2, null), 80);


        //Define tile flywheel
        Level.WALL_TILE = new Tile(true, false, ServiceLocator.getDrawable("wall"));
        Level.FLOOR_TILE = new Tile(false, false, ServiceLocator.getDrawable("floor"));
        Level.GOAL_TILE = new Tile(false, true, ServiceLocator.getDrawable("goal"));
    }

    public void onNewGameTap(View v) {
        startActivity(new Intent(this, level_Selection_Packs.class));
       // RandomGame p = new RandomGame('M');
       // p.printMap2();

    }

    public void onQuickPlayTap(View v) {
        startActivity(new Intent(this, quick_play.class));
    }

    public void onCustomLevelTap(View v) {

        createToast("Creating custom level of diff: " );
    }

    private void createToast(String str) {
        Toast myToast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        myToast.show();
    }

    public void onHowToPlay(View v){
        startActivity(new Intent(this, Pop.class));
    }



}

