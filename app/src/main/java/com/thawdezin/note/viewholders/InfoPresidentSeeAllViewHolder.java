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

public class InfoPresidentSeeAllViewHolder extends RecyclerView.ViewHolder {
    private ImageView infoSeeAllImageView;
    private TextView infoSeeAllPresidentName;
    private TextView infoSeeAllPresidentStartYear;
    private TextView infoSeeAllPresidentEndYear;
    private TextView infoSeeAllPresidentParty;

    public InfoPresidentSeeAllViewHolder(@NonNull final View itemView) {
        super(itemView);
        infoSeeAllImageView = itemView.findViewById(R.id.iv_item_info_see_all_president);
        infoSeeAllPresidentName = itemView.findViewById(R.id.item_info_president_see_all_name);
        infoSeeAllPresidentStartYear = itemView.findViewById(R.id.item_info_president_see_all_start_year);
        infoSeeAllPresidentEndYear = itemView.findViewById(R.id.item_info_president_see_all_end_year);
        infoSeeAllPresidentParty = itemView.findViewById(R.id.item_info_president__see_all_party);

    }
    public void bindData(final AmericanPresidents americanPresidents, List<AmericanPresidents> americanPresidentsList){
        infoSeeAllPresidentName.setText(americanPresidents.getPresident());
        infoSeeAllPresidentStartYear.setText(americanPresidents.getTookOffice());
        infoSeeAllPresidentEndYear.setText(americanPresidents.getLeftOffice());
        if(americanPresidents.getNumber() == americanPresidentsList.size()){
            infoSeeAllPresidentEndYear.setText(MyConstants.PRESENT_PRESIDENT);
        }
        int newWidth = 300; // New width in pixels
        int newHeight = 400; // New height in pixels
        infoSeeAllImageView.requestLayout();
        // Apply the new height for ImageView programmatically
        infoSeeAllImageView.getLayoutParams().height = newHeight;
        // Apply the new width for ImageView programmatically
        infoSeeAllImageView.getLayoutParams().width = newWidth;
        // Set the scale type for ImageView image scaling
        infoSeeAllImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        infoSeeAllPresidentParty.setText(americanPresidents.getParty());
        Glide.with(this.itemView).load(americanPresidents.getPhotoUrl()).placeholder(R.drawable.ic_action_loading).into(infoSeeAllImageView);
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
