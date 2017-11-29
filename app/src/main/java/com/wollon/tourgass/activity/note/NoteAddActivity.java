package com.wollon.tourgass.activity.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.base.BaseActivity;
import com.wollon.tourgass.dto.Note;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/28 0028.
 */

public class NoteAddActivity extends BaseActivity {
    private EditText titleEditText;
    private EditText detailsEditText;
    private Button submitButton;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_edit);

        titleEditText=(EditText) findViewById(R.id.note_title);
        detailsEditText=(EditText) findViewById(R.id.note_details);
        submitButton=(Button) findViewById(R.id.note_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note=new Note();
                note.setId(UUID.randomUUID());
                note.setSolved(false);
                note.setTitle(titleEditText.getText().toString());
                note.setDetails(detailsEditText.getText().toString());
                note.setTime(new Date());
                NoteLab noteLab=NoteLab.get(context);
                List<Note> list=noteLab.getNote();
                list.add(note);
                finish();
            }
        });
    }
}
