package com.vhackclub.oliu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vhackclub.oliu.R;
import com.vhackclub.oliu.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by duncapham on 7/25/15.
 */
public class SuggestionRecyclerViewAdapter extends RecyclerView.Adapter<SuggestionRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Restaurant> suggestions;
    private Context context;

    public SuggestionRecyclerViewAdapter(Context context, ArrayList<Restaurant> suggestions) {
        this.context = context;
        this.suggestions = suggestions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant suggestion = suggestions.get(position);
        holder.tvName.setText(suggestion.getName());
        holder.tvPhone.setText(suggestion.getPhone());
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvName;
        public TextView tvPhone;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
        }
    }

    public void addItem(Restaurant restaurant) {
        this.suggestions.add(restaurant);
    }

    public void resetItems() {
        this.suggestions.clear();
    }
}
