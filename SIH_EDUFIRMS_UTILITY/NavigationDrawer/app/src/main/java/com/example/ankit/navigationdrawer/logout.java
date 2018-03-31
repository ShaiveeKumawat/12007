package com.example.ankit.navigationdrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.Year;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ankit on 31-01-2018.
 */

public class logout extends Fragment {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.college_name)
    TextView collegeName;
    @BindView(R.id.father_name)
    TextView fatherName;
    @BindView(R.id.father_phone_number)
    TextView fatherPhoneNumber;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.logout)
    Button logout;
    Unbinder unbinder;
    @BindView(R.id.welcome)
    TextView welcome;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.welcome_Faculty)
    TextView welcomeFaculty;
    @BindView(R.id.name_faculty)
    TextView nameFaculty;
    @BindView(R.id.number_faculty)
    TextView numberFaculty;
    @BindView(R.id.college_name_faculty)
    TextView collegeNameFaculty;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.teacher)
    LinearLayout teacher;

    TextView fname,fphnum;
    LinearLayout student;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logout, container, false);
        unbinder = ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Student_Mobile_App", MODE_PRIVATE);
        fname = (TextView)view.findViewById(R.id.fname);
        fphnum = (TextView)view.findViewById(R.id.fphnum);
        student = (LinearLayout)view.findViewById(R.id.student);
        if(sharedPreferences.getString("Mode","Teacher").equals("Student")) {
            name.setText(sharedPreferences.getString("Student_Name", "NA"));
            id.setText(sharedPreferences.getString("Id", "NA"));
            number.setText(sharedPreferences.getString("Mobile_number", "NA"));
            collegeName.setText(sharedPreferences.getString("College", "NA"));
            fatherName.setText(sharedPreferences.getString("Father_Name", "NA"));
            number.setText(sharedPreferences.getString("Mobile_number", "NA"));
            year.setText(sharedPreferences.getString("Year", "NA"));
        }
        else if(sharedPreferences.getString("Mode","Teacher").equals("Parent"))
        {
            name.setText(sharedPreferences.getString("Father_Name", "NA"));
            collegeName.setText(sharedPreferences.getString("College", "NA"));
            number.setText(sharedPreferences.getString("F_Mobile_number", "NA"));
            id.setText(sharedPreferences.getString("Id", "NA"));
            fatherName.setText(sharedPreferences.getString("Student_Name", "NA"));
            fatherPhoneNumber.setText(sharedPreferences.getString("Mobile_number", "NA"));
            year.setText(sharedPreferences.getString("Year", "NA"));
            fname.setText("Child Name");
            fphnum.setText("Child's Phone Number");
            image.setImageResource(R.drawable.ic_verified_user_black_24dp);
            welcome.setText("Welcome Parent");
        }
        else if(sharedPreferences.getString("Mode","Teacher").equals("Teacher"))
        {
            nameFaculty.setText(sharedPreferences.getString("Teacher_Name", "NA"));
            department.setText(sharedPreferences.getString("Department", "NA"));
            collegeNameFaculty.setText(sharedPreferences.getString("College", "NA"));
            numberFaculty.setText(sharedPreferences.getString("Phone", "NA"));
            student.setVisibility(View.GONE);
            teacher.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Logout");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Student_Mobile_App", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Verified", false);
        editor.commit();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
