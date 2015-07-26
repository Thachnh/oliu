package com.vhackclub.oliu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.vhackclub.oliu.base.BaseEvent;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.event_planner.EventPlannerRecyclerAdapter;
import com.vhackclub.oliu.models.Restaurant;

import java.util.Arrays;

public class EventPlannerActivity extends FragmentActivity {

    public static BaseEvent mEvent;
    private EventPlannerRecyclerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.event_planner);

        Bundle b = getIntent().getExtras();
        String id = b.getString("event_id");
        pullEvent(id);

        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        ParseObject.registerSubclass(BaseEvent.class);
        mAdapter = new EventPlannerRecyclerAdapter(null, this, getLayoutInflater());

        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(mAdapter);
    }

    private void pullEvent(String id) {
        Log.d("pull event", "event id " + id);
        ParseQuery<BaseEvent> query = ParseQuery.getQuery(BaseEvent.class);

        query.getInBackground(id, new GetCallback<BaseEvent>() {
            @Override
            public void done(BaseEvent baseEvent, ParseException e) {
                if (e == null) {
                    EventPlannerActivity.mEvent = baseEvent;
                    Log.d("done query", "event " + baseEvent.getGreeting());
                    mAdapter.updateEvent(baseEvent);
                }
            }
        });
    }

}
