package com.example.mynotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.pojo.Note;
import com.example.mynotes.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private EditText editTitleText, editDescriptionText;
    private Button btnSaveNote;
    int origin;
    Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        myDB = new DatabaseHelper(this);
        origin = getIntent().getIntExtra(Utility.ORIGIN,-1);
//        code for setting back navigation arrow button (<--)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialize();
        getPreviousNote();

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveNote();
            }
        });
    }

    private void getPreviousNote() {
        mNote = getIntent().getExtras().getParcelable("existingNote");
        if (origin == Utility.EDIT_NOTE){
            if (mNote != null){
                editTitleText.setText(mNote.getTitle());
                editDescriptionText.setText(mNote.getDescription());
                getSupportActionBar().setTitle(mNote.getDate());
            }
        }
    }
    
    private void saveNote() {
        String noteTitle = editTitleText.getText().toString().trim();
        String noteDescription = editDescriptionText.getText().toString().trim();
        String date = new SimpleDateFormat("dd MMM, yyyy").format(new Date());

        if (TextUtils.isEmpty(noteTitle)){
            editTitleText.setError("Title cannot be empty!");
            return;
        }
        boolean isInserted = false;
        
        if (origin == Utility.NEW_NOTE){
            isInserted = myDB.insertData(noteTitle,noteDescription,date);
        } else if (origin == Utility.EDIT_NOTE){
            isInserted = myDB.updateData(mNote.getId(), noteTitle, noteDescription, date);
        }

        if (isInserted) {
            finish();
        }
        else Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();

    }

    private void initialize() {
        editTitleText = findViewById(R.id.edit_note_title);
        editDescriptionText = findViewById(R.id.edit_note_description);
        btnSaveNote = findViewById(R.id.btn_save_note);
    }
}
