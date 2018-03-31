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

import com.example.ankit.navigationdrawer.Adapter.EventAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

public class dashboard extends Fragment {
    private List<Event> eventList;//List to hold products
    private DatabaseReference mDataRef;
    RecyclerView recyclerViewEvents;
    EventAdapter adapter;//adapter to link products

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dashboard,container,false);
        List<Banner> banners=new ArrayList<>();
        BannerSlider bannerSlider = view.findViewById(R.id.banner);
        //add banner using image url
        //banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.one));
        banners.add(new DrawableBanner(R.drawable.two));
        banners.add(new DrawableBanner(R.drawable.three));
        banners.add(new DrawableBanner(R.drawable.four));
        bannerSlider.setBanners(banners);

        //Event card data
        eventList = new ArrayList<>();
        recyclerViewEvents = view.findViewById(R.id.recyclerViewEvents);
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        //creating recyclerview adapter
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Events");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String desc = (String)dataSnapshot1.child("eDescription").getValue();
                    String title = (String)dataSnapshot1.child("eTitle").getValue();
                    String img = (String)dataSnapshot1.child("eImgurl").getValue();
                    String id =dataSnapshot1.getKey();
                    eventList.add(new Event(title,desc,img,id));
                }
                adapter = new EventAdapter(getContext(),eventList);
                recyclerViewEvents.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error:"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerViewEvents.setLayoutManager(mLayoutManager);
        return view;

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");

    }
}