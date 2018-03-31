package com.example.ankit.navigationdrawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceParent extends Fragment {

    Long pend,pend1,p,t;
    String college,Year,Branch,Id;

    ArrayList<String> subjects = new ArrayList<String>();
    ArrayList<Float> percentage = new ArrayList<Float>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_attendance_parent, container, false);
        p = (long) 0;
        t = (long) 0;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Student_Mobile_App", Context.MODE_PRIVATE);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        college = sharedPreferences.getString("College", "NA");
        Year = sharedPreferences.getString("Year", "NA");
        Branch = sharedPreferences.getString("Branch", "NA");
        Id = sharedPreferences.getString("Id", "NA");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Colleges/" + college + "/Year/" + Year + "/" + Branch + "/Students/" + Id + "/Attendance");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pend = dataSnapshot.getChildrenCount();
                for (DataSnapshot Subjects : dataSnapshot.getChildren()) {

                    subjects.add(Subjects.getKey());
                    String value = Subjects.getKey();


                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Colleges/" + college + "/Year/" + Year + "/" + Branch + "/Students/" + Id + "/Attendance/" + value);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pend1 = dataSnapshot.getChildrenCount();
                            p = (long) 0;
                            t = (long) 0;
                            for (DataSnapshot Dates : dataSnapshot.getChildren()) {
                                pend1 = pend1 - 1;
                                if (Dates.getValue().equals("P")) {
                                    p++;

                                }
                                t++;

                            }
                            if (pend1 == 0)
                            {
                                pend = pend - 1;
                                percentage.add((float) (p * 100.00) / t);
                                if (pend == 0 && pend1 ==0)
                                {
                                    AttendanceAdapter attendanceAdapter = new AttendanceAdapter(getContext(), subjects, percentage);
                                    recyclerView.setAdapter(attendanceAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


                }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

        return view;
    }

}
