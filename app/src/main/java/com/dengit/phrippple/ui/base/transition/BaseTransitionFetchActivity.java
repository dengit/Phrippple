package com.dengit.phrippple.ui.base.transition;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dengit.phrippple.ui.base.FetchBaseActivity;
import com.dengit.phrippple.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by dengit on 15/12/25.
 */
//only transition beginning
public abstract class BaseTransitionFetchActivity<T> extends FetchBaseActivity<T> {

    protected void startDetailActivity(View view, Intent intent, int sharedElementResId) {
        final SimpleDraweeView image = (SimpleDraweeView) view.findViewById(sharedElementResId);
        if (Utils.hasLollipop()) {
            startActivityLollipop(image, intent, "photo_hero");
        } else {
            startActivityGingerBread(image, intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivityLollipop(View view, Intent intent, String sharedElementName) {
        ImageView hero = (ImageView) view;
        ((ViewGroup) hero.getParent()).setTransitionGroup(false);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this, hero, sharedElementName);
        startActivity(intent, options.toBundle());
    }

    public void startActivityGingerBread(View view, Intent intent) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        intent.
                putExtra("left", screenLocation[0]).
                putExtra("top", screenLocation[1]).
                putExtra("width", view.getWidth()).
                putExtra("height", view.getHeight());

        startActivity(intent);

        // Override transitions: we don't want the normal window animation in addition to our
        // custom one
        overridePendingTransition(0, 0);

        // The detail activity handles the enter and exit animations. Both animations involve a
        // ghost view animating into its final or initial position respectively. Since the detail
        // activity starts translucent, the clicked view needs to be invisible in order for the
        // animation to look correct.
        ViewPropertyAnimator.animate(view).alpha(0.0f);
    }
}
