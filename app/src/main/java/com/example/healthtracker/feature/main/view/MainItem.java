package com.example.healthtracker.feature.main.view;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.common.util.OnClickListener;

public class MainItem {

    @StringRes private int text;
    @DrawableRes private int icon;

    private OnClickListener onClickListener;

    public MainItem(int text, int icon, OnClickListener onClickListener) {
        this.text = text;
        this.icon = icon;
        this.onClickListener = onClickListener;
    }

    public void onClickListener() {
        onClickListener.run();
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
