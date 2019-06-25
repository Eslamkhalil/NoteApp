package com.example.noteapp.adapters;

import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.noteapp.R;
import com.example.noteapp.models.Encap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

 private List<Encap> list;

 private OnNoteListener mOnNoteListener;
  private OnNoteLongeClickListener longeClickListener;

  public NotesAdapter(List<Encap> list,OnNoteListener onNoteListener) {
    this.list = list;

    this.mOnNoteListener=onNoteListener;
  }


  public NotesAdapter(OnNoteListener onNoteListener) {

    this.mOnNoteListener=onNoteListener;
  }
  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row,viewGroup,false),mOnNoteListener);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
    Encap e = list.get(i);
    myViewHolder.bTime.setText(formatDate(e.getTimestamp()));
    myViewHolder.bNote.setText(e.getNote());

  }

  @Override
  public int getItemCount() {
    return list.size();
  }



  public void SetDataSource(List<Encap> list){
    this.list=list;
    notifyDataSetChanged();
  }




  public  void onLongListner(OnNoteLongeClickListener longeClickListener){
   this . longeClickListener=longeClickListener;
  }

  private String formatDate(String dateStr) {
    try {
      SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = fmt.parse(dateStr);
      SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
      return fmtOut.format(date);
    } catch (ParseException e) {

    }

    return "";
  }

  public class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener,
      OnLongClickListener {

    TextView bTime,bNote;
    CardView cardView;
    OnNoteListener onNoteListener;
    private MyViewHolder(@NonNull View itemView , OnNoteListener onNoteListener)  {
      super(itemView);
      bTime=itemView.findViewById(R.id.txt_time);
      bNote=itemView.findViewById(R.id.txt_note);
      cardView=itemView.findViewById(R.id.show_card);
      this.onNoteListener=onNoteListener;
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
      onNoteListener.OnNoteClick(getAdapterPosition());



    }


    @Override
    public boolean onLongClick(View view) {
      longeClickListener.OnLongCLick(getAdapterPosition());
      return true;
    }
  }
  public interface OnNoteListener{
    void OnNoteClick(int pos);
  }


  public interface OnNoteLongeClickListener{
    void OnLongCLick(int pos);
  }
}
