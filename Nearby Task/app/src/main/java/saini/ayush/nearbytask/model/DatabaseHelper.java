package saini.ayush.nearbytask.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String id= "ID";
    private static final String title= "TITLE";
    private static final String task= "TASK";
    private static final String date= "DATE";
    public static final String TABLE_NAME = "TASKS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,TASK TEXT,DATE DATETIME DEFAULT CURRENT_TIMESTAMP) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String Title, String Task ){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title , Title);
        contentValues.put(task, Task);
       long result =  db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        else
            return true;
    }

    public List<Task> getAllTasks(){
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                date + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(id)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(this.title)));
                task.setTask(cursor.getString(cursor.getColumnIndex(this.task)));
                task.setTimestamp(cursor.getString(cursor.getColumnIndex(date)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        db.close();
        return tasks;
    }

    public int getTasksCount() {
        //String countQuery = "SELECT  COUNT(*) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(countQuery, null);
        int count = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);//cursor.getInt (cursor.getColumnIndex ("COUNT(*)"));
        //cursor.close();
        return count;
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, id + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }
}