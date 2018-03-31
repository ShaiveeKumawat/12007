package com.example.ankit.navigationdrawer;

import android.content.Context;
import android.net.Uri;
import java.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankit.navigationdrawer.Adapter.MsgAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chatting extends Fragment {
    public static List<MessageClass> messagesList;//List to hold products
    private DatabaseReference mDataRef;
    RecyclerView recyclerViewMessages;
    MsgAdapter adapter;//adapter to link products
    EditText message;
    Button send_btn;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message,container,false);
        messagesList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Messages").child("2").child("CS");
        message = view.findViewById(R.id.message);
        send_btn = view.findViewById(R.id.sendBtn);
        recyclerViewMessages = view.findViewById(R.id.recyclerView);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(false);
        recyclerViewMessages.setLayoutManager(mLayoutManager);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(chatting.this).attach(chatting.this).commit();

            }
        });
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String message = (String)dataSnapshot1.child("message").getValue();
                    String date = (String)dataSnapshot1.child("date_time").getValue();
                    messagesList.add(new MessageClass(message,date));
                }
                adapter = new MsgAdapter(getContext(),messagesList);
                recyclerViewMessages.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error:"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

    return view;
    }
    private void send_msg() {
        String msg = message.getText().toString();
        String id = databaseReference.push().getKey();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        MessageClass m = new MessageClass(msg,currentDateTimeString);
        databaseReference.child("Messages").child("2").child("CS").child(id).setValue(m);
        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
        message.setText("");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Chat");
    }
}

