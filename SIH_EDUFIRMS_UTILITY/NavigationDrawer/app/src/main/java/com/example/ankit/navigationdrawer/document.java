package com.example.ankit.navigationdrawer;


import android.*;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class document extends Fragment {

    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";

    private static final int PICK_PDF_CODE = 2342;
    private Spinner spinner;
    private Button buttonUploadFile;
    private ProgressBar progressBar;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    ListView listViewDocuments;
    List<Uploads> uploadList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.document, container, false);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
        listViewDocuments = (ListView) view.findViewById(R.id.listView);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        buttonUploadFile = (Button) view.findViewById(R.id.buttonUploadFile);
        uploadList = new ArrayList<>();
        buttonUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF();
            }
        });
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uploadList.clear();
                for (DataSnapshot uploadSnapshot : dataSnapshot.getChildren()) {
                    Uploads uploads = uploadSnapshot.getValue(Uploads.class);
                    uploadList.add(uploads);
                }

                DocumentList adapter = new DocumentList(getActivity(), uploadList);
                listViewDocuments.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewDocuments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Uploads uploads = uploadList.get(position);
                Delete(uploads.getId());
                return false;
            }
        });
        return view;
    }

    public void Delete(final String id) {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(getContext());
        myAlert.setTitle("Delete");
        myAlert.setMessage("Are you sure?");
        myAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDocument(id);
                dialog.dismiss();
            }
        });
        myAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        myAlert.setCancelable(false);
        myAlert.show();
    }


    public void deleteDocument(String id) {
        mDatabaseReference = mDatabaseReference.child(id);
        mDatabaseReference.removeValue();
        Toast.makeText(getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        mDatabaseReference = mDatabaseReference.getRoot().child(DATABASE_PATH_UPLOADS);
    }


    private void getPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            } else {
                Toast.makeText(getContext(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        StorageReference sRef = mStorageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String item = spinner.getSelectedItem().toString();
                        String id = mDatabaseReference.push().getKey();
                        Uploads upload = new Uploads(item, taskSnapshot.getDownloadUrl().toString(), id);
                        mDatabaseReference.child(id).setValue(upload);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //textViewStatus.setText((int) progress + "% Uploading...");

                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Document");
    }
}
