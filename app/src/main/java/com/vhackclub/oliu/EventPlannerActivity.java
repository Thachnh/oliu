package com.vhackclub.oliu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.ParseObject;
import com.vhackclub.oliu.base.BaseEvent;
import com.vhackclub.oliu.base.Comment;
import com.vhackclub.oliu.event_planner.EventPlannerRecyclerAdapter;
import com.vhackclub.oliu.models.Restaurant;

import java.util.Arrays;

public class EventPlannerActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.event_planner);
        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        ParseObject.registerSubclass(BaseEvent.class);
        BaseEvent e = new BaseEvent();
        e.setType(com.vhackclub.oliu.base.BaseEvent.TYPE.EAT);
        EventPlannerRecyclerAdapter adapter = new EventPlannerRecyclerAdapter(e, this, getLayoutInflater());

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
        adapter.updateRestaurant(Arrays.asList(Restaurant.createInstance(), Restaurant.createInstance()));
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }
}
