package saini.ayush.nearbytask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    EditText taskhead,taskcontent;
    TextView edit;
    private int clickCount = 0;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_task_detail);
        taskcontent = findViewById (R.id.taskcontent);
        taskhead=findViewById (R.id.taskhead);
        edit = findViewById (R.id.edit);
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
        //update database


        //do database related code above this
        super.onBackPressed ();
    }
}
