package saini.ayush.nearbytask.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String id= "ID";
    private static final String title= "TITLE";
    private static final String task= "TASK";
    private static final String date= "DATE";
    public static final String TABLE_NAME = "TASKS";
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,TASK TEXT,DATE TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }




    // INSERT DNEW TASK IN DATABASE -->




    public boolean insertData(String Title, String Task ){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title , Title);
        contentValues.put(task, Task);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String Time = sdf.format(new Date ());
        Toast.makeText (context,Time,Toast.LENGTH_SHORT).show();
        contentValues.put(date,Time);
        long result =  db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        else
            return true;
    }



    // GET ALL TASK DATA -->



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
                task.setTimestamp(cursor.getString(cursor.getColumnIndex(this.date)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        db.close();
        return tasks;
    }




    //// GET ALL DATA ABOUT SINGLE TASK -->


    public Task getTask(int _id){
        Task task = new Task();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "+id+" = "+_id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst ()){
            task.setId(cursor.getInt(cursor.getColumnIndex(id)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(this.title)));
            task.setTask(cursor.getString(cursor.getColumnIndex(this.task)));
            task.setTimestamp(cursor.getString(cursor.getColumnIndex(this.date)));
        }else{
            Toast.makeText (context,"Error",Toast.LENGTH_SHORT).show();
        }

        return task;
    }



    // UPDATE TASK -->



    public void updateTask(int _id, String title, String task, String date){
        ContentValues cv = new ContentValues();
        cv.put(this.id,_id); //These Fields should be your String values of actual column names
        cv.put(this.title,title);
        cv.put(this.task,task);
        cv.put(this.date,date);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update (TABLE_NAME,cv,this.id+" = " + _id,null);
    }



    //DELETE TASK -->



    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, id + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }
}