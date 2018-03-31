package com.example.ankit.navigationdrawer;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText editTextPhone,editTextCode;
    Button sendotp,verify;
    LinearLayout verify_otp,login;
    TextInputEditText number , otp;
    int status=0;
    boolean ans = false;
    long pend;
    FirebaseAuth mAuth;

    String codeSent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //*********** Spinner *******
        SharedPreferences sharedPreferences = getSharedPreferences("Student_Mobile_App",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("Verified",false))
        {
            if (sharedPreferences.getString("Mode", "Teacher").equals("Student"))
            {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else if(sharedPreferences.getString("Mode", "Teacher").equals("Parent"))
            {
                Intent i = new Intent(LoginActivity.this,Parent.class);
                startActivity(i);
                finish();
            }
            else if(sharedPreferences.getString("Mode", "Teacher").equals("Teacher"))
            {
                Intent i = new Intent(LoginActivity.this,Teacher.class);
                startActivity(i);
                finish();
            }

        }
        spinner = (Spinner)findViewById(R.id.welcome_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        sendotp= findViewById(R.id.send_OTP);
        mAuth = FirebaseAuth.getInstance();
        verify = findViewById(R.id.verify);
        editTextPhone = findViewById(R.id.mobile_number);
        editTextCode = findViewById(R.id.otp);
        verify_otp = findViewById(R.id.verif_otp);
        login = findViewById(R.id.login);
        number = findViewById(R.id.mobile_number);
        otp = findViewById(R.id.otp);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                check_number();
//                sendVerificationCode();
//                verify_otp.setVisibility(View.VISIBLE);
//                login.setVisibility(View.GONE);
//                if(ans==true)
//                {
//                    Toast.makeText(LoginActivity.this, "successfully found", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "NOT found", Toast.LENGTH_SHORT).show();
//                }

//                login.setVisibility(view.GONE);
//                verify_otp.setVisibility(View.VISIBLE);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                verifySignInCode();
                SharedPreferences sharedPreferences = getSharedPreferences("Student_Mobile_App",MODE_PRIVATE);
                if(sharedPreferences.getBoolean("Verified",false))
                {
                    if (sharedPreferences.getString("Mode", "Teacher").equals("Student"))
                    {
                        Toast.makeText(LoginActivity.this, "Welcome Student", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else if(sharedPreferences.getString("Mode", "Teacher").equals("Parent"))
                    {
                        Toast.makeText(LoginActivity.this, "Welcome Parent", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this,Parent.class);
                        startActivity(i);
                        finish();
                    }
                    else if(sharedPreferences.getString("Mode", "Teacher").equals("Teacher"))
                    {
                        Toast.makeText(LoginActivity.this, "Welcome Teacher", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this,Teacher.class);
                        startActivity(i);
                        finish();
                    }

                }

//                Intent i = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(i);
//                finish();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //***************************
        switch(text) {
            case "Login As Student":{
                status = 0 ;
                break;
            }
            case "Login As Faculty":{

                status = 1 ;
                break;
            }
            case "Login As Parent":{

                status = 2 ;
                break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public  void check_number()
    {
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("Login");
        final DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("LOGIN-T");
        final SharedPreferences sharedPreferences = getSharedPreferences("Student_Mobile_App",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(status==0 ||status ==2) {
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pend = dataSnapshot.getChildrenCount();
                    for (DataSnapshot credentials : dataSnapshot.getChildren()) {
                        pend = pend - 1;
                        if (status == 0) {
                            String Number = (String) credentials.child("Mobile_number").getValue();
                            if (Number.equals(number.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "" + Number, Toast.LENGTH_LONG).show();
                                editor.putBoolean("Verified", true);
                                editor.putString("Mode", "Student");
                                editor.putString("Branch", credentials.child("Branch").getValue().toString());
                                editor.putString("College", credentials.child("College").getValue().toString());
                                editor.putString("F_Mobile_number", credentials.child("F_Mobile_number").getValue().toString());
                                editor.putString("Father_Name", credentials.child("Father_Name").getValue().toString());
                                editor.putString("Id", credentials.child("Id").getValue().toString());
                                editor.putString("Student_Name", credentials.child("Student_Name").getValue().toString());
                                editor.putString("Year", credentials.child("Year").getValue().toString());
                                editor.putString("Mobile_number", credentials.child("Mobile_number").getValue().toString());
                                editor.commit();
                            }
                        } else if (status == 2) {

                            String Number = (String) credentials.child("F_Mobile_number").getValue();
                            if (Number.equals(number.getText().toString()))
                            {
                                Toast.makeText(LoginActivity.this, "" + Number, Toast.LENGTH_LONG).show();
                                editor.putBoolean("Verified", true);
                                editor.putString("Mode", "Parent");
                                editor.putString("Branch", credentials.child("Branch").getValue().toString());
                                editor.putString("College", credentials.child("College").getValue().toString());
                                editor.putString("F_Mobile_number", credentials.child("F_Mobile_number").getValue().toString());
                                editor.putString("Father_Name", credentials.child("Father_Name").getValue().toString());
                                editor.putString("Id", credentials.child("Id").getValue().toString());
                                editor.putString("Student_Name", credentials.child("Student_Name").getValue().toString());
                                editor.putString("Year", credentials.child("Year").getValue().toString());
                                editor.putString("Mobile_number", credentials.child("Mobile_number").getValue().toString());
                                editor.commit();
                            }
                        }
                    }
                    if (pend == 0 && sharedPreferences.getBoolean("Verified",false))
                    {
                        verify_otp.setVisibility(View.VISIBLE);
                        login.setVisibility(View.GONE);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(status==1) {
            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {   pend = dataSnapshot.getChildrenCount();
                    for(DataSnapshot credentials : dataSnapshot.getChildren())
                    {

                        pend = pend -1;
                    String Number = (String) credentials.child("Phone").getValue();
                    Toast.makeText(LoginActivity.this, "" + Number, Toast.LENGTH_LONG).show();
                    if (Number.equals(number.getText().toString())) {
                        editor.putBoolean("Verified", true);
                        editor.putString("Mode", "Teacher");
                        editor.putString("Department", credentials.child("Department").getValue().toString());
                        editor.putString("College", credentials.child("College").getValue().toString());
                        editor.putString("Teacher_Name", credentials.child("Teacher_Name").getValue().toString());
                        editor.putString("Phone", credentials.child("Phone").getValue().toString());
//                        Set<String> set = new HashSet<String>();
//                        Set<String> set1 = new HashSet<String>();
//                        for(DataSnapshot cre : credentials.child("Year").getChildren())
//                        {
//                            for(DataSnapshot cre1 : credentials.child("Year").getChildren())
//                            {
//                                set1.add(cre1.getValue().toString());
//                            }
//                            set.add(cre.getKey());
//                        }
//                        editor.putStringSet("year",set);
//                        editor.putStringSet("Subjects",set);
                        editor.commit();

                    }

                    }
                    if (pend == 0 && sharedPreferences.getBoolean("Verified",false))
                    {
                        verify_otp.setVisibility(View.VISIBLE);
                        login.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void sendVerificationCode(){

        String phone = editTextPhone.getText().toString();

        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            editTextPhone.setError("Please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private void verifySignInCode(){
        String code = editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                            Toast.makeText(getApplicationContext(),
                                    "Login Successfull", Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
        }
    };


}
