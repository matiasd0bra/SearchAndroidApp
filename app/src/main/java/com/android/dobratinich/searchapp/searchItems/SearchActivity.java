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
    private SearchItem mItemList = new SearchItem();
    private RecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mLastSearch;
    private String TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mPresenter = new SearchPresenter(this);
        initLayout();
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
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
