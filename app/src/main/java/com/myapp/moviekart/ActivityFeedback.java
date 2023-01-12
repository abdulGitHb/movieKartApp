package com.myapp.moviekart;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityFeedback extends AppCompatActivity {

    Button btnFeedback;
    EditText etTopic, etDesc;
    DatabaseReference dbRefFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        btnFeedback= findViewById(R.id.btnUploadFeedback);
        etTopic= findViewById(R.id.etTopicFeedback);
        etDesc= findViewById(R.id.etDescFeedback);

        btnFeedback.setOnClickListener(v -> {
            dbRefFeed= FirebaseDatabase.getInstance().getReference().child("feedback");
            if(etTopic.getText().toString().isEmpty() || etDesc.getText().toString().isEmpty()){
                Toast.makeText(ActivityFeedback.this, "Please fill in all the details", Toast.LENGTH_LONG).show();
            }else{
                ArrayList<String> map= new ArrayList<>();
                String topic= etTopic.getText().toString();
                String desc= etDesc.getText().toString();
                map.add(0, topic);
                map.add(1, desc);
                dbRefFeed.push().setValue(map.toString());
                Toast.makeText(ActivityFeedback.this, "Thankyou for your valuable Feedback!", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}