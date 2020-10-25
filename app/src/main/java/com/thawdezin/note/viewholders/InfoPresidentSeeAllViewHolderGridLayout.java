package com.thawdezin.note.viewholders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thawdezin.note.R;
import com.thawdezin.note.activities.InfoPresidentDetailActivity;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.utils.MyConstants;

import java.util.List;

public class InfoPresidentSeeAllViewHolderGridLayout extends RecyclerView.ViewHolder {
    private ImageView presidentImageView;
    private TextView presidentName;
    private TextView presidentStartYear;
    private TextView presidentEndYear;
    private TextView presidentParty;
    public InfoPresidentSeeAllViewHolderGridLayout(@NonNull View itemView) {
        super(itemView);
        presidentImageView = itemView.findViewById(R.id.iv_item_info_president_grid_layout);
        presidentName = itemView.findViewById(R.id.item_info_president_name_grid_layout);
        presidentStartYear = itemView.findViewById(R.id.item_info_president_start_year_grid_layout);
        presidentEndYear = itemView.findViewById(R.id.item_info_president_end_year_grid_layout);
        presidentParty = itemView.findViewById(R.id.item_info_president_party_grid_layout);

    }
    public void bindData(final AmericanPresidents americanPresidents, List<AmericanPresidents> americanPresidentsList){
        presidentName.setText(americanPresidents.getPresident());
        presidentStartYear.setText(americanPresidents.getTookOffice());
        presidentEndYear.setText(americanPresidents.getLeftOffice());
        presidentParty.setText(americanPresidents.getParty());
        if(americanPresidents.getNumber() == americanPresidentsList.size()){
            presidentEndYear.setText(MyConstants.PRESENT_PRESIDENT);
        }
        int newWidth = 300; // New width in pixels
        int newHeight = 400; // New height in pixels
        presidentImageView.requestLayout();
        // Apply the new height for ImageView programmatically
        presidentImageView.getLayoutParams().height = newHeight;
        // Apply the new width for ImageView programmatically
        presidentImageView.getLayoutParams().width = newWidth;
        // Set the scale type for ImageView image scaling
        presidentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this.itemView).load(americanPresidents.getPhotoUrl()).placeholder(R.drawable.ic_action_loading).into(presidentImageView);
        final int presidentNumber = americanPresidents.getNumber();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), InfoPresidentDetailActivity.class);
                intent.putExtra(MyConstants.INFO_DETAIL_INTENT,presidentNumber);
                v.getContext().startActivity(intent);
            }
        });
    }
}
