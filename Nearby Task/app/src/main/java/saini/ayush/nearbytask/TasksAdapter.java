package saini.ayush.nearbytask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import saini.ayush.nearbytask.model.Task;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {

    private Context context;
    private List<Task> notesList;
    RelativeLayout placeholderView;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView task;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            placeholderView = (RelativeLayout) view.findViewById(R.id.placeholder_view);
            placeholderView.setOnClickListener(this);
            task = placeholderView.findViewById(R.id.taskTitle);
            timestamp = placeholderView.findViewById(R.id.timestamp);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public TasksAdapter(Context context, List<Task> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_placeholder, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = notesList.get(position);
        holder.task.setText(task.getTask());
        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(task.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2019-02-12 00:15:42
     * Output: Feb 12
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}