package saini.ayush.nearbytask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBufferObserverSet;

import java.text.SimpleDateFormat;
import java.util.Date;

import saini.ayush.nearbytask.model.DatabaseHelper;
import saini.ayush.nearbytask.model.Task;

public class TaskDetailActivity extends AppCompatActivity {

    EditText taskhead,taskcontent;
    TextView edit;
    private int clickCount = 0,_id;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_task_detail);
        taskcontent = findViewById (R.id.taskcontent);
        taskhead=findViewById (R.id.taskhead);
        edit = findViewById (R.id.edit);

        Intent i = getIntent ();
        _id = i.getIntExtra ("ID",0);
        DatabaseHelper db = new DatabaseHelper (this);
        Task task = db.getTask (_id);
        taskhead.setText(task.getTitle ());
        taskcontent.setText(task.getTask ());
    }
    public void enableEditing( View v){
        clickCount++;
        taskcontent.setEnabled (true);
        taskhead.setEnabled (true);
        if(clickCount%2!=0)edit.setText ("DONE");
        else done();
    }

    public void done(){
        edit.setText ("EDIT");
        taskcontent.setEnabled (false);
        taskhead.setEnabled (false);
        String title = taskhead.getText ().toString();
        String content = taskcontent.getText ().toString ();
        //update database


        DatabaseHelper db = new DatabaseHelper (this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = sdf.format(new Date ());
        db.updateTask (_id,title,content,time);


        //do database related code above this
        super.onBackPressed ();
    }
}
