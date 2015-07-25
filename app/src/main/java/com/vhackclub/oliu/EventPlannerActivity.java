package com.vhackclub.oliu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.ParseObject;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.base.Event;
import com.vhackclub.oliu.base.Suggestion;
import com.vhackclub.oliu.event_planner.EventPlannerRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EventPlannerActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.event_planner);
        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        ParseObject.registerSubclass(Event.class);
        Event e = new Event();
        e.setType(Event.TYPE.EAT);
        EventPlannerRecyclerAdapter adapter = new EventPlannerRecyclerAdapter(e, this, getLayoutInflater());

        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Suggestion.class);

        Comment m1 = new Comment();
        m1.setText("Fuck 1");
        Comment m2 = new Comment();
        m2.setText("Fuck 2");
        Comment m3 = new Comment();
        m3.setText("Fuck 3");
        Comment m4 = new Comment();
        m4.setText("Fuck 4");
        Comment m5 = new Comment();
        m5.setText("Fuck 5");
        adapter.updateComments(Arrays.asList(m1, m2, m3, m4, m5));
        Suggestion s1 = new Suggestion();
        s1.setName("Sex 1");
        Suggestion s2 = new Suggestion();
        s2.setName("Sex 2");
        Suggestion s3 = new Suggestion();
        s3.setName("Sex 3");
        Suggestion s4 = new Suggestion();
        s4.setName("Sex 4");
        Suggestion s5 = new Suggestion();
        s5.setName("Sex 5");
        adapter.updateSuggestions(Arrays.asList(s1,s2,s3,s4,s5));
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }
}
