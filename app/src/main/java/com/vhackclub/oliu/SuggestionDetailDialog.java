package com.vhackclub.oliu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.vhackclub.oliu.event_planner.RestaurantCard;
import com.vhackclub.oliu.models.Restaurant;

/**
 * Created by duncapham on 7/26/15.
 */
public class SuggestionDetailDialog extends DialogFragment implements View.OnClickListener{
    private Button mBtnChoose;
    private Restaurant restaurant;

    public interface OnChoosingSuggestionListener {
        void onChoosingSuggestion(String suggestionId);
    }

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
        restaurant = (Restaurant) getArguments().getSerializable("restaurant");
        restaurantCard.setRestaurant(restaurant);
        mBtnChoose.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        final OnChoosingSuggestionListener listener = (OnChoosingSuggestionListener) getActivity();
        restaurant.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    String id = restaurant.getObjectId();
                    listener.onChoosingSuggestion(id);
                }
            }
        });
    }
}
