package com.vhackclub.oliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vhackclub.oliu.base.BaseEvent;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.event_planner.EventPlannerRecyclerAdapter;
import com.vhackclub.oliu.models.Restaurant;
import com.vhackclub.oliu.util.Util;

import java.util.Arrays;

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
        pullEvent(id);

        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        ParseObject.registerSubclass(BaseEvent.class);
        mAdapter = new EventPlannerRecyclerAdapter(null, this, getLayoutInflater());

        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(mAdapter);
    }

    public void initView() {
        mCommentCreate = (Button) findViewById(R.id.comment_send);
        mCommentEdt = (EditText) findViewById(R.id.comment_edt);
        mCommentCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setText(mCommentEdt.getText().toString());
                comment.setUser(ParseUser.getCurrentUser());
                mEvent.put("comments", comment);
                mEvent.saveInBackground();
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
                    mEvent = baseEvent;
                    mAdapter.updateEvent(baseEvent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.PICK_LOCATION) {
            if (resultCode == RESULT_OK) {
                String locationId = data.getStringExtra("restaurandId");
            }
        }
    }
}
