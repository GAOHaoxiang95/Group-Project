package hereforthebeer.sokoban;

/**
 * Created by Ibz on 22/03/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public enum ModelObject {

    PACK1(R.string.packone, R.layout.pack_one),
    PACK2(R.string.packtwo, R.layout.pack_two),
    PACK3(R.string.packthree, R.layout.pack_three),
    PACK4(R.string.packfour, R.layout.pack_four);



    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }


    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}

