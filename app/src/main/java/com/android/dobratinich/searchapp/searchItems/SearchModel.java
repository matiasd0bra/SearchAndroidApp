package com.android.dobratinich.searchapp.searchItems;

import com.android.dobratinich.searchapp.domain.SearchItem;
import com.android.dobratinich.searchapp.api.ApiClient;
import com.android.dobratinich.searchapp.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchModel implements SearchContract.Model{

    private SearchContract.Presenter mPresenter;
    private SearchItem mItemList;

    public SearchModel(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void searchItems(String keyword) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SearchItem> call;
        call = apiInterface.getSearch(keyword);
        call.enqueue(new Callback<SearchItem>() {
            @Override
            public void onResponse(Call<SearchItem> call, Response<SearchItem> response) {
                if (response.isSuccessful() && response.body().getResults() != null) {
                    if (mItemList.getResults() != null && !mItemList.getResults().isEmpty()) {
                        mItemList.getResults().clear();
                    }
                    mItemList = response.body();
                    mPresenter.showSearchResult(mItemList);
                } else {
                    mPresenter.showError("No se encontraron resultados");
                }
            }

            @Override
            public void onFailure(Call<SearchItem> call, Throwable t) {
                mPresenter.showError("No se encontraron resultados");
            }
        });
    }
}
