package com.thawdezin.note.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.adapters.InfoPresidentSeeAllAdapter;
import com.thawdezin.note.adapters.InfoPresidentSeeAllAdapterGridLayout;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.fragment.FragmentInfo;
import com.thawdezin.note.utils.MyConstants;

import java.util.ArrayList;
import java.util.List;

public class InfoPresidentsSeeAllActivity extends AppCompatActivity {
    public static int layout = 0;
    InfoPresidentSeeAllAdapter infoPresidentSeeAllAdapter;
    InfoPresidentSeeAllAdapterGridLayout infoPresidentSeeAllAdapterGridLayout;
    private RecyclerView infoSeeAllRecyclerView;
    private int scrollPosition = 0;
    private List<AmericanPresidents> presidentsSeeAllList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_president_see_all_view_layout);
        infoSeeAllRecyclerView = findViewById(R.id.info_see_all_recycler_view);
        infoSeeAllRecyclerView.setItemAnimator(null);
        init();
        if (layout == 0) {
            setUpRecyclerView();
        } else {
            setUpRecyclerViewWithGridLayout();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.see_all_presidents);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                callingIntent();
                return true;
            case R.id.linear_layout:
                layout = 0;
                setUpRecyclerView();
                return true;
            case R.id.grid_layout:
                layout = 1;
                setUpRecyclerViewWithGridLayout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerViewWithGridLayout() {
        scrollPosition = getIntent().getIntExtra(MyConstants.DETAIL_SELL_ALL_PRESIDENT_NUMBER, 0);
        presidentsSeeAllList = FragmentInfo.getPresidentsSeeAllList();
        infoPresidentSeeAllAdapterGridLayout = new InfoPresidentSeeAllAdapterGridLayout(presidentsSeeAllList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        infoSeeAllRecyclerView.setLayoutManager(layoutManager);
        infoSeeAllRecyclerView.setAdapter(infoPresidentSeeAllAdapterGridLayout);
        if (scrollPosition != 0) {
            infoSeeAllRecyclerView.scrollToPosition(scrollPosition);
        }
    }

    private void callingIntent() {
        Intent intent = new Intent(InfoPresidentsSeeAllActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MyConstants.INTENT_TO_MAIN, R.id.bn_info);
        startActivity(intent);
    }

    private void setUpRecyclerView() {
        scrollPosition = getIntent().getIntExtra(MyConstants.DETAIL_SELL_ALL_PRESIDENT_NUMBER, 0);
        presidentsSeeAllList = FragmentInfo.getPresidentsSeeAllList();
        infoPresidentSeeAllAdapter = new InfoPresidentSeeAllAdapter(presidentsSeeAllList);
        infoSeeAllRecyclerView.setAdapter(infoPresidentSeeAllAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        infoSeeAllRecyclerView.setLayoutManager(layoutManager);
        if (scrollPosition != 0) {
            infoSeeAllRecyclerView.scrollToPosition(scrollPosition);
        }
    }

    /**
     * getting layout menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

}
