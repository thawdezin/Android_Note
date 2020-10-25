package com.thawdezin.note.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.thawdezin.note.R;
import com.thawdezin.note.utils.MyConstants;
import com.github.chrisbanes.photoview.PhotoView;

public class PresidentPhotoViewActivity extends AppCompatActivity {
    private static boolean flag = true;
    private ActionBar actionBar;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.president_photo_view);
        init();
        Intent intent = getIntent();
        url = intent.getStringExtra(MyConstants.DETAIL_INTENT_PHOTOVIEW);
        PhotoView photoView = findViewById(R.id.president_photo_view);
        Glide.with(this).load(url).placeholder(R.drawable.placeholder_image).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    actionBar.show();
                    flag = false;
                } else {
                    actionBar.hide();
                    flag = true;
                }
            }
        });
    }

    private void init() {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.file_photo_back_icon);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// edited here
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));
        actionBar.setElevation(0);
        actionBar.hide();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            callingIntent();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callingIntent() {
        Intent intent = new Intent(PresidentPhotoViewActivity.this, InfoPresidentDetailActivity.class);
        intent.putExtra(MyConstants.DETAIL_INTENT_PHOTOVIEW,url);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
