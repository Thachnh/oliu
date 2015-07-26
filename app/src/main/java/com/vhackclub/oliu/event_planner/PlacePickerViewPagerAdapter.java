package com.vhackclub.oliu.event_planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhackclub.oliu.R;
import com.vhackclub.oliu.SearchSuggestionActivity;
import com.vhackclub.oliu.models.LocationSuggestion;
import com.vhackclub.oliu.models.Restaurant;
import com.vhackclub.oliu.util.Util;

import java.util.List;

/**
 * Created by geruk on 7/25/15.
 */
public class PlacePickerViewPagerAdapter extends PagerAdapter {

    private List<LocationSuggestion> mSuggestion;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PlacePickerViewPagerAdapter(
            Context context,
            LayoutInflater layoutInflater) {
        mContext = context;
        mLayoutInflater = layoutInflater;
    }

    public void updateSuggestions(List<LocationSuggestion> suggestion) {
        mSuggestion = suggestion;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSuggestion == null ? 1 : mSuggestion.size() + 1 /* Adding option */;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mSuggestion != null && position < mSuggestion.size()) {
            RestaurantCard view = (RestaurantCard) mLayoutInflater.inflate(R.layout.restaurant_page, container, false);
            container.addView(view);
            LocationSuggestion suggestion = (LocationSuggestion) mSuggestion.get(position);
            view.setRestaurant((Restaurant) suggestion.getLocation());
            return view;
        }
        View view = mLayoutInflater.inflate(R.layout.add_option_page, container, false);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).startActivityForResult(new Intent(mContext, SearchSuggestionActivity.class), Util.PICK_LOCATION);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
