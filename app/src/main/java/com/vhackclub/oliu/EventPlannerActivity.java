package com.vhackclub.oliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vhackclub.oliu.base.BaseEvent;
import com.vhackclub.oliu.base.BaseLocation;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.event_planner.EventPlannerRecyclerAdapter;
import com.vhackclub.oliu.models.LocationSuggestion;
import com.vhackclub.oliu.models.Restaurant;
import com.vhackclub.oliu.util.Util;

import java.util.List;

public class EventPlannerActivity extends FragmentActivity {

    private EventPlannerRecyclerAdapter mAdapter;
    private BaseEvent mEvent;
    private EditText mCommentEdt;
    private Button mCommentCreate;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.event_planner);
        initView();

        Bundle b = getIntent().getExtras();
        String id = b.getString("event_id");
        subscribePush(id);
        pullEvent(id);

        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new EventPlannerRecyclerAdapter(null, this, getLayoutInflater());

        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(mAdapter);
    }

    private void subscribePush(final String eventId) {
        ParsePush.subscribeInBackground("event-" + eventId);
    }

    public void initView() {
        mCommentCreate = (Button) findViewById(R.id.comment_send);
        mCommentEdt = (EditText) findViewById(R.id.comment_edt);
        mCommentCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Comment comment = new Comment();
                comment.setText(mCommentEdt.getText().toString());
                comment.setUser(ParseUser.getCurrentUser());
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        mEvent.addComment(comment);
                        mEvent.saveInBackground();
                        Util.push("event-" + mEvent.getObjectId(), "comment", comment.getObjectId(), comment.getPushDesc());
                    }
                });
                mAdapter.addComment(comment);
                mCommentEdt.setText("");
            }
        });

    }

    private void pullEvent(String id) {
        ParseQuery<BaseEvent> query = ParseQuery.getQuery(BaseEvent.class);

        query.getInBackground(id, new GetCallback<BaseEvent>() {
            @Override
            public void done(BaseEvent baseEvent, ParseException e) {
                if (e == null) {
                    try {
                        baseEvent.fetchIfNeeded();
                        mEvent = baseEvent;
                        tryAddMember();
                        mAdapter.updateEvent(baseEvent);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.d("TEST", "" + Log.getStackTraceString(e));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.PICK_LOCATION) {
            if (resultCode == RESULT_OK) {
                String locationId = data.getStringExtra("restaurandId");
                Log.d("activity Result", "id " + locationId);
                handleNewLocation(locationId);
            }
        }
    }

    private void tryAddMember() {
        List<ParseUser> mems = mEvent.getMembers();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (mems == null || !mems.contains(currentUser)) {
            mEvent.addMember(currentUser);
            Util.push("event-" + mEvent.getObjectId(), "add_member", currentUser.getObjectId(), currentUser.getObjectId() + " just joined");
        }
    }

    private void handleNewLocation(String locationId) {
        ParseQuery<Restaurant> query = ParseQuery.getQuery(Restaurant.class);

        query.getInBackground(locationId, new GetCallback<Restaurant>() {
            @Override
            public void done(Restaurant restaurant, ParseException e) {
                if (e == null) {
                    Log.d("handle new", "find location " + restaurant.getObjectId() + " " + restaurant.getName());
                    addLocationSuggestion(restaurant);
                }
            }
        });
    }

    private void addLocationSuggestion(BaseLocation location) {
        final LocationSuggestion suggestion = new LocationSuggestion();
        suggestion.setLocation(location);
        suggestion.setEvent(mEvent);
        suggestion.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("add location", "new suggestion id " + suggestion.getObjectId());
                Util.push("event-" + mEvent.getObjectId(), "suggest_location", suggestion.getObjectId(), "Someone suggests some shit");
                mEvent.addLocationSuggestion(suggestion);
                mEvent.saveInBackground();
                mAdapter.addLocationSuggestion(suggestion);
            }
        });
    }
}
