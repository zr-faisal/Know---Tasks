package com.know.know_tasks.util;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class CommonUtils {

    private static final String DATE_TIME_FORMAT = "dd MMM HH:mm";

    public static void showSnackBarMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBarMessage(View view, int messageResource) {
        Snackbar.make(view, messageResource, Snackbar.LENGTH_SHORT).show();
    }

    public static String getDateTimeString(long dateTime) {
        return new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
                .format(new Date(dateTime));
    }
}
