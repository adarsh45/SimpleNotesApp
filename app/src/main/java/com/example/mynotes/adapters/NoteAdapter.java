package com.example.mynotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.pojo.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    ArrayList<Note> mNotes;
    OnNoteListener mOnNoteListener;

    public NoteAdapter(ArrayList<Note> notes, OnNoteListener onNoteListener){
        this.mNotes = notes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note,parent,false);
        return new NoteViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        final Note note = mNotes.get(position);
        holder.noteTitleTextView.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView noteTitleTextView;
        public OnNoteListener onNoteListener;

        public NoteViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.noteTitleTextView = itemView.findViewById(R.id.note_title_text_view);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
