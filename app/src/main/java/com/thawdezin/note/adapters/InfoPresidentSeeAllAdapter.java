package com.thawdezin.note.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.viewholders.InfoPresidentSeeAllViewHolder;

import java.util.List;

public class InfoPresidentSeeAllAdapter extends RecyclerView.Adapter<InfoPresidentSeeAllViewHolder> {
    private List<AmericanPresidents> presidentsList;

    public InfoPresidentSeeAllAdapter(List<AmericanPresidents> presidentsList) {
        this.presidentsList = presidentsList;

    }

    @NonNull
    @Override
    public InfoPresidentSeeAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_info_president_see_all_view, parent, false);
        return new InfoPresidentSeeAllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoPresidentSeeAllViewHolder holder, int position) {
        holder.bindData(presidentsList.get(position), presidentsList);
    }

    @Override
    public int getItemCount() {
        return presidentsList.size();
    }
}
