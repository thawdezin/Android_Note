package com.thawdezin.note.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.thawdezin.note.R;
import com.thawdezin.note.activities.FilePhotoViewPagerActivity;
import com.thawdezin.note.data.vos.MyFile;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class FilePhotoViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<MyFile> filePhotoList;
    private static boolean actionBar = true;
    public FilePhotoViewPagerAdapter(Context context, List<MyFile> feedItemList) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.filePhotoList = feedItemList;
    }

    @Override
    public int getCount() {
        return (null != filePhotoList ? filePhotoList.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.item_file_photo_view, container, false);

        MyFile file = filePhotoList.get(filePhotoList.size()-1-position);

        PhotoView photoView = itemView.findViewById(R.id.photo_view);
        Glide
                .with(this.mContext)
                .load(file.getImageUri())
                .into(photoView);

        container.addView(itemView);
        //FilePhotoViewPagerActivity.actionBar.hide();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionBar){
                    FilePhotoViewPagerActivity.actionBar.show();
                    Log.d(">>>>>", ">>>>>>>>>>>>>>:touch Container");
                    actionBar = false;
                }
                else {
                    FilePhotoViewPagerActivity.actionBar.hide();
                    actionBar = true;
                }
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
