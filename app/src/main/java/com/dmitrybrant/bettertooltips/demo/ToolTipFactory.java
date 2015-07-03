package com.dmitrybrant.bettertooltips.demo;

import android.content.Context;

import com.dmitrybrant.bettertooltips.ToolTip;

/**
 * Created by Ryan on 21/01/2015.
 */
public class ToolTipFactory {

    public static ToolTip getToolTipWithText(Context context, String text){
        return new ToolTip()
                .withText(text)
                .withColor(context.getResources().getColor(R.color.holo_blue))
                .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                .withShadow();
    }
}
