package hereforthebeer.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hereforthebeer.sokoban.Game.GameActivity;
import hereforthebeer.sokoban.Game.GameThread;


public class level_Selection_Grid extends AppCompatActivity {

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level__selection__grid);
        //setColor();
    }


    public void createMapName(View v) {
        String mapName = "";
        long pass = 1;
        switch (v.getId()) {
            case R.id.map1:

                mapName = "map1";
                key = "1";
                break;
            case R.id.map2:

                mapName = "map2";
                key = "2";
                break;
            case R.id.map3:

                mapName = "map3";
                key = "3";
                break;
            case R.id.map4:

                mapName = "map4";
                key = "4";
                break;
            case R.id.map5:

                mapName = "map5";
                key = "5";
                break;
            case R.id.map6:

                mapName = "map6";
                key = "6";
                break;
            case R.id.map7:

                mapName = "map7";
                key = "7";
                break;
            case R.id.map8:

                mapName = "map8";
                key = "8";
                break;
            case R.id.map9:

                mapName = "map9";
                key = "9";
                break;
            case R.id.map10:

                mapName = "map10";
                key = "10";
                break;
            case R.id.map11:

                mapName = "map11";
                key = "11";
                break;
            case R.id.map12:

                mapName = "map12";
                key = "12";
                break;
            case R.id.map13:

                mapName = "map13";
                key = "13";
                break;
            case R.id.map14:

                mapName = "map14";
                key = "14";
                break;
            case R.id.map15:

                mapName = "map15";
                key = "15";
                break;
            case  R.id.boss:

                mapName = "bosslevel1";
                key = "16";
        }

        loadMap(mapName);

    }
    public void setColor(){

        Button btn = (Button) findViewById(R.id.map1);

        btn = (Button) findViewById(R.id.map1);
        btn.setBackgroundColor(0x33ad3210);
        btn = (Button) findViewById(R.id.map2);
        btn.setBackgroundColor(0x33ad3210);
        btn = (Button) findViewById(R.id.map3);
        btn.setBackgroundColor(0x33ad3210);
    }


    public void loadMap(String map) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("LOAD", map);
        if(key == "16")
            intent.putExtra("AI", true);
        else
            intent.putExtra("AI", false);
        startActivity(new Intent(intent));
    }

    public void onBackTap(View v) {
        //Links to the "Level_Selection_Pack"
        startActivity(new Intent(this, level_Selection_Packs.class));

    }
    public void onbtnLevel1Tap(View v) {
    }
}
