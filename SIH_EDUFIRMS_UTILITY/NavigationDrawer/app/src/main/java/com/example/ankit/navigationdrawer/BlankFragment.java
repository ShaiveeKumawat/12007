package com.example.ankit.navigationdrawer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    public Context nCtx;


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_blank,container,false);
        TextView Id = (TextView)view.findViewById(R.id.eId);
        TextView Title = (TextView)view.findViewById(R.id.eTitle);;
        TextView Description= (TextView)view.findViewById(R.id.eDescription);;
        ImageView Image= (ImageView)view.findViewById(R.id.eImage);;

//        Bundle eData = getArguments().getActivity().getIntent().getExtras();
//        String eId=eData.getString("eId");
//        String eTitle=eData.getString("eTitle");
//        String eDescription=eData.getString("eDescription");
//        String eImage=eData.getString("eImage");
        String eId=getArguments().getString("eId");
        String eTitle=getArguments().getString("eTitle");
        String eDescription=getArguments().getString("eDescription");
        String eImage =getArguments().getString("eImage");

        Id.setText(eId);
        Title.setText(eTitle);
        Description.setText(eDescription);
        Glide.with(nCtx.getApplicationContext()).load(String.valueOf(eImage)).into(Image);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
