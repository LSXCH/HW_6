package com.byted.camp.todolist.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.byted.camp.todolist.NoteOperator;
import com.byted.camp.todolist.R;
import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created on 2019/1/23.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);

    private final NoteOperator operator;

    private CheckBox checkBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;

    public NoteViewHolder(@NonNull View itemView, NoteOperator operator) {
        super(itemView);
        this.operator = operator;

        checkBox = itemView.findViewById(R.id.checkbox);
//        checkBox.setBackgroundColor(Color.WHITE);
        contentText = itemView.findViewById(R.id.text_content);
        dateText = itemView.findViewById(R.id.text_date);
        deleteBtn = itemView.findViewById(R.id.btn_delete);
    }

    public void bind(final Note note) {
        contentText.setText(note.getContent());
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(note.getState() == State.DONE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                note.setState(isChecked ? State.DONE : State.TODO);
                operator.updateNote(note);
            }
        });

        if(note.getState() == State.DONE)
        {
            contentText.setTextColor(Color.GRAY);
//            contentText.setPaintFlags(contentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); //添加删除线
            contentText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //尝试另一种添加和去掉删除线的方式
        }
        else
        {
            contentText.setTextColor(Color.BLACK);
            contentText.getPaint().setFlags(0);
            contentText.invalidate();
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operator.deleteNote(note);   //执行删除方法
            }
        });
        itemView.setBackgroundColor(note.getPriority().color);
    }
}
