package com.android.dobratinich.searchapp.itemDetail;

import com.android.dobratinich.searchapp.domain.Description;
import com.android.dobratinich.searchapp.domain.Item;

import java.util.List;

public interface ItemDetailContract {

    interface View {
        void showItemImages(List<String> imgUrls);

        void showItemInfo(Item item);

        void showItemDescription(String description);

        void showError(String error);
    }

    interface Presenter {
        void searchItemById(String id);

        void showItemImages(List<String> imgUrls);

        void showItemInfo(Item item);

        void showItemDescription(String description);

        void showError(String error);

        void searchItemPictures(String id);

        void searchItemDescription(String id);
    }

    interface Model {
        void searchItemById(String id);

        void searchItemPictures(String id);

        void searchItemDescription(String id);
    }
}
