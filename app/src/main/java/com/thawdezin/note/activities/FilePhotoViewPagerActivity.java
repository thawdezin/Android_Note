package com.thawdezin.note.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.thawdezin.note.R;
import com.thawdezin.note.adapters.FilePhotoViewPagerAdapter;
import com.thawdezin.note.data.vos.MyFile;
import com.thawdezin.note.fragment.FragmentFile;
import com.thawdezin.note.utils.MyConstants;

import java.util.ArrayList;
import java.util.List;

public class FilePhotoViewPagerActivity extends AppCompatActivity {

    public static ActionBar actionBar;
    ViewPager viewPager;
    FilePhotoViewPagerAdapter filePhotoViewPagerAdapter;
    List<MyFile> filePhotoViewArray = new ArrayList<>();
    private int filePhotoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_photo_view_pager);

        init();

        viewPager = findViewById(R.id.view_pager);
        filePhotoPosition = getIntent().getIntExtra(MyConstants.FILE_POSITION_POSITION, 0);
        Toast.makeText(this, String.valueOf(filePhotoPosition), Toast.LENGTH_SHORT).show();
        filePhotoViewArray = FragmentFile.getMyFileList();
        filePhotoViewPagerAdapter = new FilePhotoViewPagerAdapter(this, filePhotoViewArray);
        viewPager.setAdapter(filePhotoViewPagerAdapter);
        viewPager.setCurrentItem(filePhotoPosition);
    }

    /**
     * Initialize
     */
    private void init() {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.file_photo_back_icon);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));
        actionBar.setElevation(0);
        actionBar.setTitle("");
        // removing status bar and show image full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// edited here
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        Intent intent = new Intent(FilePhotoViewPagerActivity.this, MainActivity.class);
        intent.putExtra(MyConstants.INTENT_TO_MAIN, R.id.bn_file);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MyConstants.INTENT_TO_MAIN_FILE_POSITION,filePhotoPosition);
        startActivity(intent);
    }

}