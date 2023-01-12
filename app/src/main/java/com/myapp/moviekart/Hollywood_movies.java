package com.myapp.moviekart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.moviekart.Adapters.MoviesMainAdapter;
import com.myapp.moviekart.Adapters.RecentMovieAdapter;
import com.myapp.moviekart.Adapters.RecyclerViewInterface;
import com.myapp.moviekart.Models.MoviesMainModel;
import com.myapp.moviekart.Models.Recent_movie_model;

import java.util.ArrayList;

public class Hollywood_movies extends AppCompatActivity implements RecyclerViewInterface {

    MoviesMainAdapter hollyAdapter;
    FirebaseRecyclerOptions<MoviesMainModel> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();// hide status bar

        setContentView(R.layout.activity_hollywood_movies);

        Toast.makeText(Hollywood_movies.this,"Movies are sorted by Name for easy search", Toast.LENGTH_LONG).show();

        RecyclerView rvHollyMain=findViewById(R.id.rv_holly_main);

        options= new FirebaseRecyclerOptions.Builder<MoviesMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("movies").child("hollywood")
                        .orderByChild("name"), MoviesMainModel.class)
                        .build();

        hollyAdapter= new MoviesMainAdapter(options, this);
        rvHollyMain.setAdapter(hollyAdapter);

        rvHollyMain.setLayoutManager(new GridLayoutManager(this, 3));
    }
    @Override
    protected void onStart() {
        super.onStart();
        hollyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hollyAdapter.stopListening();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Hollywood_movies.this, MovieDetail.class);

        intent.putExtra("name", options.getSnapshots().get(position).getName());
        intent.putExtra("link", options.getSnapshots().get(position).getLink());
        intent.putExtra("category", options.getSnapshots().get(position).getCategory());
        intent.putExtra("duration", options.getSnapshots().get(position).getDuration());
        intent.putExtra("description", options.getSnapshots().get(position).getDescription());
        intent.putExtra("thumbnail", options.getSnapshots().get(position).getThumbnail());
        intent.putExtra("trailer", options.getSnapshots().get(position).getTrailer());

        startActivity(intent);

    }
}