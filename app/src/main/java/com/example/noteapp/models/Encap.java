package com.example.noteapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Encap implements Parcelable {


  private int id;
  private String note;
  private String timestamp;


  public Encap(){

  }

  public Encap(int id, String note, String timestamp) {
    this.id = id;
    this.note = note;
    this.timestamp = timestamp;
  }

  protected Encap(Parcel in) {
    id = in.readInt();
    note = in.readString();
    timestamp = in.readString();
  }

  public static final Creator<Encap> CREATOR = new Creator<Encap>() {
    @Override
    public Encap createFromParcel(Parcel in) {
      return new Encap(in);
    }

    @Override
    public Encap[] newArray(int size) {
      return new Encap[size];
    }
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(note);
    parcel.writeString(timestamp);
  }
}
