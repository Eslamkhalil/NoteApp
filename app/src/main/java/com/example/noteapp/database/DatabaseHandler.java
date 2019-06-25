package com.example.noteapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.noteapp.models.Encap;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

  private static final String COLUMN_TIMESTAMP = "timestamp";
  private static final String dbName = "Notes";
  private static final String tableName = "note";
  private static final int dbVersaion = 2;
  private static final String UID = "id";
  private static final String note = "addNote";
  private static final String CREATE_TABLE = "CREATE TABLE " + tableName +
      " ("
      + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + note + " VARCHAR(255),"
      + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
      + ")";
  private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + tableName;

  private Context context;
  private SQLiteDatabase sqLiteDatabase;

  public DatabaseHandler(Context context) {
    super(context, dbName, null, dbVersaion);
    this.context = context;
    sqLiteDatabase = this.getReadableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    db.execSQL(CREATE_TABLE);
    Toast.makeText(context, "Table was Created", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(DROP_TABLE);
    onCreate(db);
    Toast.makeText(context, "Table was Dropped", Toast.LENGTH_SHORT).show();
  }


  public boolean Insert_Data(String str) {
    sqLiteDatabase = this.getReadableDatabase();
    ContentValues values = new ContentValues();
    values.put(note, str);

    long id = sqLiteDatabase.insert(tableName, null, values);

    sqLiteDatabase.close();

    return id > -1;
  }

  public ArrayList<Encap> getAllData() {
    sqLiteDatabase = this.getReadableDatabase();
    ArrayList<Encap> encapArrayList = new ArrayList<>();

    Cursor curs = sqLiteDatabase
        .rawQuery("SELECT * FROM " + tableName + " ORDER BY " + COLUMN_TIMESTAMP + " DESC", null);

    curs.moveToFirst();

    while (!curs.isAfterLast()) {
      Encap encap5 = new Encap();

      encap5.setId(curs.getInt(curs.getColumnIndex(UID)));
      encap5.setNote(curs.getString(curs.getColumnIndex(note)));
      encap5.setTimestamp(curs.getString(curs.getColumnIndex(COLUMN_TIMESTAMP)));
      encapArrayList.add(encap5);
      curs.moveToNext();
    }
    curs.close();

    return encapArrayList;
  }

  public void updateData(String str, Integer id) {
    sqLiteDatabase = this.getWritableDatabase();
    sqLiteDatabase.execSQL("Update note set addNote ='" + str + "' where id=" + (id));

    sqLiteDatabase.close();
  }

  public void deletingData(int pos) {
    sqLiteDatabase = this.getWritableDatabase();
    sqLiteDatabase.delete(tableName, "id = ?", new String[]{Integer.toString(pos)});
    sqLiteDatabase.close();
  }


  public Encap getNotById(String id) {

    Cursor curs = sqLiteDatabase
        .rawQuery("SELECT * FROM " + tableName + "  WHERE id = " + id + " ORDER BY "
            + COLUMN_TIMESTAMP + " DESC ", null);

    curs.moveToFirst();
    Encap encap5 = new Encap();
    while (!curs.isAfterLast()) {

      encap5.setId(curs.getInt(curs.getColumnIndex(UID)));
      encap5.setNote(curs.getString(curs.getColumnIndex(note)));
      encap5.setTimestamp(curs.getString(curs.getColumnIndex(COLUMN_TIMESTAMP)));
      //encapArrayList.add(encap5);
      curs.moveToNext();
    }
    curs.close();
    return encap5;


  }

}
