package com.example.ankit.navigationdrawer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankit.navigationdrawer.LibraryClass;
import com.example.ankit.navigationdrawer.R;

import java.util.List;

/**
 * Created by Ankit on 10-03-2018.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    public Context context;
    public List<LibraryClass> libraryClassList;

    public LibraryAdapter(Context context,List<LibraryClass> libraryClassList)
    {
        this.context = context;
        this.libraryClassList = libraryClassList;
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_library, parent,false);
        return new LibraryAdapter.LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibraryViewHolder holder, int position) {
        //getting the product of the specified position
        final LibraryClass libraryClass = libraryClassList.get(position);
        holder.textViewName.setText("Book Name: "+libraryClass.getbName());
        holder.textViewNumber.setText("Book Number: "+libraryClass.getbNumber());
        holder.textViewIssuedDate.setText("Issued Date: "+libraryClass.getbIssuedDate());
        holder.textViewSubmissionDate.setText("Return in: "+libraryClass.getbSubmissionDate()+"days");
    }

    @Override
    public int getItemCount() {
        return libraryClassList.size();
    }

    class LibraryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewNumber,textViewIssuedDate,textViewSubmissionDate;

        public LibraryViewHolder(final View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewIssuedDate = itemView.findViewById(R.id.textViewIssuedDate);
            textViewSubmissionDate = itemView.findViewById(R.id.textViewSubmissionDate);
        }

    }
}


/*

{
        Date dt2 = new DateAndTime().getCurrentDateTime();

        long diff = dt2.getTime() - dt1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));

        if (diffInDays > 1) {
            System.err.println("Difference in number of days (2) : " + diffInDays);
            return false;
        } else if (diffHours > 24) {

            System.err.println(">24");
            return false;
        } else if ((diffHours == 24) && (diffMinutes >= 1)) {
            System.err.println("minutes");
            return false;
        }
        return true;
}

 */