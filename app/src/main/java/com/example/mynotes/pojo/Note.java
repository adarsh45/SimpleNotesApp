package com.example.mynotes.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String id, noteText, date;

   public Note(){}

    public Note(String id, String noteText, String date) {
        this.id = id;
        this.noteText = noteText;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.noteText);
        dest.writeString(this.date);
    }

    protected Note(Parcel in) {
        this.id = in.readString();
        this.noteText = in.readString();
        this.date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
