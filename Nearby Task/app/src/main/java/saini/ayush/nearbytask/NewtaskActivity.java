package saini.ayush.nearbytask;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import saini.ayush.nearbytask.model.DatabaseHelper;
import saini.ayush.nearbytask.model.Task;

public class NewtaskActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private TasksAdapter mAdapter;
    private List<Task> tasksList = new ArrayList<>();
    private EditText newNote;
    private  String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        db = new DatabaseHelper(this);
        newNote = (EditText) findViewById(R.id.taskdetail);
    }

    public void BackButton(View view){
        Intent intent = new Intent(NewtaskActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void saveTask(View view){
        string = newNote.toString();
        createTask(string);
    }

    private void createTask(String task) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertTask(task);

        // get the newly inserted note from db
        Task n = db.getTask(id);

        if (n != null) {
            // adding new note to array list at 0 position
            tasksList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();
        }
    }

}
