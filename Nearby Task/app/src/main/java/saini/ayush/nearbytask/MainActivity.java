package saini.ayush.nearbytask;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
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


        Empty();

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

    public void Empty(){
        int count = db.getTasksCount();
        if(count>0)noTaskView.setVisibility(View.INVISIBLE);
        else noTaskView.setVisibility(View.VISIBLE);
    }

}

