package com.android.dobratinich.searchapp.api;

import com.android.dobratinich.searchapp.domain.Description;
import com.android.dobratinich.searchapp.domain.Item;
import com.android.dobratinich.searchapp.domain.SearchItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("sites/MLA/search")
    Call<SearchItem> getSearch(
            @Query("q") String id
    );

    @GET("items/{id}")
    Call<Item> getItem(
            @Path("id") String id
    );

    @GET("items/{id}/description")
    Call<Description> getItemDescription(
            @Path("id") String id
    );

}
