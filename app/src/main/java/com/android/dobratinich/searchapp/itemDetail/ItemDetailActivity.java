package com.android.dobratinich.searchapp.itemDetail;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private RelativeLayout errorLayout;
    private TextView errorTitle, errorDescription;
    private Button errorBtn;

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

        errorLayout.setVisibility(View.GONE);
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
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        errorTitle.setText(error);
        errorDescription.setText(errorCode);
    }

    private void initErrorLayout() {
        errorLayout = findViewById(R.id.error_layout);
        errorTitle = findViewById(R.id.error_title);
        errorDescription = findViewById(R.id.error_desc);
        errorBtn = findViewById(R.id.error_btn);
        errorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retriveItemDetail();
            }
        });
    }
}
