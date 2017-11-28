package com.wollon.tourgass.activity.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.base.BaseActivity;
import com.wollon.tourgass.dto.Note;

import java.util.Date;
import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/28 0028.
 */

public class NoteEditActivity extends BaseActivity{
    private static final String EXTRA_NOTE_ID="com.wollon.tourgass.note_id";
    private EditText titleEditText;
    private EditText detailsEditText;
    private Button submitButton;
    private UUID noteId;
    private Note note;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_edit);

        titleEditText=(EditText) findViewById(R.id.note_title);
        detailsEditText=findViewById(R.id.note_details);
        submitButton=(Button) findViewById(R.id.note_submit);
        noteId=(UUID) getIntent().getSerializableExtra(EXTRA_NOTE_ID);
        note=NoteLab.get(this).getNote(noteId);

        titleEditText.setText(note.getTitle());
        detailsEditText.setText(note.getDetails());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setTitle(titleEditText.getText().toString());
                note.setDetails(detailsEditText.getText().toString());
                note.setTime(new Date());
                finish();
            }
        });

    }

    public static Intent newIntent(Context packageContext, UUID planId){
        Intent intent=new Intent(packageContext,NoteEditActivity.class);
        intent.putExtra(EXTRA_NOTE_ID,planId);

        return intent;
    }



}
