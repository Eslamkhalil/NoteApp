package com.example.noteapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.noteapp.database.DatabaseHandler;
import com.example.noteapp.models.Encap;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;


public class Edite_NoteActivity extends AppCompatActivity {

 private EditText meditText;
 private String wdata ;
 private Encap tencap;
 private DatabaseHandler bdatabaseHandler;
 private SlidrInterface slidrInterface;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edite__note);
    bdatabaseHandler = new DatabaseHandler(Edite_NoteActivity.this);
    meditText=findViewById(R.id.editText2);
    slidrInterface  = Slidr.attach(this);
    slidrInterface.unlock();
    NewDataUpdated();


  }
  private void NewDataUpdated(){
  if(getIntent().getExtras()!=null){ {
    Bundle b=getIntent().getExtras();
    String id =b.getString("notId");//.getString();
    Log.e("ID",id+"   ");
    tencap=bdatabaseHandler .getNotById(id);
    wdata = tencap.getNote();
    meditText.setText(wdata);
    meditText.setSingleLine(false);
  }
  }

  meditText.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
     bdatabaseHandler.updateData(charSequence.toString(),tencap.getId());


    }

    @Override
    public void afterTextChanged(Editable editable) {
      if(editable.toString().isEmpty()){
        bdatabaseHandler.deletingData(tencap.getId());
      }else {
        bdatabaseHandler.updateData(editable.toString(), tencap.getId());
      }
    }
  });
}

}
