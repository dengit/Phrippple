package com.dengit.phrippple.adapter;

import android.content.Intent;
import android.view.View;

import com.dengit.phrippple.ui.base.transition.TransitionBaseActivity;
import com.dengit.phrippple.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by dengit on 15/12/29.
 */
public abstract class RecyclerViewTransitionBaseAdapter<T> extends RecyclerViewBaseAdapter<T> {

    public RecyclerViewTransitionBaseAdapter(List<T> items, View footer, TransitionBaseActivity<T> transitionActivity) {
        super(items, footer, transitionActivity);
    }

    protected void startDetailActivity(View view, Intent intent, int sharedElementResId) {
        final SimpleDraweeView image = (SimpleDraweeView) view.findViewById(sharedElementResId);
        if (Utils.hasLollipop()) {
            ((TransitionBaseActivity)mActivity).startActivityLollipop(image, intent, "photo_hero");
        } else {
            ((TransitionBaseActivity)mActivity).startActivityGingerBread(image, intent);
        }
    }

}
