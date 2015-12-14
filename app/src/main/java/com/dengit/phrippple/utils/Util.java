package com.dengit.phrippple.utils;

import android.text.Html;

/**
 * Created by dengit on 15/12/14.
 */
public class Util {
    public static CharSequence textToHtml(String description) {
        return description == null ? "" : Html.fromHtml(description);
    }
}
