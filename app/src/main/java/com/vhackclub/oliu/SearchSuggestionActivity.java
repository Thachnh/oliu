package com.vhackclub.oliu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vhackclub.oliu.adapters.SuggestionRecyclerViewAdapter;
import com.vhackclub.oliu.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;


public class SearchSuggestionActivity extends ActionBarActivity {
    EditText etSearch;
    Button btnSearch;
    RecyclerView rvSuggestion;
    SuggestionRecyclerViewAdapter adapter;

    FoursquareAPI api;
    public static final String baseUrl = "https://api.foursquare.com/v2";
    private final String TAG = "4square";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_suggestion);
        // look up views
        etSearch     = (EditText) findViewById(R.id.etSearch);
        btnSearch    = (Button)   findViewById(R.id.btnSearch);
        rvSuggestion = (RecyclerView) findViewById(R.id.rvSuggestion);

        // Create adapter passing in the data
        adapter = new SuggestionRecyclerViewAdapter(this, new ArrayList<Restaurant>());
        // bind adapter
        rvSuggestion.setAdapter(adapter);
        // set layout manager
        rvSuggestion.setLayoutManager(new LinearLayoutManager(this));

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(baseUrl).build();
        api = restAdapter.create(FoursquareAPI.class);

        // handle search click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString();
                if (query != null && query.length() != 0) {
                    api.get4SquareSearchVenuesData(
                            FoursquareAPIData.CLIENT_ID,
                            FoursquareAPIData.CLIENT_SECRET,
                            query,
                            new Callback<Response>() {
                                @Override
                                public void success(Response response, Response response2) {
                                    String str = new String(((TypedByteArray) response.getBody()).getBytes());
                                    try {
                                        JSONObject obj = new JSONObject(str);
                                        parseVenues(obj);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            }
                    );


//                    api.get4SquareSpecificVenueData("40a55d80f964a52020f31ee3",
//                            FoursquareAPIData.CLIENT_ID,
//                            FoursquareAPIData.CLIENT_SECRET,
//                            new Callback<Response>() {
//                                @Override
//                                public void success(Response response, Response response2) {
//                                    String str = new String(((TypedByteArray) response.getBody()).getBytes());
//                                    System.out.print(str);
//                                }
//
//                                @Override
//                                public void failure(RetrofitError error) {
//
//                                }
//                            });

                };
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_suggestion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parseVenues(JSONObject obj) {
        adapter.resetItems();
        try {
            JSONArray array = obj.getJSONObject("response").getJSONArray("venues");
            for (int i=0; i<array.length(); i++) {
                JSONObject suggestionJson = null;
                suggestionJson = array.getJSONObject(i);
                String id = suggestionJson.getString("id");
                String name = suggestionJson.getString("name");
                String phone = null;
                if (suggestionJson.getJSONObject("contact").length() != 0) {
                    phone = suggestionJson.getJSONObject("contact").getString("formattedPhone");
                }
                Restaurant restaurant = new Restaurant();
                restaurant.setId(id);
                restaurant.setName(name);
                restaurant.setPhone(phone);
                adapter.addItem(restaurant);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}