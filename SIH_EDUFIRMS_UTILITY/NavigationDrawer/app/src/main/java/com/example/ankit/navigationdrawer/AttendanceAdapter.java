package com.example.ankit.navigationdrawer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder>{
    Context context;

    ArrayList<String> sub;
    ArrayList<Float> per;

    public AttendanceAdapter(Context context, ArrayList<String> subject , ArrayList<Float> percentage)
    {
        this.sub = new ArrayList<String>();
        this.per = new ArrayList<Float>();
        this.context =context;
        this.sub = subject;
        this.per = percentage;
    }

    @Override
    public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View myownview = inflater.inflate(R.layout.attendance_parent,parent,false);
        return new AttendanceHolder(myownview);
    }

    @Override
    public void onBindViewHolder(AttendanceHolder holder, int position)
    {
        holder.name.setText(sub.get(position));
        holder.per.setText(per.get(position).toString());
        holder.circleProgressbar.setForegroundProgressColor(Color.GREEN);
        holder.circleProgressbar.setBackgroundProgressWidth(30);
        holder.circleProgressbar.setForegroundProgressWidth(30);
        holder.circleProgressbar.setRoundedCorner(true);
        holder.circleProgressbar.setClockwise(false);
        int animationDuration = 1000; // 1000ms = 1s
        holder.circleProgressbar.setProgressWithAnimation(per.get(position), animationDuration);
    }

    @Override
    public int getItemCount() {
        return sub.size();
    }

    public class AttendanceHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView per;
        CircleProgressbar circleProgressbar;
        public AttendanceHolder(View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            per = (TextView)itemView.findViewById(R.id.per);
            circleProgressbar = (CircleProgressbar)itemView.findViewById(R.id.yourCircularProgressbar);
        }
    }
}
