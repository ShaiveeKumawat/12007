package com.example.ankit.navigationdrawer.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankit.navigationdrawer.MessageClass;
import com.example.ankit.navigationdrawer.R;

import java.util.List;


/**
 * Created by Ankit on 15-03-2018.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder>{

    public Context context;
    public List<MessageClass> messagesList;
    public CardView cardView;

    public MsgAdapter(Context context, List<MessageClass> messagesList){
        this.context = context;
        this.messagesList = messagesList;
    }
    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_message,parent,false);
        cardView = view.findViewById(R.id.cardView);
        return new MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, final int position) {
        final MessageClass msg = messagesList.get(position);
        holder.msg.setText(msg.getMessage());
        holder.date_time.setText(msg.getDate_time());

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {

        TextView msg,date_time;
        CardView cardView;

        public MsgViewHolder(final View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.textViewmessage);
            date_time = itemView.findViewById(R.id.message_time);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
