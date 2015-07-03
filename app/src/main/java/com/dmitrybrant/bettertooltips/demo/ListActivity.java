package com.dmitrybrant.bettertooltips.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmitrybrant.bettertooltips.OnboardingTracker;
import com.dmitrybrant.bettertooltips.ToolTip;
import com.dmitrybrant.bettertooltips.ToolTipRelativeLayout;
import com.dmitrybrant.bettertooltips.ToolTipView;


public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String[] strings = new String[]{
                "This",
                "Is",
                "A",
                "List",
                "To",
                "Demonstrate",
                "Tooltips",
                "In",
                "A",
                "ListView"
        };

        final ToolTipRelativeLayout toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.list_tooltipframelayout);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list_text, strings);

        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        final OnboardingTracker tracker = new OnboardingTracker(this, getString(R.string.tracker_list))
                .withFirstShow(0)
                .withLastShow(3)
                .build();

        final ToolTip toolTip = new ToolTip()
                .withText("This is the Droid you are looking for!")
                .withColor(getResources().getColor(R.color.holo_blue))
                .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                .withShadow();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createToolTipView(toolTipRelativeLayout, listView, tracker, toolTip);
            }
        }, 1300);
    }

    private void createToolTipView(ToolTipRelativeLayout toolTipRelativeLayout, ListView listView, OnboardingTracker tracker, ToolTip toolTip) {
        View firstChild = listView.getChildAt(2);
        View image = firstChild.findViewById(R.id.list_image);
        ToolTipView toolTipView = toolTipRelativeLayout.showToolTipWithTracker(toolTip, image, tracker);
        toolTipRelativeLayout.addToolTipView(toolTipView);
    }


}
