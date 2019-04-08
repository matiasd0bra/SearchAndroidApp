package com.android.dobratinich.searchapp.searchItems;

import com.android.dobratinich.searchapp.domain.SearchItem;

public interface SearchContract {

    interface View {
        void showSearchResult(SearchItem searchItems);
        void showError(String error);
    }

    interface Presenter {
        void showError(String error);
        void showSearchResult(SearchItem searchItems);
        void searchItems(String keyword);
    }

    interface Model {
        void searchItems(String keyword);
    }
}
