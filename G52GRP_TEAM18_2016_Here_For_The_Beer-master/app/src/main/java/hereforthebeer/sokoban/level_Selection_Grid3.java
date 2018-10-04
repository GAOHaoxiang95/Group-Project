package hereforthebeer.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hereforthebeer.sokoban.Game.GameActivity;
import hereforthebeer.sokoban.Game.GameThread;


public class level_Selection_Grid3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level__selection__grid3);
        lockButton();
    }


    public void createMapName(View v) {
        String mapName = "";
        String key;
        long pass = 1;

        Button btn = (Button) findViewById(R.id.map1);
        btn.setEnabled(false);

        switch (v.getId()) {
            case R.id.map1:

                mapName = pass == 1 ? "map1" : null;
                key = "1";
                break;
            case R.id.map2:

                mapName = pass == 1 ? "map2" : null;
                key = "2";
                break;
            case R.id.map3:

                mapName = pass == 1 ? "map3" : null;
                key = "3";
                break;
            case R.id.map4:

                mapName = pass == 1 ? "map4" : null;
                key = "4";
                break;
            case R.id.map5:

                mapName = pass == 1 ? "map5" : null;
                key = "5";
                break;
            case R.id.map6:

                mapName = pass == 1 ? "map6" : null;
                key = "6";
                break;
            case R.id.map7:

                mapName = pass == 1 ? "map7" : null;
                key = "7";
                break;
            case R.id.map8:

                mapName = pass == 1 ? "map8" : null;
                key = "8";
                break;
            case R.id.map9:

                mapName = pass == 1 ? "map9" : null;
                key = "9";
                break;
            case R.id.map10:

                mapName = pass == 1 ? "map10" : null;
                key = "10";
                break;
            case R.id.map11:

                mapName = pass == 1 ? "map1" : null;
                key = "11";
                break;
            case R.id.map12:

                mapName = pass == 1 ? "map1" : null;
                key = "12";
                break;
            case R.id.map13:

                mapName = pass == 1 ? "map1" : null;
                key = "13";
                break;
            case R.id.map14:

                mapName = pass == 1 ? "map1" : null;
                key = "14";
                break;
            case R.id.map15:

                mapName = pass == 1 ? "map1" : null;
                key = "15";
                break;
            default:

                mapName = pass == 1 ? "map1" : null;
                key = "16";
        }

        loadMap(mapName);

    }

    public void lockButton(){

        Button btn = (Button) findViewById(R.id.map1);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map1);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map2);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map3);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map4);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map5);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map6);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map7);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map8);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map9);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map10);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map11);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map12);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map13);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map14);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.map15);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.boss);
        btn.setEnabled(false);
    }

    public void loadMap(String map) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("LOAD", map);
        startActivity(new Intent(intent));
    }

    public void onBackTap(View v) {
        //Links to the "Level_Selection_Pack"
        startActivity(new Intent(this, level_Selection_Packs.class));

    }
    public void onbtnLevel1Tap(View v) {
    }
}
