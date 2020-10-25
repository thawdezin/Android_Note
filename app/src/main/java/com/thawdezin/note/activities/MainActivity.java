package com.thawdezin.note.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.thawdezin.note.R;
import com.thawdezin.note.fragment.FragmentFile;
import com.thawdezin.note.fragment.FragmentInfo;
import com.thawdezin.note.fragment.FragmentNote;
import com.thawdezin.note.utils.MyConstants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static BottomNavigationView bottomNavigationView;
    private static FloatingActionButton floatingActionButton;
    private static String currentFragment;
    boolean doubleBackToExitPressedOnce;
    Fragment fragment;
    private Toolbar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bn_note:
                    toolbar.setTitle(R.string.note);
                    floatingActionButton.show();
                    floatingActionButton.setImageResource(R.drawable.ic_fab_note);
                    fragment = new FragmentNote();
                    loadFragment(fragment);
                    return true;
                case R.id.bn_file:
                    toolbar.setTitle(R.string.file);
                    floatingActionButton.setAdjustViewBounds(true);
                    floatingActionButton.setImageResource(R.drawable.ic_fab_file);
                    fragment = new FragmentFile();
                    floatingActionButton.show();
                    loadFragment(fragment);
                    return true;
                case R.id.bn_info:
                    toolbar.setTitle(R.string.info);
                    fragment = new FragmentInfo();
                    loadFragment(fragment);
                    floatingActionButton.hide();
                    return true;
            }
            return false;
        }
    };


    public MainActivity() {
        doubleBackToExitPressedOnce = false;
    }

    public static BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public static FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public static String getCurrentFragment() {
        return currentFragment;
    }

    public static void setCurrentFragment(String currentFragment) {
        MainActivity.currentFragment = currentFragment;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindViews();

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new FragmentNote());
        bottomNavigationView.setSelectedItemId(R.id.bn_note);
        int mainCurrent = getIntent().getIntExtra(MyConstants.INTENT_TO_MAIN, 0);
        int filePosition = getIntent().getIntExtra(MyConstants.INTENT_TO_MAIN_FILE_POSITION,0);
        int notePosition = getIntent().getIntExtra(MyConstants.INTENT_TO_MAIN_NOTE_POSITION,0);
        Log.d("notePosition" , String.valueOf(notePosition));
        switch (mainCurrent) {
            case R.id.bn_file:
                loadFragment(new FragmentFile());
                bottomNavigationView.setSelectedItemId(R.id.bn_file);
                FragmentFile.setFilePosition(filePosition);
                break;
            case R.id.bn_info:
                loadFragment(new FragmentInfo());
                bottomNavigationView.setSelectedItemId(R.id.bn_info);
                break;
            case R.id.bn_note:
                loadFragment(new FragmentNote());
                bottomNavigationView.setSelectedItemId(R.id.bn_note);
                FragmentNote.setNotePosition(notePosition);
        }
    }


    /**
     * Bind View
     */
    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        floatingActionButton = findViewById(R.id.fab);
    }

    /**
     * Initialize
     */
    private void init() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.equals(getCurrentFragment(), MyConstants.currentNote)) {
            if (doubleBackToExitPressedOnce) {
                MainActivity.this.finish();
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals(getCurrentFragment(), MyConstants.currentFile)) {
            bottomNavigationView.setSelectedItemId(R.id.bn_note);

        } else if (TextUtils.equals(getCurrentFragment(), MyConstants.currentInfo)) {
            bottomNavigationView.setSelectedItemId(R.id.bn_file);
        } else {
            Toast.makeText(getApplicationContext(), "NO ELSE case", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



