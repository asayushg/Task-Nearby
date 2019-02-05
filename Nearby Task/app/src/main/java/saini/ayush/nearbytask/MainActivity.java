package saini.ayush.nearbytask;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.List;

import saini.ayush.nearbytask.model.ExampleData;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Animator spruceAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                initSpruce();
            }
        };

        // Mock data objects
        List<ExampleData> placeHolderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            placeHolderList.add(new ExampleData());
        }

        recyclerView.setAdapter(new RecyclerAdapter(placeHolderList));
        recyclerView.setLayoutManager(linearLayoutManager);


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
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        List<ExampleData> placeholderList;

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            RelativeLayout placeholderView;

            ViewHolder(View itemView) {
                super(itemView);
                placeholderView = (RelativeLayout) itemView.findViewById(R.id.placeholder_view);
                placeholderView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"ABC",Toast.LENGTH_SHORT).show();
            }
        }

        RecyclerAdapter(List<ExampleData> placeholderList) {
            this.placeholderList = placeholderList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_placeholder, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return placeholderList.size();
        }

    }
}

