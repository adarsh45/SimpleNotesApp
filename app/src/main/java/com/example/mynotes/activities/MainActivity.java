package com.example.mynotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.adapters.NoteAdapter;
import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.pojo.Note;
import com.example.mynotes.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener{

    private RecyclerView rvNotes;
    private FloatingActionButton fab;
    
    private DatabaseHelper myDB;
    public NoteAdapter adapter;
    ArrayList<Note> myNotes = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();

        fetchData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        myDB = new DatabaseHelper(this);
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra(Utility.ORIGIN, Utility.NEW_NOTE);
                startActivity(intent);
            }
        });

    }

    private void fetchData() {
        Cursor c = myDB.getAllData();
        myNotes.clear();
//        check if DB has some data
        if (c.getCount() >=1){
            while (c.moveToNext()){
                Note note = new Note();
                note.setId(c.getString(0));
                note.setTitle(c.getString(1));
                note.setDescription(c.getString(2));
                note.setDate(c.getString(3));
                myNotes.add(note);
            }

            rvNotes.setLayoutManager(new LinearLayoutManager(this));
            adapter = new NoteAdapter(myNotes, this);
            adapter.notifyDataSetChanged();
            rvNotes.setAdapter(adapter);

        }else {
//            if DB has NO data
            Toast.makeText(this, "Database is Empty", Toast.LENGTH_SHORT).show();
        }
        c.close();
    }

    private void initialize() {
        rvNotes = findViewById(R.id.rv_notes);
        fab = findViewById(R.id.add_note_fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra(Utility.ORIGIN, Utility.EDIT_NOTE);
        intent.putExtra("existingNote", myNotes.get(position));
        startActivity(intent);
    }
}
