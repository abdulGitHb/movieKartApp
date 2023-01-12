package com.myapp.moviekart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.moviekart.Models.MoviesMainModel;

public class UploadMovie extends AppCompatActivity {

    Button btnUpload;
    EditText name, link, duration, description, thumbnail, trailer;
    Spinner categorySpinner, typeSpinner;
    String category, type;

    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_movie);

        name= findViewById(R.id.name);
        link= findViewById(R.id.link);
        trailer= findViewById(R.id.trailer);
        duration= findViewById(R.id.duration);
        description= findViewById(R.id.description);
        thumbnail= findViewById(R.id.thumbnail);
        categorySpinner= findViewById(R.id.categorySpinner);
        typeSpinner= findViewById(R.id.typeSpinner);
        btnUpload=findViewById(R.id.btnUpload);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});


        dbRef= FirebaseDatabase.getInstance().getReference("movies");
        btnUpload.setOnClickListener(v -> {
//            MoviesMainModel(String name, String link, String category, String duration, String description, String thumbnail)
            String mname= name.getText().toString();
            String mlink=link.getText().toString();
            String mcategory=category;
            String mduration= duration.getText().toString();
            String mdescription= description.getText().toString();
            String mthumbnail= thumbnail.getText().toString();
            String mtrailer= trailer.getText().toString();

            if(mname.isEmpty() || mlink.isEmpty() || mduration.isEmpty() ||mdescription.isEmpty() || mthumbnail.isEmpty() || mtrailer.isEmpty()){
                Toast.makeText(UploadMovie.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            }else {
                MoviesMainModel movie = new MoviesMainModel(mname, mlink, mcategory, mduration, mdescription, mthumbnail, mtrailer);

                dbRef.child(type).push().setValue(movie);
            }
        });


    }
}