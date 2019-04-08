package com.android.dobratinich.searchapp.searchItems;

import com.android.dobratinich.searchapp.domain.SearchItem;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchModel mModel;
    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        this.mView = view;
        mModel = new SearchModel(this);
    }

    @Override
    public void showError(String error, String errorCode) {
        mView.showError(error, errorCode);
    }

    @Override
    public void showSearchResult(SearchItem searchItems) {
        mView.showSearchResult(searchItems);
    }

    @Override
    public void searchItems(String keyword) {
        mModel.searchItems(keyword);
    }
}
