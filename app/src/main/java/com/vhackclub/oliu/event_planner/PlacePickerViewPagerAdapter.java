package com.vhackclub.oliu.event_planner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vhackclub.oliu.R;
import com.vhackclub.oliu.base.Suggestion;

import java.util.List;

/**
 * Created by geruk on 7/25/15.
 */
public class PlacePickerViewPagerAdapter extends PagerAdapter {

    private List<Suggestion> mSuggestion;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PlacePickerViewPagerAdapter(
            Context context,
            LayoutInflater layoutInflater,
            List<Suggestion> suggestion) {
        mSuggestion = suggestion;
        mContext = context;
        mLayoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return mSuggestion == null ? 0 : mSuggestion.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.place_card, container, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mSuggestion.get(position).getName());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
