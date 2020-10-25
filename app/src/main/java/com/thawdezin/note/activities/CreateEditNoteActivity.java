package com.thawdezin.note.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.thawdezin.note.R;
import com.thawdezin.note.data.db.MyNoteDBAccess;
import com.thawdezin.note.data.vos.MyNote;
import com.thawdezin.note.utils.DateTimeHelper;
import com.thawdezin.note.utils.MyConstants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import io.realm.Realm;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CreateEditNoteActivity extends AppCompatActivity {
    public static Realm noteRealm;
    public static String currentDate;
    public static String currentTime;
    public static java.util.Date date;
    public static long millis;

    /* Views */
    FloatingActionButton mFloatingActionButton;
    EditText etTitle;
    EditText etDescription;

    Intent intent;
    int code;
    MyNote noteIdResult;
    String mainTitle;
    String mainDescription;
    int intentToMainNotePosition;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit_layout);
        bindViews();
        init();
        intentToMainNotePosition = getIntent().getIntExtra(MyConstants.INTENT_TO_MAIN_NOTE_POSITION, 0);
        mFloatingActionButton = findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(actionBar.getTitle()).equals("Create Note")) {
                    if ((etTitle.getText().toString().isEmpty()) || (etDescription.getText().toString().isEmpty())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle(MyConstants.warning);
                        builder.setMessage(MyConstants.dialog_title);
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                dialog.dismiss();
                            }
                        });
                        builder.setIcon(R.drawable.ic_warning);
                        builder.show();
                    } else {
                        millis = System.currentTimeMillis();
                        mainTitle = etTitle.getText().toString();
                        mainDescription = etDescription.getText().toString();

                        /**
                         * New Note Create Method
                         */
                        MyNoteDBAccess.createNote(millis, mainTitle, mainDescription);
                        MyNoteDBAccess.closeReam();
                        callingIntent();
                    }
                } else {
                    millis = System.currentTimeMillis();
                    mainTitle = etTitle.getText().toString();
                    mainDescription = etDescription.getText().toString();

                    /**
                     * Calling Realm Note Edit Method
                     */
                    MyNoteDBAccess.editNote(noteIdResult, millis, mainTitle, mainDescription);
                    MyNoteDBAccess.closeReam();
                    callingIntent();

                }
            }
        });
        gettingID();
    }

    private void callingIntent() {
        intent = new Intent(CreateEditNoteActivity.this, MainActivity.class);
        intent.putExtra(MyConstants.INTENT_TO_MAIN_NOTE_POSITION, intentToMainNotePosition);
        intent.putExtra(MyConstants.INTENT_TO_MAIN, R.id.bn_note);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * View Binding Method
     */
    private void bindViews() {
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
    }

    /**
     * Initialize
     */
    private void init() {
        millis = System.currentTimeMillis();
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);// set drawable icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        noteRealm = Realm.getDefaultInstance();
        date = new java.util.Date(millis);
        currentTime = DateTimeHelper.getDateFormat().toString();
        currentDate = DateTimeHelper.getTimeFormat().toString();
        Log.d("Date", currentDate);
        Log.d("Time", currentTime);
    }

    /**
     * Getting Note Intent ID and Process Type
     */
    private void gettingID() {
        String processType = getIntent().getStringExtra(MyConstants.processType);
        assert processType != null;
        switch (processType) {
            case MyConstants.processTypeCreate:
                actionBar.setTitle(R.string.create_note);
                break;
            case MyConstants.processTypeEdit:
                code = getIntent().getIntExtra(MyConstants.ID, 0);
                actionBar.setTitle(R.string.edit_note);

                /**
                 * Getting Note Id from Realm
                 */
                noteIdResult = MyNoteDBAccess.getNoteId(code);
                if (noteIdResult.getTitle() != null && noteIdResult.getDescription() != null) {
                    etTitle.setText(noteIdResult.getTitle());
                    etDescription.setText(noteIdResult.getDescription());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + processType);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Objects.requireNonNull(actionBar.getTitle()).equals(MyConstants.processTypeEdit)) {
            getMenuInflater().inflate(R.menu.note_action_bar_delete_menu, menu);
        }
        return true;
    }

    /**
     * Back and Delete Button Event
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                callingIntent();
                finish();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Exit");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        /**
                         * Calling Note Delete Method from Realm
                         */
                        MyNoteDBAccess.deleteNote(noteIdResult.getId());
                        callingIntent();

                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.setIcon(R.drawable.ic_warning);// Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                // A null listener allows the button to dismiss the dialog and take no further action.
                builder.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

