package com.vhackclub.oliu.event_planner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vhackclub.oliu.R;
import com.vhackclub.oliu.base.BaseSuggestion;

/**
 * Created by geruk on 7/26/15.
 */
public class VoteRow extends LinearLayout {

    public BaseSuggestion mSuggestion;
    public final TextView mLikeCount;
    public final CheckBox mLike;

    public VoteRow(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.vote_layout, this);
        mLikeCount = (TextView) findViewById(R.id.like_count);
        mLike = (CheckBox) findViewById(R.id.like);
        reset();
    }

    public VoteRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoteRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.vote_layout, this);
        mLikeCount = (TextView) findViewById(R.id.like_count);
        mLike = (CheckBox) findViewById(R.id.like);
        reset();
    }

    public void reset() {
        mLike.setChecked(false);
        mLikeCount.setText(Integer.toString(0));
    }

    public void bindSuggestion(BaseSuggestion suggestion) {
        mSuggestion = suggestion;
        int likeCount = mSuggestion.getVoters() == null ? 0 : mSuggestion.getVoters().size();
        mLikeCount.setText(Integer.toString(likeCount));
        mLike.setChecked(hasCurrentUserLike());
        mLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLike.setEnabled(false);
                if (isChecked) {
                    mSuggestion.addVote();
                    mSuggestion.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            mLike.setEnabled(true);
                            int likeCount = mSuggestion.getVoters() == null ? 0 : mSuggestion.getVoters().size();
                            mLikeCount.setText(Integer.toString(likeCount));
                        }
                    });
                } else {
                    mSuggestion.unVote();
                    mSuggestion.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            mLike.setEnabled(true);
                            int likeCount = mSuggestion.getVoters() == null ? 0 : mSuggestion.getVoters().size();
                            mLikeCount.setText(Integer.toString(likeCount));
                        }
                    });
                }
            }
        });
    }

    public boolean hasCurrentUserLike() {
        if (mSuggestion.getVoters() == null) {
            return false;
        }
        ParseUser currentUser = ParseUser.getCurrentUser();
        for (ParseUser user : mSuggestion.getVoters()) {
            if (user.getObjectId().equals(currentUser.getObjectId())) {
                return true;
            }
        }
        return false;
    }
}
