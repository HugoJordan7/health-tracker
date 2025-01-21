package com.example.healthtracker.feature.main.view;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class MainItem {

    private int id;
    @StringRes private int text;
    @DrawableRes private int icon;

    public MainItem(int id, int text, int icon) {
        this.id = id;
        this.text = text;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
