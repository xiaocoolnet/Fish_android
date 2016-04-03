package cn.xiaocool.fish.utils.weather;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtils {

    public static void show(Activity activity, int resId) {
        Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(Activity activity, CharSequence text) {
        Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(View view, CharSequence text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
