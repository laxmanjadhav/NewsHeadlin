package com.laxman.newsheadline.NetworkPackage;

import com.laxman.newsheadline.Model.TopHeadlineResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkInterface {
    @GET("top-headlines")
    Observable<TopHeadlineResponse> GetHeadline(@Query("country") String country, @Query("apiKey") String apiKey);

}
