package hereforthebeer.sokoban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class level_Selection_Packs extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level__selection__packs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new customSwipeAdapter(this));
    }

    public void onOneTap(View v){
        startActivity(new Intent(this, level_Selection_Grid.class));
    }

    public void onTwoTap(View v){
        startActivity(new Intent(this, level_Selection_Grid2.class));
    }

    public void onThreeTap(View v){
        startActivity(new Intent(this, level_Selection_Grid3.class));
    }

    public void onFourTap(View v){
        startActivity(new Intent(this, level_Selection_Grid4.class));
    }

    public void onBackTap(View v){
        startActivity(new Intent(this, MainMenuActivity.class));
    }



}
