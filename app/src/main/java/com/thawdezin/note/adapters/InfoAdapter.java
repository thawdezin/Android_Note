package com.thawdezin.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.viewholders.InfoViewHolder;

import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder> {
    List<AmericanPresidents> presidentsList = new ArrayList<>();

    public InfoAdapter(List<AmericanPresidents> presidentsList) {
        this.presidentsList = presidentsList;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_president_info, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        holder.bindData(presidentsList.get(position),presidentsList);
    }

    @Override
    public int getItemCount() {
        return presidentsList.size();
    }
}
