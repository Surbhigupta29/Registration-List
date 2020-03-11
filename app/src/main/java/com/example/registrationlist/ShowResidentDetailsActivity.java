package com.example.registrationlist;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ShowResidentDetailsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    List<ResidentDetails> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;
    private static final String TAG = "ResidentDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resident_details);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowResidentDetailsActivity.this));
        progressDialog = new ProgressDialog(ShowResidentDetailsActivity.this);
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference(CreateResidentsActivity.Database_Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ResidentDetails residentDetails = dataSnapshot.getValue(ResidentDetails.class);
                    list.add(residentDetails);
                    Log.d(TAG, "1......." + list);
                }
                adapter = new RecyclerViewAdapter(ShowResidentDetailsActivity.this, list);
           if(list.size() == 0){
               Toast.makeText(ShowResidentDetailsActivity.this,"No Data is in Resident list", Toast.LENGTH_LONG).show();
           }
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}