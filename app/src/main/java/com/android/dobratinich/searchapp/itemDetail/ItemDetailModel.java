package com.android.dobratinich.searchapp.itemDetail;

import com.android.dobratinich.searchapp.api.ApiClient;
import com.android.dobratinich.searchapp.api.ApiInterface;
import com.android.dobratinich.searchapp.domain.Description;
import com.android.dobratinich.searchapp.domain.Item;
import com.android.dobratinich.searchapp.domain.Picture;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailModel implements ItemDetailContract.Model {

    private ItemDetailContract.Presenter mPresenter;
    private ApiInterface apiInterface;
    private Item mItem;


    public ItemDetailModel(ItemDetailContract.Presenter presenter) {
        mPresenter = presenter;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    @Override
    public void searchItemById(String id) {
        if(mItem == null || mItem.getId()!=id) {
        Call<Item> call;
        call = apiInterface.getItem(id);

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                mItem = response.body();
                mPresenter.showItemInfo(mItem);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                mPresenter.showError("Esto falla");
            }
        });
        } if (mItem != null && mItem.getId()==id) {
            mPresenter.showItemInfo(mItem);
        }
    }

    @Override
    public void searchItemPictures(String id) {
        if(mItem == null || mItem.getId()!=id) {
            Call<Item> call;
            call = apiInterface.getItem(id);

            call.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    mItem = response.body();
                    mPresenter.showItemImages(getPicturesUrlList());
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    mPresenter.showError("Esto falla");
                }
            });
        } else if (mItem != null && mItem.getId()==id) {
            mPresenter.showItemImages(getPicturesUrlList());
        }
    }

    @Override
    public void searchItemDescription(String id) {
            Call<Description> call;
            call = apiInterface.getItemDescription(id);

            call.enqueue(new Callback<Description>() {
                @Override
                public void onResponse(Call<Description> call, Response<Description> response) {
                    final Description descr = response.body();
                    mPresenter.searchItemDescription(descr.getPlainText());
                }

                @Override
                public void onFailure(Call<Description> call, Throwable t) {
                    mPresenter.showError("Esto falla");
                }
            });
    }

    private List<String> getPicturesUrlList() {
        List<String> pictureList = new ArrayList<String>();
        for (Picture pic:mItem.getPictures()) {
            pictureList.add(pic.getUrl());
        }
        return pictureList;
    }

}
