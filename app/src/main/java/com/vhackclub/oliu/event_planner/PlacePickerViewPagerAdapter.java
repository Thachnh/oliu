package com.vhackclub.oliu.event_planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.vhackclub.oliu.R;
import com.vhackclub.oliu.SearchSuggestionActivity;
import com.vhackclub.oliu.models.LocationSuggestion;
import com.vhackclub.oliu.models.Restaurant;
import com.vhackclub.oliu.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geruk on 7/25/15.
 */
public class PlacePickerViewPagerAdapter extends PagerAdapter {

    private List<LocationSuggestion> mSuggestions = new ArrayList<LocationSuggestion>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PlacePickerViewPagerAdapter(
            Context context,
            LayoutInflater layoutInflater) {
        mContext = context;
        mLayoutInflater = layoutInflater;
    }

    public void updateSuggestions(List<LocationSuggestion> suggestions) {
        if (suggestions == null) {
            mSuggestions = new ArrayList<LocationSuggestion>();
        } else {
            mSuggestions = suggestions;
        }
        notifyDataSetChanged();
    }

    public void addSuggestion(LocationSuggestion suggestion) {
        mSuggestions.add(suggestion);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSuggestions == null ? 1 : mSuggestions.size() + 1 /* Adding option */;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewKey viewKey = (ViewKey) object;
        if (viewKey.objectId == null) {
            return !(view instanceof RestaurantCard);
        };
        if (view instanceof RestaurantCard) {
            return ((RestaurantCard) view).getRestaurant().getObjectId() == viewKey.objectId;
        } else {
            return false;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mSuggestions != null && position < mSuggestions.size()) {
            RestaurantCard view = (RestaurantCard) mLayoutInflater.inflate(R.layout.restaurant_page, container, false);
            container.addView(view);
            final LocationSuggestion suggestion = (LocationSuggestion) mSuggestions.get(position);

            // create restaurant card view
            try {
                suggestion.fetchIfNeeded();
                Log.d("instantiate item", "position " + position + " item " + suggestion.getObjectId());
                Restaurant restaurant = (Restaurant) suggestion.getLocation();
                restaurant.fetchIfNeeded();
                view.setRestaurant(restaurant);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // create & attach vote view
            VoteRow voteView = (VoteRow) mLayoutInflater.inflate(R.layout.vote_row, view, false);
            voteView.reset();
            voteView.bindSuggestion(mSuggestions.get(position));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM;
            view.addView(voteView, params);
            return new ViewKey(suggestion.getLocation().getObjectId());
        }
        Log.d("instantiate item", "add add button " + position);
        View view = mLayoutInflater.inflate(R.layout.add_option_page, container, false);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).startActivityForResult(new Intent(mContext, SearchSuggestionActivity.class), Util.PICK_LOCATION);
            }
        });
        return new ViewKey();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        for (int i = 0; i < container.getChildCount(); i++) {
            if (isViewFromObject(container.getChildAt(i), object)) {
                container.removeViewAt(i);
                return;
            }
        }
    }

    public static class ViewKey {
        public final String objectId;
        public ViewKey() {
            this.objectId = null;
        }
        public ViewKey(String objectId) {
            this.objectId = objectId;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
