package saini.ayush.nearbytask;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import saini.ayush.nearbytask.model.DatabaseHelper;
import saini.ayush.nearbytask.model.Task;
import saini.ayush.nearbytask.model.TaskAdapter;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Animator spruceAnimator;
    private TextView noTaskView;
    private TaskAdapter mAdapter;
    private List<Task> tasksList = new ArrayList<>();
    private int pos;

    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        noTaskView = findViewById(R.id.noTask);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                initSpruce();
            }
        };
        db = new DatabaseHelper(this);
        tasksList.addAll(db.getAllTasks());
        mAdapter = new TaskAdapter(this, tasksList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        db.close ();

        Empty();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Opening Detailed View of task and Edit
                Intent intent = new Intent(MainActivity.this,TaskDetailActivity.class);

                //code to send clicked item data (like database_task_id(only) or all data)
                intent.putExtra("ID",tasksList.get (position).getId ());

                MainActivity.this.startActivity (intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                pos=position;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Delete Task?");
                            alertDialogBuilder.setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            deleteTask(position);
                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    }
        }));

    }
    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
        db = new DatabaseHelper(this);
        tasksList.clear ();
        tasksList.addAll(db.getAllTasks());
        mAdapter.notifyDataSetChanged ();
        db.close ();
        Empty ();
    }


    private void initSpruce() {
        spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                .sortWith(new DefaultSort(100))
                .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                        ObjectAnimator.ofFloat(recyclerView, "translationX", -recyclerView.getWidth(), 0f).setDuration(800))
                .start();
    }

    public void NewTask(View view){
        Intent intent = new Intent(MainActivity.this,NewtaskActivity.class);
        startActivity(intent);
    }

    private void deleteTask(int position) {
        // deleting the note from db
        db = new DatabaseHelper(this);
        db.deleteTask(tasksList.get(position));

        // removing the note from the list
        tasksList.remove(position);
        mAdapter.notifyItemRemoved(position);
        db.close ();

        Empty();
    }

    public void Empty(){
        int count = tasksList.size();
        if(count>0)noTaskView.setVisibility(View.INVISIBLE);
        else noTaskView.setVisibility(View.VISIBLE);
    }

}

