package com.know.know_tasks.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class CommonUtils {

    public static void showSnackBarMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBarMessage(View view, int messageResource) {
        Snackbar.make(view, messageResource, Snackbar.LENGTH_SHORT).show();
    }

}
