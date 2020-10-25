package com.thawdezin.note.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.thawdezin.note.R;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.fragment.FragmentInfo;
import com.thawdezin.note.utils.MyConstants;

import java.util.ArrayList;
import java.util.List;

public class InfoPresidentDetailActivity extends AppCompatActivity {
    public static ActionBar actionBar;
    private static boolean flag = true;
    public static List<AmericanPresidents> presidentDetailList = new ArrayList<>();
    private ImageView detailImageView;
    private TextView detailPresidentName;
    private TextView detailPresidentNumber;
    private TextView detailPresidentStartYear;
    private TextView detailPresidentEndYear;
    private TextView detailPresidentBirthYear;
    private TextView detailPresidentDeadYear;
    private TextView detailPresidentParty;
    private RelativeLayout relativeLayout;
    private String photoUrl;
    private int scrollPosition;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_president_detail_layout);
        init();
        bindViews();
        relativeLayout = findViewById(R.id.detail_relative_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
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
        detailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewIntent();
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// edited here
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presidentDetailList = FragmentInfo.getPresidentsSeeAllListWithYear();
        String url = getIntent().getStringExtra(MyConstants.DETAIL_INTENT_PHOTOVIEW);
        int seeAllToDetailIntentNumber = getIntent().getIntExtra(MyConstants.INFO_DETAIL_INTENT, 0);
        for (AmericanPresidents americanPresidents : presidentDetailList) {

            if (americanPresidents.getNumber() == seeAllToDetailIntentNumber || americanPresidents.getPhotoUrl().equals(url)) {
                Log.d("Number", seeAllToDetailIntentNumber + "'.................");
                scrollPosition = presidentDetailList.size()-seeAllToDetailIntentNumber;
                Log.d("LOG>>>>>>>>.." , String.valueOf(scrollPosition));
                detailPresidentName.setText(americanPresidents.getPresident());
                if (seeAllToDetailIntentNumber == 1) {
                    detailPresidentNumber.setText(seeAllToDetailIntentNumber + "st President");
                } else if (seeAllToDetailIntentNumber == 2) {
                    detailPresidentNumber.setText(seeAllToDetailIntentNumber + "nd President");
                } else if (seeAllToDetailIntentNumber == 3) {
                    detailPresidentNumber.setText(seeAllToDetailIntentNumber + "rd President");
                } else {
                    detailPresidentNumber.setText(seeAllToDetailIntentNumber + "th President");
                }
                if(americanPresidents.getNumber()==1){
                    detailPresidentParty.setText(americanPresidents.getParty());
                }
                else{
                    detailPresidentParty.setText(americanPresidents.getParty() + " Party");
                }
                detailPresidentStartYear.setText(": " + americanPresidents.getTookOffice());
                detailPresidentEndYear.setText(": " + americanPresidents.getLeftOffice());
                detailPresidentBirthYear.setText(": " + americanPresidents.getBirthYear());
                detailPresidentDeadYear.setText(": " + americanPresidents.getDeathYear());
                // for intent value to photo view activity , assign photo url value
                photoUrl = americanPresidents.getPhotoUrl();
                if (americanPresidents.getNumber() == presidentDetailList.size()) {
                    detailPresidentEndYear.setText(": " + MyConstants.PRESENT_PRESIDENT);
                }
                if (americanPresidents.getDeathYear() == 0 | americanPresidents.getLeftOffice().equals("")) {
                    detailPresidentDeadYear.setText(": " + MyConstants.PRESENT_ALIVE);
                }
                Glide.with(this).load(americanPresidents.getPhotoUrl()).placeholder(R.drawable.placeholder_image).into(detailImageView);
            }
        }
    }

    private void photoViewIntent() {
        Intent intent = new Intent(InfoPresidentDetailActivity.this, PresidentPhotoViewActivity.class);
        intent.putExtra(MyConstants.DETAIL_INTENT_PHOTOVIEW,photoUrl);
        startActivity(intent);
    }

    /**
     * Bind View
     */
    private void bindViews() {
        detailImageView = findViewById(R.id.info_detail_image_view);
        detailPresidentName = findViewById(R.id.info_detail_name);
        detailPresidentNumber = findViewById(R.id.item_info_detail_number);
        detailPresidentStartYear = findViewById(R.id.item_info_detail_start_year);
        detailPresidentEndYear = findViewById(R.id.item_info_detail_end_year);
        detailPresidentBirthYear = findViewById(R.id.item_info_detail_birth);
        detailPresidentDeadYear = findViewById(R.id.item_info_detail_dead);
        detailPresidentParty = findViewById(R.id.item_info_detail_party);
        relativeLayout = findViewById(R.id.detail_relative_layout);
    }

    /**
     * Initialize
     */
    private void init() {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.file_photo_back_icon);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// edited here
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));
        actionBar.hide();
        actionBar.setElevation(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            callingIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callingIntent() {
        Intent intent = new Intent(InfoPresidentDetailActivity.this, InfoPresidentsSeeAllActivity.class);
        intent.putExtra(MyConstants.DETAIL_SELL_ALL_PRESIDENT_NUMBER,scrollPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
