package com.deepak.myapplication;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


public class CustomBindingAdapter {

    @BindingAdapter("strikeThrough")
    public static void setStrikeThrough(TextView textView, boolean isCompleted) {
        if (isCompleted) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setTextSize(14);
            textView.setAlpha(0.7F);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            textView.setTextSize(16);
            textView.setAlpha(1);
        }
    }
}
