package com.example.ankit.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ankit.navigationdrawer.DisplayEvent;
import com.example.ankit.navigationdrawer.Event;
import com.example.ankit.navigationdrawer.R;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    //this context we will use to inflate the layout
    public Context mCtx;
//add a;
//    DatabaseReference databasepBrand;

    //we are storing all the products in a list
    private List<Event> eventList;
//    List<String> keyList;
//    DatabaseReference myref ;
//    DatabaseReference mychild,mlike,mcheck;

    //getting the context and product list with constructor
    public EventAdapter(Context mCtx, List<Event> eventList) {
        this.mCtx = mCtx;
        this.eventList = eventList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_events,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        //getting the product of the specified position
        final Event event = eventList.get(position);

        //myref = FirebaseDatabase.getInstance().getReference().child("Adidas").child("T-shirt");
//        final String key = keyList.get(position).toString();
        //binding the data with the viewholder views
        holder.textViewTitle.setText(event.geteTitle());
        holder.textViewShortDesc.setText(event.geteDescription());
        Glide.with(mCtx.getApplicationContext()).load(String.valueOf(event.geteImgurl())).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }


    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;

        public EventViewHolder(final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(ctx, "Button Pressed", Toast.LENGTH_SHORT).show();
                    Event event = eventList.get(getLayoutPosition());
                    Intent intentBundle = new Intent(mCtx,DisplayEvent.class);
                    Bundle bundle = new Bundle();
                    intentBundle.putExtra("eId",event.geteId());
                    intentBundle.putExtra("etitle",event.geteTitle());
                    intentBundle.putExtra("eDescription",event.geteDescription());
                    intentBundle.putExtra("eImage",event.geteImgurl());
                    //Toast.makeText(mCtx, "Button Pressed", Toast.LENGTH_SHORT).show();
                    mCtx.startActivity(intentBundle);
                }
            });
        }

    }
}

