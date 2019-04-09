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
    private ApiInterface mApiInterface;
    private Item mItem;


    public ItemDetailModel(ItemDetailContract.Presenter presenter) {
        mPresenter = presenter;
        mApiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    @Override
    public void searchItemById(String id) {
        if (mItem == null || mItem.getId() != id) {
            Call<Item> call;
            call = mApiInterface.getItem(id);
            call.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        mItem = response.body();
                        mPresenter.showItemInfo(mItem);
                    } else {
                        sendError(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    sendError(0);
                }
            });
        }
        if (mItem != null && mItem.getId() == id) {
            mPresenter.showItemInfo(mItem);
        }
    }

    @Override
    public void searchItemPictures(String id) {
        if (mItem == null || mItem.getId() != id) {
            Call<Item> call;
            call = mApiInterface.getItem(id);

            call.enqueue(new Callback<Item>() {
                @Override
                public void onResponse(Call<Item> call, Response<Item> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        mItem = response.body();
                        mPresenter.showItemImages(getPicturesUrlList());
                    } else {
                        sendError(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Item> call, Throwable t) {
                    sendError(0);
                }
            });
        } else if (mItem != null && mItem.getId() == id) {
            mPresenter.showItemImages(getPicturesUrlList());
        }
    }

    @Override
    public void searchItemDescription(String id) {
        Call<Description> call;
        call = mApiInterface.getItemDescription(id);

        call.enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                if (response.isSuccessful() && response.body() != null) {
                    final Description descr;
                    descr = response.body();
                    mPresenter.showItemDescription(descr.getPlainText());
                } else {
                    sendError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {
                sendError(0);
            }
        });
    }

    private List<String> getPicturesUrlList() {
        List<String> pictureList = new ArrayList<String>();
        for (Picture pic : mItem.getPictures()) {
            pictureList.add(pic.getUrl());
        }
        return pictureList;
    }

    private void sendError(int errorCode) {
        switch (errorCode) {
            case 0:
                mPresenter.showError("No se encontro el item", "Intente nuevamente");
                break;
            case 404:
                mPresenter.showError("No se encontro el item", "Intente nuevamente - Error 404");
                break;
            case 500:
                mPresenter.showError("No se pudo conectar al Servidor", "Intente nuevamente - Error 500");
                break;
            default:
                mPresenter.showError("Error desconocido", "Intente nuevamente");
        }
    }

}
