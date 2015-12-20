package com.dengit.phrippple.utils;

import android.content.res.Resources;
import android.os.Build;
import android.text.Html;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;

/**
 * Created by dengit on 15/12/14.
 */
public class Util {
    public static CharSequence textToHtml(String description) {
        return description == null ? "" : Html.fromHtml(description);
    }

    @SuppressWarnings("deprecation")
    public static int getColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//level 23
            return APP.getInstance().getResources().getColor(colorResId, APP.getInstance().getTheme());
        } else {
            return APP.getInstance().getResources().getColor(colorResId);
        }
    }

    public static int dp2px(float dp) {
        float destiny = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * destiny);
    }
}
