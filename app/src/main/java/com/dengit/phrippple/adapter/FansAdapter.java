package com.dengit.phrippple.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.data.Fan;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FansAdapter extends BaseAdapter {

    private List<Fan> mFans;
    public FansAdapter(List<Fan> fans) {
        mFans = fans;
    }

    @Override
    public int getCount() {
        return mFans.size();
    }

    @Override
    public Object getItem(int position) {
        return mFans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.fan_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpFanItem(holder, position);
        return convertView;
    }

    private void setUpFanItem(ViewHolder holder, int position) {
        Fan fan = (Fan) getItem(position);
        holder.fanPortrait.setImageURI(Uri.parse(fan.user.avatar_url));
        holder.fanName.setText(fan.user.name);
    }

    public void setData(List<Fan> newShots) {
        mFans.clear();
        appendData(newShots);
    }

    public void appendData(List<Fan> newShots) {
        mFans.addAll(newShots);
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.fan_portrait)
        SimpleDraweeView fanPortrait;

        @Bind(R.id.fan_name)
        TextView fanName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
