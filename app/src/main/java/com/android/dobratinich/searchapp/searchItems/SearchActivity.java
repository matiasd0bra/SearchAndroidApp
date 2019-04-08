package com.android.dobratinich.searchapp.searchItems;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dobratinich.searchapp.adapter.RecyclerViewAdapter;
import com.android.dobratinich.searchapp.domain.Item;
import com.android.dobratinich.searchapp.domain.SearchItem;
import com.android.dobratinich.searchapp.R;
import com.android.dobratinich.searchapp.itemDetail.ItemDetailActivity;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, SwipeRefreshLayout.OnRefreshListener {

    private SearchPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchItem mItemList;
    private RecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mLastSearch;
    private RelativeLayout errorLayout;
    private TextView errorTitle, errorDescription;
    private Button errorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mPresenter = new SearchPresenter(this);
        initLayout();
        initErrorLayout();
    }

    private void initLayout() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Que deseas buscar?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    mLastSearch = query;
                    mSwipeRefreshLayout.setRefreshing(true);
                    onLoadingSwipeRefresh(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        if (mLastSearch != null && !mLastSearch.isEmpty()) {
            onLoadingSwipeRefresh(mLastSearch);
        }
    }

    private void onLoadingSwipeRefresh(final String keyword) {
        errorLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.searchItems(keyword);
                    }
                }
        );
    }

    @Override
    public void showSearchResult(SearchItem searchItems) {
        mSwipeRefreshLayout.setRefreshing(false);
        mItemList = searchItems;
        mAdapter = new RecyclerViewAdapter(searchItems.getResults(), SearchActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchActivity.this, ItemDetailActivity.class);
                Item item = mItemList.getResults().get(position);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
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
                onRefresh();
            }
        });
    }
}
