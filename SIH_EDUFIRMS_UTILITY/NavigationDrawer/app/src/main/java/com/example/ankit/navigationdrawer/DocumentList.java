package com.example.ankit.navigationdrawer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ankit on 10-02-2018.
 */

public class DocumentList extends ArrayAdapter<Uploads> {
    private Activity context;
    List<Uploads> uploadList;

    public DocumentList(Activity context, List<Uploads> uploadList) {
        super(context, R.layout.layout_doc_list, uploadList);
        this.context = context;
        this.uploadList = uploadList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_doc_list, null, true);

        TextView textViewDocument = (TextView) listViewItem.findViewById(R.id.textViewDocument);

        Uploads route = uploadList.get(position);
        textViewDocument.setText(route.getName());

        return listViewItem;
    }
}