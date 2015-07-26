package com.vhackclub.oliu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vhackclub.oliu.event_planner.RestaurantCard;
import com.vhackclub.oliu.models.Restaurant;

/**
 * Created by duncapham on 7/26/15.
 */
public class SuggestionDetailDialog extends DialogFragment{
    private Button mBtnChoose;

    public SuggestionDetailDialog() {

    }

    public static SuggestionDetailDialog newInstance(Restaurant restaurant) {
        SuggestionDetailDialog suggestionDetailDialog = new SuggestionDetailDialog();
        Bundle args = new Bundle();
        args.putSerializable("restaurant", restaurant);
        suggestionDetailDialog.setArguments(args);
        return suggestionDetailDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_detail_view, container, false);
        RestaurantCard restaurantCard = (RestaurantCard) view.findViewById(R.id.cvRestaurant);
        mBtnChoose = (Button) view.findViewById(R.id.btnChoose);
        Restaurant restaurant = (Restaurant) getArguments().getSerializable("restaurant");
        restaurantCard.setRestaurant(restaurant);
        return view;
    }
}
