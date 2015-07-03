package com.dmitrybrant.bettertooltips.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dmitrybrant.bettertooltips.OnboardingTracker;
import com.dmitrybrant.bettertooltips.ToolTip;
import com.dmitrybrant.bettertooltips.ToolTipRelativeLayout;
import com.dmitrybrant.bettertooltips.ToolTipSequence;
import com.dmitrybrant.bettertooltips.ToolTipView;

public class TrackerActivity extends Activity implements View.OnClickListener {

    private ToolTipRelativeLayout toolTipFrameLayout;
    private ToolTipSequence toolTipSequence;
    private ToolTipView delayToolTipView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracker_activity);

        toolTipFrameLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipframelayout);

        OnboardingTracker sequenceTracker = new OnboardingTracker(this, "sequence").build();
        toolTipSequence = new ToolTipSequence(sequenceTracker, toolTipFrameLayout);

        addDelayedToolTipView();
        addSequenceOneToolTipView();
        addTrackerToolTipView();
        addSequenceTwoToolTipView();
        addSequenceThreeToolTipView();
    }

    private void addDelayedToolTipView() {
        ToolTip toolTip = ToolTipFactory.getToolTipWithText(this, "You don't seem to have \n found this button yet!");
        toolTip.withDelay(5000);

        OnboardingTracker tracker = new OnboardingTracker(this, getString(R.string.tracker_delayed_button))
                .build();

        final View delayedButton = findViewById(R.id.activity_main_redtv);
        delayedButton.setOnClickListener(this);
        delayToolTipView = toolTipFrameLayout.showToolTipWithTracker(toolTip, delayedButton, tracker);
        toolTipFrameLayout.addToolTipView(delayToolTipView);
    }

    private void addSequenceOneToolTipView() {
        final ToolTip toolTip = ToolTipFactory.getToolTipWithText(this, "This is the first");

        ToolTipView sequenceOneToolTipView = toolTipFrameLayout.showToolTipForView(toolTip, findViewById(R.id.activity_main_greentv));
        toolTipSequence.addToSequence(sequenceOneToolTipView);
    }

    private void addSequenceTwoToolTipView() {
        final ToolTip toolTip = ToolTipFactory.getToolTipWithText(this, "In a sequence");

        ToolTipView sequenceTwoToolTipView = toolTipFrameLayout.showToolTipForView(toolTip, findViewById(R.id.activity_main_bluetv));
        toolTipSequence.addToSequence(sequenceTwoToolTipView);
    }

    private void addSequenceThreeToolTipView() {
        final ToolTip toolTip = ToolTipFactory.getToolTipWithText(this, "That is now done");

        final View refreshButton = findViewById(R.id.activity_main_purpletv);
        refreshButton.setOnClickListener(this);
        ToolTipView sequenceThreeToolTipView = toolTipFrameLayout.showToolTipForView(toolTip, findViewById(R.id.activity_main_purpletv));
        toolTipSequence.addToSequence(sequenceThreeToolTipView);
    }

    private void addTrackerToolTipView() {
        OnboardingTracker tracker = new OnboardingTracker(this, getString(R.string.tracker_first_button))
                .withFirstShow(1)
                .withLastShow(3)
                .afterInitialOnboarding(true)
                .build();

        final ToolTip toolTip = ToolTipFactory.getToolTipWithText(this, "You're already a pro with the top left button. Try this one. Shows on 2nd to 4th views after top left was pressed");

        ToolTipView trackerToolTipView = toolTipFrameLayout.showToolTipWithTracker(toolTip, findViewById(R.id.activity_main_orangetv), tracker);
        toolTipFrameLayout.addToolTipView(trackerToolTipView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.activity_main_redtv) {
            if (delayToolTipView != null) {
                final OnboardingTracker tracker = delayToolTipView.getTracker();
                tracker.setDismissedPref(true);
                tracker.setInitialOnboardingComplete(true);
            }
        } else if ((id == R.id.activity_main_purpletv)) {
            if (delayToolTipView != null) {
                //Restart delayed ToolTip
                final OnboardingTracker tracker = delayToolTipView.getTracker();
                tracker.setDismissedPref(false);
            }
        }
    }
}

