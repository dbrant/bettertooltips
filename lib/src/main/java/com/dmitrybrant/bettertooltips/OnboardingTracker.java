package com.dmitrybrant.bettertooltips;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ryan on 10/01/2015.
 */
public class OnboardingTracker {

    private final static String DEFAULT_PREFS = "DEFAULT_PREFS";
    private final static String DISMISSED = "_DISMISSED";
    private final static String INITIAL_ONBOARDING = "INITIAL_ONBOARDING";

    private boolean show = false;
    private boolean initial = false;
    private boolean isAfterInitial = false;
    private Context context;
    private String id;
    private int lastShow = -1;
    private int firstShow = 0;

    /**
     * Creates a new OnboardingTracker with an id used to save the history in shared preferences.
     */
    public OnboardingTracker (Context context, String id) {
        this.context = context;
        this.id = id;
    }

    /**
     * Set the number of builds of this Tracker before the Tracker will set to show (zero-indexed).
     *
     * @return this OnboardingTracker to build upon.
     */
    public OnboardingTracker withFirstShow(int firstShow) {
        this.firstShow = firstShow;
        return this;
    }

    /**
     * Set the number of builds of this Tracker before the Tracker will stop showing (zero-indexed).
     *
     * @return this OnboardingTracker to build upon.
     */
    public OnboardingTracker withLastShow(int lastShow) {
        this.lastShow = lastShow;
        return this;
    }

    /**
     * Sets up the tracker to only begin incrementing after the initial onboarding has been completed,
     * this is signalled by any tracker calling setInitialOnboardingComplete(true).
     *
     * @return this OnboardingTracker to build upon.
     */
    public OnboardingTracker afterInitialOnboarding(boolean isAfterInitial) {
        this.isAfterInitial = isAfterInitial;
        return this;
    }

    /**
     * The Tracker will calculate whether to show when built based on its history and the first and
     * last show settings.
     *
     * @return this OnboardingTracker to build upon.
     */
    public OnboardingTracker build() {
        if (!isAfterInitial || getInitialOnboardingComplete()) {
            int currentCount = getTrackerCounter();
            boolean dismissed = getDismissedPref();
            show = shouldViewShow(currentCount, dismissed);
            incrementTrackerCounter(currentCount);
        }
        return this;
    }

    private boolean shouldViewShow(int currentCount, boolean dismissed) {
        if (dismissed) {
            return false;
        } else {
            return ((firstShow <= currentCount) && (lastShow == -1 || (currentCount <= lastShow)));
        }
    }

    private int getTrackerCounter() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(id, 0);
    }

    private void incrementTrackerCounter(int currentCount) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(id, currentCount + 1).commit();
    }

    public boolean shouldShow() {
        return show;
    }

    public void setDismissedPref(boolean dismissed) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(id + DISMISSED, dismissed).commit();
        show = false;
    }

    public boolean getDismissedPref() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(id + DISMISSED, false);
    }

    public void setInitialOnboardingComplete(boolean complete) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(INITIAL_ONBOARDING, complete).commit();
    }

    public boolean getInitialOnboardingComplete() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(INITIAL_ONBOARDING, false);
    }
}
