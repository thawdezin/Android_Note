package com.thawdezin.note.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thawdezin.note.R;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.utils.MyConstants;

import java.util.List;

public class InfoViewHolder extends RecyclerView.ViewHolder {
    private ImageView presidentImageView;
    private TextView presidentName;
    private TextView presidentStartYear;
    private TextView presidentEndYear;

    public InfoViewHolder(@NonNull View itemView) {
        super(itemView);
        presidentImageView = itemView.findViewById(R.id.iv_item_info_president);
        presidentName = itemView.findViewById(R.id.item_info_president_name);
        presidentStartYear = itemView.findViewById(R.id.item_info_president_start_year);
        presidentEndYear = itemView.findViewById(R.id.item_info_president_end_year);
    }

    public void bindData(AmericanPresidents americanPresidents, List<AmericanPresidents> americanPresidentsList) {
        presidentName.setText(americanPresidents.getPresident());
        presidentStartYear.setText(americanPresidents.getTookOffice());
        presidentEndYear.setText(americanPresidents.getLeftOffice());
        if (americanPresidents.getNumber() == americanPresidentsList.size()) {
            presidentEndYear.setText(MyConstants.PRESENT_PRESIDENT);
        }
        int newWidth = 180; // New width in pixels
        int newHeight = 280; // New height in pixels
        presidentImageView.requestLayout();
        // Apply the new height for ImageView programmatically
        presidentImageView.getLayoutParams().height = newHeight;
        // Apply the new width for ImageView programmatically
        presidentImageView.getLayoutParams().width = newWidth;
        // Set the scale type for ImageView image scaling
        presidentImageView.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(this.itemView).load(americanPresidents.getPhotoUrl()).placeholder(R.drawable.ic_action_loading).into(presidentImageView);
    }
}
