package com.thawdezin.note.viewholders;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thawdezin.note.R;
import com.thawdezin.note.activities.FilePhotoViewPagerActivity;
import com.thawdezin.note.data.vos.MyFile;
import com.thawdezin.note.utils.MyConstants;

public class FileViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public FileViewHolder(@NonNull View itemView) {
        super(itemView);
        bindViews();
    }

    private void bindViews() {
        imageView = itemView.findViewById(R.id.iv_file);
    }

    public void bindData(MyFile myFile, final int position) {
        Glide.with(this.itemView).load(myFile.getImageUri()).placeholder(R.drawable.placeholder_image).into(imageView);
        Log.d("myFileImageUri", myFile.getImageUri() + "ViewHolder>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), FilePhotoViewPagerActivity.class);
                intent.putExtra(MyConstants.FILE_POSITION_POSITION, position);
                v.getContext().startActivity(intent);
            }
        });
    }

}
