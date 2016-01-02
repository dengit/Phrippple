/**
 * Copyright 2014 Rahul Parsani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dengit.phrippple.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;

import com.dengit.phrippple.APP;

public class Utils {

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

    public static Bitmap getCircleBitmap(Bitmap src) {
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        final RectF rectF = new RectF(0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        float r = Math.min(rectF.centerX(), rectF.centerY());
        canvas.drawCircle(rectF.centerX(), rectF.centerY(),
                r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        return bitmap;
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListenerCompat(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (hasJellyBean()) {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    @SuppressWarnings("deprecation")
    public static void setBackgroundCompat(View v, Drawable drawable) {
        if (hasJellyBean()) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Uses static final constants to detect if the device's platform version is Lollipop or
     * later.
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Lollipop or
     * later.
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
