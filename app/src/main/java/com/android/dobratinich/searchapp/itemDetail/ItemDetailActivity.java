package com.android.dobratinich.searchapp.itemDetail;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dobratinich.searchapp.R;
import com.android.dobratinich.searchapp.adapter.ViewPagerAdapter;
import com.android.dobratinich.searchapp.domain.Item;

import java.util.List;

public class ItemDetailActivity extends AppCompatActivity implements ItemDetailContract.View {

    private ItemDetailPresenter mPresenter;
    private ViewPager mItemImage;
    private TextView mTitle, mPrice, mDescription;
    private String mId;
    private RelativeLayout mErrorLayout;
    private TextView mErrorTitle, mErrorDescription;
    private Button mErrorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        mPresenter = new ItemDetailPresenter(this);

        mTitle = findViewById(R.id.title);
        mPrice = findViewById(R.id.price);
        mDescription = findViewById(R.id.description);
        initErrorLayout();
        retriveItemDetail();
    }

    private void retriveItemDetail() {
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");

        mErrorLayout.setVisibility(View.GONE);
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
        mPrice.setText("$" + item.getPrice());
    }

    @Override
    public void showItemDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showError(String error, String errorCode) {
        if (mErrorLayout.getVisibility() == View.GONE) {
            mErrorLayout.setVisibility(View.VISIBLE);
        }
        mErrorTitle.setText(error);
        mErrorDescription.setText(errorCode);
    }

    private void initErrorLayout() {
        mErrorLayout = findViewById(R.id.error_layout);
        mErrorTitle = findViewById(R.id.error_title);
        mErrorDescription = findViewById(R.id.error_desc);
        mErrorBtn = findViewById(R.id.error_btn);
        mErrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retriveItemDetail();
            }
        });
    }
}
