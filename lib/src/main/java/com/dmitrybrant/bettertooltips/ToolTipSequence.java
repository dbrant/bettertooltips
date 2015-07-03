package com.dmitrybrant.bettertooltips;

import java.util.LinkedList;

/**
 * Created by Ryan John on 16/01/2015.
 */
public class ToolTipSequence implements ToolTipView.OnToolTipViewClickedListener {

    LinkedList<ToolTipView> sequence = new LinkedList<ToolTipView>();
    private OnboardingTracker tracker;
    private ToolTipRelativeLayout layout;

    public ToolTipSequence(OnboardingTracker tracker, ToolTipRelativeLayout layout) {
        this.tracker = tracker;
        this.layout = layout;
    }

    public void addToSequence(ToolTipView toolTipView) {
        if (sequence.isEmpty() && tracker.shouldShow()) {
            startToolTipView(toolTipView);
        }
        toolTipView.setOnToolTipViewClickedListener(this);
        sequence.add(toolTipView);
    }


    @Override
    public void onToolTipViewClicked(ToolTipView clickedView) {
        int index = sequence.indexOf(clickedView);
        int nextIndex = index + 1;
        if (index != -1 && nextIndex < sequence.size()) {
            ToolTipView nextView = sequence.get(nextIndex);
            startToolTipView(nextView);
        } else {
            tracker.setDismissedPref(true);
        }
        clickedView.remove();
    }

    private void startToolTipView(ToolTipView toolTipView) {
        if (toolTipView != null) {
            layout.addView(toolTipView);
        }
    }
}
