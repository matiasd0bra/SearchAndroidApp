package com.android.dobratinich.searchapp.itemDetail;

import com.android.dobratinich.searchapp.domain.Item;

import java.util.List;

public class ItemDetailPresenter implements ItemDetailContract.Presenter {

    private ItemDetailModel mModel;
    private ItemDetailContract.View mView;

    public ItemDetailPresenter(ItemDetailContract.View view) {
        mView = view;
        mModel = new ItemDetailModel(this);
    }

    @Override
    public void searchItemById(String id) {
        mModel.searchItemById(id);
    }

    @Override
    public void showItemImages(List<String> imgUrls) {
        mView.showItemImages(imgUrls);
    }

    @Override
    public void showItemInfo(Item item) {
        mView.showItemInfo(item);
    }

    @Override
    public void showItemDescription(String description) {
        mView.showItemDescription(description);
    }

    @Override
    public void showError(String error, String errorCode) {
        mView.showError(error, errorCode);
    }

    @Override
    public void searchItemPictures(String id) {
        mModel.searchItemPictures(id);
    }

    @Override
    public void searchItemDescription(String id) {
        mModel.searchItemDescription(id);
    }
}
