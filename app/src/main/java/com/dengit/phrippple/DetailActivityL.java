package com.dengit.phrippple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;

import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;

import timber.log.Timber;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class DetailActivityL extends AbstractDetailActivity {

    @Override
    public void postCreate() {
        applySystemWindowsBottomInset();
    }

    @Override
    public void colorButton(int id, int bgColor, int tintColor) {
        View buttonView = findViewById(id);

        RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
        GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
        rippleBackground.setColor(bgColor);

        ripple.setColor(ColorStateList.valueOf(tintColor));
    }

    @Override
    public void setupEnterAnimation() {
        getWindow().getEnterTransition().addListener(new TransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                //                ImageView hero = (ImageView) findViewById(R.id.photo);
                Integer colorFrom = Util.getColor(R.color.photo_tint);
                ValueAnimator color = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, 0);
                color.addUpdateListener(new TintListener(hero));
                color.start();

                //                findViewById(R.id.info_button).animate().alpha(1.0f);
                //                findViewById(R.id.star_button).animate().alpha(1.0f);
                //                mTargetImage.setBackground(hero.getDrawable());
//                hero.setVisibility(View.INVISIBLE);
                ((ViewGroup)hero.getParent()).removeView(hero);
                mTargetImage.setVisibility(View.VISIBLE);
                setTargetImage();
                Timber.d("***********setupEnterAnimation onTransitionEnd");
                getWindow().getEnterTransition().removeListener(this);
            }
        });
    }

    @Override
    public void setupExitAnimation() {
        Integer colorTo = Util.getColor(R.color.photo_tint);
        ValueAnimator color = ValueAnimator.ofObject(new ArgbEvaluator(), 0, colorTo);
        color.addUpdateListener(new TintListener(hero));
        color.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finishAfterTransition();
            }
        });
        color.start();

        //        findViewById(R.id.info_button).animate().alpha(0.0f);
        //        findViewById(R.id.star_button).animate().alpha(0.0f);

        //        mTargetImage.setBackground(hero.getDrawable());
        mTargetImage.setVisibility(View.GONE);
        ((ViewGroup) mTargetImage.getParent()).addView(hero);
//        hero.setVisibility(View.VISIBLE);
    }

    public abstract boolean isTargetAnimate();

    public abstract String getTargetImageUrl();

    private void applySystemWindowsBottomInset() {
        container.setFitsSystemWindows(true);
        container.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                if (metrics.widthPixels < metrics.heightPixels) {
                    view.setPadding(0, 0, 0, windowInsets.getSystemWindowInsetBottom());
                } else {
                    view.setPadding(0, 0, windowInsets.getSystemWindowInsetRight(), 0);
                }
                return windowInsets.consumeSystemWindowInsets();
            }
        });
    }

    private void setTargetImage() {
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(getResources())
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        String url = getTargetImageUrl();
        Timber.d("**url:%s", url);

        if (isTargetAnimate()) {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(url))
                    .setAutoPlayAnimations(true)
                    .build();
            mTargetImage.setController(controller);
            //                        mTargetImage.setHierarchy(gdh); // todo gif can not start as uncomment this line
        } else {
            mTargetImage.setImageURI(Uri.parse(url));
            //                        mTargetImage.setHierarchy(gdh);
        }

    }


    private static class TintListener implements ValueAnimator.AnimatorUpdateListener {
        private final ImageView mHero;

        TintListener(ImageView hero) {
            mHero = hero;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            mHero.getDrawable().setTint((Integer) valueAnimator.getAnimatedValue());
        }
    }

}