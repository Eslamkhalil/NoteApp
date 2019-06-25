package com.example.noteapp;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.noteapp.adapters.NotesAdapter;
import com.example.noteapp.adapters.NotesAdapter.OnNoteListener;
import com.example.noteapp.adapters.NotesAdapter.OnNoteLongeClickListener;
import com.example.noteapp.database.DatabaseHandler;
import com.example.noteapp.models.Encap;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {

  DatabaseHandler mdatabaseHandler;
  NotesAdapter mnotesAdapter;
  RecyclerView recyclerView;
  ArrayList<Encap> listData6 = new ArrayList<>();
  TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Ini();

    listData6 = mdatabaseHandler.getAllData();
    mnotesAdapter.SetDataSource(listData6);
  }


  private void Ini() {
    mdatabaseHandler = new DatabaseHandler(MainActivity.this);
    mnotesAdapter = new NotesAdapter(listData6,this);
    textView = findViewById(R.id.textView);
    initRecyclerView();
  }


  private boolean isTherData() {
    return listData6.size() > 0;
  }

  public void fbOnClick(View view) {
    insertData();
  }

  private void initRecyclerView() {

    recyclerView = findViewById(R.id.recy_view);
    recyclerView.setAdapter(mnotesAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    deleteItem();
  }

  private void insertData() {

    AlertDialog.Builder builder = new Builder(this);
    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    View view = inflater.inflate(R.layout.add_new_note, null);
    builder.setView(view);
    final AlertDialog dialog = builder.create();
    final EditText txt_note = view.findViewById(R.id.add_new_note);
    Button save = view.findViewById(R.id.btn_add_save);
    save.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String ssdata = txt_note.getText().toString().trim();

        if (ssdata.isEmpty()) {
          txt_note.setError("This field can not be blank");
        } else {
          if (mdatabaseHandler.Insert_Data(ssdata)) {
            Toast.makeText(MainActivity.this, "Data Insert", Toast.LENGTH_SHORT).show();
            listData6 = mdatabaseHandler.getAllData();
            if (isTherData()) {
              textView.setVisibility(View.GONE);
            }
            mnotesAdapter.SetDataSource(listData6);
            dialog.dismiss();

          }

        }
      }
    });
    dialog.show();
  }

  private void deleteItem() {
    mnotesAdapter.onLongListner(new OnNoteLongeClickListener() {
      @Override
      public void OnLongCLick(final int po) {
        Snackbar.make(recyclerView, "هل تريد الحذف حقا فعلا احلف ؟", Snackbar.LENGTH_LONG)
            .setAction("ايوة والله", new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                mdatabaseHandler.deletingData(listData6.get(po).getId());
                listData6 = mdatabaseHandler.getAllData();
                if (!isTherData()) {
                  textView.setVisibility(View.VISIBLE);
                  textView.setText("There's No Data , push & Add");
                  Toast.makeText(MainActivity.this, "There's No Data", Toast.LENGTH_SHORT).show();
                }
                mnotesAdapter.SetDataSource(listData6);

              }
            }).show();
      }
    });
  }

  @Override
  public void OnNoteClick(int pos) {
    Log.e("frist", String.valueOf(pos));
    Send(pos);

  }


  private void Send(int pos) {
    Intent intent = new Intent(this, Edite_NoteActivity.class);
    Bundle b = new Bundle();
    b.putString("notId", listData6.get(pos).getId() + "");
    intent.putExtras(b);
    startActivity(intent);
  }

  @Override
  protected void onResume() {
    super.onResume();
    listData6 = mdatabaseHandler.getAllData();
    if (mnotesAdapter != null) {
      mnotesAdapter.SetDataSource(listData6);
    }


  }
}
