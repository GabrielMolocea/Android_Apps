package com.gabriel.taxifee;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("maps/api/taxifee.json")
    Single<Result> getDistance(@Query("key") String key,
                         @Query("origins") String origin,
                         @Query("destination") String destination);
}
