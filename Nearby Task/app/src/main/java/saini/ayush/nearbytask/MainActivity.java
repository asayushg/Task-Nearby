package saini.ayush.nearbytask;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.List;

import saini.ayush.nearbytask.model.DatabaseHelper;
import saini.ayush.nearbytask.model.Task;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Animator spruceAnimator;
    private TasksAdapter mAdapter;
    private List<Task> tasksList = new ArrayList<>();
    private TextView noTaskView;
    private static final String tag = "MyActivity";

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
        Log.d(tag, String.valueOf(tasksList));
        mAdapter = new TasksAdapter(this, tasksList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();



    }
    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
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

    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (db.getTasksCount() > 0) {
            noTaskView.setVisibility(View.GONE);
        } else {
            noTaskView.setVisibility(View.VISIBLE);
        }
    }

}

