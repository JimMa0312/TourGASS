package com.wollon.tourgass.activity.note;

import android.content.Context;

import com.wollon.tourgass.dto.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/28 0028.
 */

public class NoteLab {
    private static NoteLab noteLab;
    private List<Note> noteList;

    public static NoteLab get(Context context){
        if(noteLab ==null){
            noteLab=new NoteLab(context);
        }
        return noteLab;
    }

    private NoteLab(Context context){
        noteList=new ArrayList<>();
        for(int i=0;i<50;i++){
            Note note=new Note();
            note.setTitle("日记#"+(i+1));
            note.setSolved(i%3==0);
            noteList.add(note);
        }
    }

    public List<Note> getNote() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public Note getNote(UUID uuid){
        for(Note note:noteList){
            if(note.getId().equals(uuid)){
                return note;
            }
        }
        return null;
    }
}
