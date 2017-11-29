package com.wollon.tourgass.activity.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wollon.tourgass.R;
import com.wollon.tourgass.activity.base.BaseActivity;
import com.wollon.tourgass.dto.Note;
import com.wollon.tourgass.util.ToolBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/28 0028.
 */

public class NoteListActivity extends BaseActivity{
    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private List<Note> list;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_list);
        View listToolbarView = findViewById(R.id.note_list_toolbar);
        toolbar=(Toolbar) listToolbarView.findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        fab=(FloatingActionButton) findViewById(R.id.note_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context,NoteAddActivity.class);
                startActivity(intent);
            }
        });

        noteRecyclerView=(RecyclerView) findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ToolBarUtil.CreateToolBarMenu(this,menu,R.menu.list_toolbar);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_delete:
                Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
                list=NoteLab.get(this).getNote();
                List<UUID> indexList=new ArrayList();
                for(Note note:list){
                    if(note.isSolved()){
                        indexList.add(note.getId());
                    }
                }

                for(int i=0;i<indexList.size();i++){
                    list.remove(NoteLab.get(this).getNote(indexList.get(i)));
                }

                updateUI();
                break;
            default:
                Toast.makeText(this,"default",Toast.LENGTH_SHORT).show();

                break;
        }

        return true;
    }

    //设置Adapter
    private void updateUI() {
        //获取Plan对象
        NoteLab noteLab = NoteLab.get(this);
        list = noteLab.getNote();
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(list);
            noteRecyclerView.setAdapter(noteAdapter);
        } else {
            noteAdapter.notifyDataSetChanged();
        }
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Note mpNote;

        private TextView titleTextView;
        private TextView timeTextView;
        private CheckBox solvedCheckBox;

        public NoteHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            titleTextView = (TextView) itemView.findViewById(R.id.list_item_note_textView_title);
            timeTextView = (TextView) itemView.findViewById(R.id.list_item_note_textView_time);
            solvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_note_checkbox);

            solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        mpNote.setSolved(b);
                    }
                }
            });
        }

        //将Holder中的Plan数据在组件中展示
        public void bindNote(Note note) {
            mpNote = note;
            titleTextView.setText(mpNote.getTitle());
            timeTextView.setText(mpNote.getTime().toString());
            solvedCheckBox.setChecked(mpNote.isSolved());
        }

        @Override
        public void onClick(View view) {
            Intent intent = NoteEditActivity.newIntent(context, mpNote.getId());
            startActivity(intent);
        }
    }


    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> noteList;

        public NoteAdapter(List<Note> list) {
            noteList = list;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.list_item_note, parent, false);

            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            Note note = noteList.get(position);
            //数据绑定
            holder.bindNote(note);
        }

        @Override
        public int getItemCount() {
            return noteList.size();
        }
    }
}
