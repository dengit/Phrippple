package com.dengit.phrippple.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.text.Html;

import com.dengit.phrippple.APP;

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
}
