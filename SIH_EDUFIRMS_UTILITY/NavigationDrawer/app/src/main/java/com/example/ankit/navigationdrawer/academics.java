package com.example.ankit.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ankit on 17-03-2018.
 */

public class academics extends Fragment {
    String s;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.academic,container,false);
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference().child("Academic");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s= dataSnapshot.getValue().toString();
                WebView mWebView = new WebView(getContext());
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+s);
                //setContentView(mWebView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Academics");
    }
}
