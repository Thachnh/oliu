package com.vhackclub.oliu.event_planner;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vhackclub.oliu.R;
import com.vhackclub.oliu.models.Restaurant;

/**
 * Created by geruk on 7/26/15.
 */
public class RestaurantCard extends CardView {

    private Restaurant mRestaurant;
    private final TextView mTitle;
    private final TextView mSubtitle;
    private final RatingBar mRating;
    private final TextView mAddress;
    private final TextView mContact;
    private final TextView mReview;
    private final TextView mTier;
    private final ImageView mBackground;

    public RestaurantCard(Context context) {
        super(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.place_card, this);
        mBackground = (ImageView) layout.findViewById(R.id.background_image);
        mTitle = (TextView) layout.findViewById(R.id.title);
        mSubtitle = (TextView) layout.findViewById(R.id.subtitle);
        mContact= (TextView) layout.findViewById(R.id.contact);
        mAddress = (TextView) layout.findViewById(R.id.address);
        mReview = (TextView) layout.findViewById(R.id.review);
        mTier = (TextView) layout.findViewById(R.id.tier);
        mRating = (RatingBar) layout.findViewById(R.id.rating);
    }

    public RestaurantCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.place_card, this);
        mBackground = (ImageView) layout.findViewById(R.id.background_image);
        mTitle = (TextView) layout.findViewById(R.id.title);
        mSubtitle = (TextView) layout.findViewById(R.id.subtitle);
        mContact= (TextView) layout.findViewById(R.id.contact);
        mAddress = (TextView) layout.findViewById(R.id.address);
        mReview = (TextView) layout.findViewById(R.id.review);
        mTier = (TextView) layout.findViewById(R.id.tier);
        mRating = (RatingBar) layout.findViewById(R.id.rating);
    }

    public RestaurantCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View layout = LayoutInflater.from(context).inflate(R.layout.place_card, this);
        mBackground = (ImageView) layout.findViewById(R.id.background_image);
        mTitle = (TextView) layout.findViewById(R.id.title);
        mSubtitle = (TextView) layout.findViewById(R.id.subtitle);
        mContact= (TextView) layout.findViewById(R.id.contact);
        mAddress = (TextView) layout.findViewById(R.id.address);
        mReview = (TextView) layout.findViewById(R.id.review);
        mTier = (TextView) layout.findViewById(R.id.tier);
        mRating = (RatingBar) layout.findViewById(R.id.rating);
    }

    public void reset() {
        mTitle.setText("");
        mSubtitle.setText("");
        mRating.setRating(0.0f);
        mContact.setText("");
        mAddress.setText("");
        mReview.setText("");
        mTier.setText("");
    }

    public void setRestaurant(Restaurant rest) {
        mRestaurant = rest;
        mBackground.setImageResource(R.drawable.background);
        mTitle.setText(rest.getName());
        mSubtitle.setText(rest.getCategory());
        mRating.setRating(1.0f);//rest.getRating());
        mContact.setText(rest.getPhone());
        mAddress.setText(rest.getAddress());
        mReview.setText(rest.getStatus());
        mTier.setText(rest.getTier());
    }

    public Restaurant getRestaurant() {
        return mRestaurant;
    }
}
