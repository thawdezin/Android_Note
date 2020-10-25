package com.thawdezin.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.viewholders.InfoPresidentSeeAllViewHolderGridLayout;

import java.util.ArrayList;
import java.util.List;

public class InfoPresidentSeeAllAdapterGridLayout extends RecyclerView.Adapter<InfoPresidentSeeAllViewHolderGridLayout> {
    private List<AmericanPresidents> presidentsList = new ArrayList<>();

    public InfoPresidentSeeAllAdapterGridLayout(List<AmericanPresidents> presidentsList) {
        this.presidentsList = presidentsList;
    }

    @NonNull
    @Override
    public InfoPresidentSeeAllViewHolderGridLayout onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_info_president_see_all_grid_layout, parent, false);
        return new InfoPresidentSeeAllViewHolderGridLayout(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoPresidentSeeAllViewHolderGridLayout holder, int position) {
        holder.bindData(presidentsList.get(position),presidentsList);
    }

    @Override
    public int getItemCount() {
        return presidentsList.size();
    }
}
