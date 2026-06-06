package com.example.healthtracker.common.util;

import android.content.Context;
import android.widget.TextView;

import com.example.healthtracker.R;

public class TextViewManager {

    public static void checkIfTextViewIsEmpty(Context context, TextView text) {
        boolean textIsEmpty = (text.getText() == null || text.getText().toString().isBlank());
        if (textIsEmpty) text.setError(context.getString(R.string.empty_field));
    }

}
