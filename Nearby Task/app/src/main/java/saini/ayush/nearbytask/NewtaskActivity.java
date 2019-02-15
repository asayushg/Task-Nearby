package saini.ayush.nearbytask;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import saini.ayush.nearbytask.model.DatabaseHelper;

public class NewtaskActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText newTask;
    private EditText titleView;
    private  String Title;
    private  String Task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        db = new DatabaseHelper(this);
        newTask = (EditText) findViewById(R.id.taskdetail);
        titleView = (EditText) findViewById(R.id.title);
    }
    public void saveTask(View view){
        Title= titleView.getText().toString();
        Task = newTask.getText().toString();
        if(Title.isEmpty()){
            Toast.makeText(this,"Title cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean isInserted = db.insertData(Title,Task);
        if(isInserted) {
            Toast.makeText(this,"Task Added",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else Toast.makeText(this,"Failed to add Task",Toast.LENGTH_SHORT).show();

    }


}
