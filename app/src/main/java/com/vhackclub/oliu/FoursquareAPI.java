package com.vhackclub.oliu;

import com.vhackclub.oliu.models.Restaurant;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by duncapham on 7/25/15.
 */
public interface FoursquareAPI {
    @GET("/venues/search?v=20130815&near=San%20Jose,%20CA")
    public void get4SquareSearchVenuesData(@Query("client_id") String client_id,
                                           @Query("client_secret") String client_secret,
                                           @Query("query") String query,
                                           Callback<Response> response);

    @GET("/venues/{venue_id}?v=20130815")
    public void get4SquareSpecificVenueData(@Path("venue_id") String venue_id,
                                            @Query("client_id") String client_id,
                                            @Query("client_secret") String client_secret,
                                            Callback<Response> response);

}
