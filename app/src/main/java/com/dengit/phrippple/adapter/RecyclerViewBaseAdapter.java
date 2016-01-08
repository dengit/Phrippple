package com.dengit.phrippple.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengit.phrippple.APP;

import java.util.List;

/**
 * Created by dengit on 15/12/29.
 */
public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    protected List<T> mItems;
    protected Activity mActivity;

    public RecyclerViewBaseAdapter(List<T> items, Activity activity) {
        mItems = items;
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(APP.getInstance()).inflate(getItemLayoutResId(), parent, false);
            return createViewHolderItem(view);
        } else if (viewType == TYPE_FOOTER) {
//            mFooter.setLayoutParams(parent.getLayoutParams());//todo why this do work
//            return new VHFooterBase(mFooter);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItemBase) {
            setupItems((VHItemBase) holder, position);
        } else if (holder instanceof VHFooterBase) {
            //cast holder to VHHeader and set data for header.
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size()/* == 0 ? 0 : mItems.size() + 1*/;
    }

    @Override
    public int getItemViewType(int position) {
//        if (isPositionFooter(position))
//            return TYPE_FOOTER;

        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == mItems.size();
    }

    protected Object getItem(int position) {
        return mItems.get(position);
    }

    public void setData(List<T> newItems) {
        mItems.clear();
        appendData(newItems);
    }

    public void appendData(List<T> newItems) {
        mItems.addAll(newItems);
        notifyDataSetChanged();
    }

    protected abstract int getItemLayoutResId();
    protected abstract RecyclerView.ViewHolder createViewHolderItem(View itemView);
    protected abstract void setupItems(VHItemBase holder, int position);

    static class VHItemBase extends RecyclerView.ViewHolder {
        public VHItemBase(View view) {
            super(view);
        }
    }

    static class VHFooterBase extends RecyclerView.ViewHolder {
        public VHFooterBase(View itemView) {
            super(itemView);
        }
    }

}
