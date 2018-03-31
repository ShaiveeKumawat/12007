package com.example.ankit.navigationdrawer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ankit.navigationdrawer.Adapter.LibraryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class library extends Fragment {

    private List<LibraryClass> libraryClassList;//List to hold products
    private DatabaseReference mDataRef;
    RecyclerView recyclerViewBooks;
    LibraryAdapter adapter;//adapter to link products


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.library,container,false);
        libraryClassList = new ArrayList<>();
        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);
//        recyclerView.setHasFixedSize(true);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        //creating recyclerview adapter
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Library").child("16ETCCS033");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = (String)dataSnapshot1.child("Name").getValue();
                    String number = (String)dataSnapshot1.child("Book_Id").getValue();
                    String issuedDate = (String)dataSnapshot1.child("Date").getValue();
                    String submissionDate = (String)dataSnapshot1.child("Days").getValue();
                    String id =dataSnapshot1.getKey();
                    libraryClassList.add(new LibraryClass(name,number,issuedDate,submissionDate));
                }
                adapter = new LibraryAdapter(getContext(),libraryClassList);
                recyclerViewBooks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error:"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerViewBooks.setLayoutManager(mLayoutManager);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Library");
    }

}
