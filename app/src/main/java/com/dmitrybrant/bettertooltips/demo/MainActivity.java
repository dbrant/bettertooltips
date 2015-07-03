package com.dmitrybrant.bettertooltips.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startToolTipActivity(View view) {
        Intent intent = new Intent(this, StandardToolTipActivity.class);
        startActivity(intent);
    }

    public void startTrackerActivity(View view) {
        Intent intent = new Intent(this, TrackerActivity.class);
        startActivity(intent);
    }

    public void startListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}
