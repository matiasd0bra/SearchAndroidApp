package com.android.dobratinich.searchapp.itemDetail;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dobratinich.searchapp.R;
import com.android.dobratinich.searchapp.adapter.ViewPagerAdapter;
import com.android.dobratinich.searchapp.domain.Item;

import java.util.List;

public class ItemDetailActivity extends AppCompatActivity implements ItemDetailContract.View {

    private ItemDetailPresenter mPresenter;
    private ViewPager mItemImage;
    private TextView mTitle, mPrice, mDescription;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        mPresenter = new ItemDetailPresenter(this);

        mTitle = findViewById(R.id.title);
        mPrice = findViewById(R.id.price);
        mDescription = findViewById(R.id.description);

        retriveItemDetail();
    }

    private void retriveItemDetail() {
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");

        mPresenter.searchItemById(mId);
        mPresenter.searchItemDescription(mId);
        mPresenter.searchItemPictures(mId);

    }

    @Override
    public void showItemImages(List<String> imgUrls) {
        mItemImage = findViewById(R.id.item_imgs);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, imgUrls);
        mItemImage.setAdapter(viewPagerAdapter);
    }

    @Override
    public void showItemInfo(Item item) {
        mTitle.setText(item.getTitle());
        mPrice.setText("$"+item.getPrice());
    }

    @Override
    public void showItemDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
