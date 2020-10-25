package com.thawdezin.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.viewholders.NoteViewHolder;

import java.util.List;

public class NoteAdapter<MyNote> extends RecyclerView.Adapter<NoteViewHolder> {
    private List<MyNote> mNoteList;

    public NoteAdapter(@NonNull List<MyNote> mNoteList) {
        this.mNoteList = mNoteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindData((com.thawdezin.note.data.vos.MyNote) mNoteList.get(position),position);
    }

    //    public void setmNoteList(List mNoteList) {
//        this.mNoteList = mNoteList;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
