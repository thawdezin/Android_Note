package com.thawdezin.note.viewholders;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.activities.CreateEditNoteActivity;
import com.thawdezin.note.data.vos.MyNote;
import com.thawdezin.note.utils.DateTimeHelper;
import com.thawdezin.note.utils.MyConstants;

import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public static int id;

    private long dateTime;
    private Date date;
    private String currentDate;
    private String currentTime;

    private TextView tvItemTitle;
    private TextView tvItemDescription;
    private TextView tvItemDate;
    private TextView tvItemTime;


    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        bindView();
    }

    private void bindView() {
        tvItemTitle = itemView.findViewById(R.id.tv_item_title);
        tvItemDescription = itemView.findViewById(R.id.tv_item_description);
        tvItemDate = itemView.findViewById(R.id.tv_item_date);
        tvItemTime = itemView.findViewById(R.id.tv_item_time);
    }

    public void bindData(final MyNote myNote, final int position) {
        tvItemTitle.setText(myNote.getTitle());
        tvItemDescription.setText(myNote.getDescription());
        dateTime = myNote.getDateTimeInstance();
        date = new Date(dateTime);
        currentDate = DateTimeHelper.getDateFormat().format(date);
        currentTime = DateTimeHelper.getTimeFormat().format(date);
        tvItemDate.setText(currentDate);
        tvItemTime.setText(currentTime);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), CreateEditNoteActivity.class);
                id = myNote.getId();
                intent.putExtra(MyConstants.ID, id);
                intent.putExtra(MyConstants.INTENT_TO_MAIN_NOTE_POSITION,position);
                intent.putExtra(MyConstants.processType, MyConstants.processTypeEdit);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            }
        });
    }
}
