package com.vhackclub.oliu.event_planner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vhackclub.oliu.R;
import com.vhackclub.oliu.base.BaseEvent;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.models.Option;
import com.vhackclub.oliu.models.Restaurant;

import java.util.List;

/**
 * Created by geruk on 7/25/15.
 */
public class EventPlannerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum VIEW_TYPES {
        TITLE,
        PLACE_PICKER,
        COMMENT,
        COMMENT_ADD
    }

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<Comment> mComments;
    private BaseEvent mEvent;
    private PlacePickerViewPagerAdapter mAdapter;

    public EventPlannerRecyclerAdapter(BaseEvent event, Context context, LayoutInflater layoutInflater) {
        this.mEvent = event;
        this.mContext = context;
        this.mLayoutInflater = layoutInflater;
        mAdapter = new PlacePickerViewPagerAdapter(mContext, mLayoutInflater);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (VIEW_TYPES.values()[viewType]) {
            case TITLE:
                return new CommentViewHolder(new TextView(mContext));
            case PLACE_PICKER:
                View view = mLayoutInflater.inflate(R.layout.place_pager, parent, false);
                return new ViewPagerHolder(view, mAdapter);
            case COMMENT:
                return new CommentViewHolder(new TextView(mContext));
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (VIEW_TYPES.values()[getItemViewType(position)]) {
            case TITLE:
                ((TextView) holder.itemView).setText(mEvent.getType().name());
            case PLACE_PICKER:
                return;
            case COMMENT:
                ((TextView) holder.itemView).setText(mComments.get(position - getOffsetPickerCount() - 1).getText());
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 1) {
            return VIEW_TYPES.TITLE.ordinal();
        }
        else if (position < 1 + getOffsetPickerCount()) {
            return VIEW_TYPES.PLACE_PICKER.ordinal();
        }
        return VIEW_TYPES.COMMENT.ordinal();
    }

    @Override
    public int getItemCount() {
        return 1 /* title */ + getOffsetPickerCount() + (mComments == null ? 0 : mComments.size());
    }

    private int getOffsetPickerCount() {
        return 1;
        // TODO
    }

    public void updateComments(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    public void addComment(Comment comment) {
        mComments.add(comment);
        notifyItemInserted(mComments.size() - 1);
    }

    public void updateRestaurant(List<Restaurant> suggestions) {
        mAdapter.updateSuggestions(suggestions);
    }

    private class ViewPagerHolder extends RecyclerView.ViewHolder {

        private ViewPager mViewPager;

        public ViewPagerHolder(View itemView, PagerAdapter adapter) {
            super(itemView);
            mViewPager = (ViewPager) itemView;
            mViewPager.setAdapter(adapter);
        }
    }

    private class CommentViewHolder extends RecyclerView.ViewHolder {
        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
