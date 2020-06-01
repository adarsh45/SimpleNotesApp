package com.example.mynotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.pojo.Note;
import com.example.mynotes.util.Utility;

import java.util.Objects;

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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

//    called from onCreate and Fetches data if note is already there
    private void getPreviousNote() {
        mNote = Objects.requireNonNull(getIntent().getExtras()).getParcelable("existingNote");
        if (origin == Utility.EDIT_NOTE){
            if (mNote != null){
                editTitleText.setText(mNote.getTitle());
                editDescriptionText.setText(mNote.getDescription());
                Objects.requireNonNull(getSupportActionBar()).setTitle(mNote.getDate());
            }
        }
    }

//    onClick method of Save button
    private void saveNote() {
        String noteTitle = editTitleText.getText().toString().trim();
        String noteDescription = editDescriptionText.getText().toString().trim();
        String date = Utility.getCurrentDate();

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

//    init views with findViewById() method
    private void initialize() {
        editTitleText = findViewById(R.id.edit_note_title);
        editDescriptionText = findViewById(R.id.edit_note_description);
        btnSaveNote = findViewById(R.id.btn_save_note);
    }

    //menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu
                ,menu);
        return true;
    }

//    delete menu onClick code
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        if (item.getItemId() == R.id.menu_delete){
            if(mNote != null){
                myDB.deleteRecord(mNote.getId());
            }
            finish();
        }
        
        return super.onOptionsItemSelected(item);
    }
}
