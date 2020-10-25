package com.thawdezin.note.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.activities.CreateEditNoteActivity;
import com.thawdezin.note.activities.MainActivity;
import com.thawdezin.note.adapters.NoteAdapter;
import com.thawdezin.note.data.db.MyNoteDBAccess;
import com.thawdezin.note.data.vos.MyNote;
import com.thawdezin.note.utils.MyConstants;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class FragmentNote extends BaseFragment {

    private static int notePosition = 0;
    private static boolean flagForRecyclerViewScroll = false;
    private RecyclerView recyclerView;
    NoteAdapter adapter;

    public static int getNotePosition() {
        return notePosition;
    }

    public static void setNotePosition(int notePosition) {
        FragmentNote.notePosition = notePosition;
    }

    @Override
    public void onStart() {
        super.onStart();

        MainActivity.getFloatingActionButton().show();
        MainActivity.getFloatingActionButton().setImageResource(R.drawable.ic_fab_note);

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setCurrentFragment(MyConstants.currentNote);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        MainActivity.getFloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateEditNoteActivity.class);
                intent.putExtra(MyConstants.processType, MyConstants.processTypeCreate);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        List<MyNote> results = MyNoteDBAccess.getAllNote();
        adapter = new NoteAdapter<>(results);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        if (notePosition != 0) {
            flagForRecyclerViewScroll = true;
        }
        if (flagForRecyclerViewScroll) {
            recyclerView.scrollToPosition(getNotePosition());
        } else {
            recyclerView.scrollToPosition(0);
        }
        if(adapter.getItemCount()<= 3 ){
            MainActivity.getBottomNavigationView().setVisibility(View.VISIBLE);
        }
        if(MainActivity.getBottomNavigationView().getVisibility()==View.INVISIBLE){
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 66, 286);
            MainActivity.getFloatingActionButton().setLayoutParams(layoutParams);
        }
        recyclerViewScroll();

        return view;
    }

    private void recyclerViewScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && MainActivity.getBottomNavigationView().isShown()) {
                    MainActivity.getBottomNavigationView().setVisibility(View.GONE);
                } else if (dy < 0) {
                    MainActivity.getBottomNavigationView().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    int getLayoutId() {
        return R.layout.note_fragment;
    }

    @Override
    void onViewReady(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

}
