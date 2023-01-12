package com.myapp.moviekart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.moviekart.Adapters.MoviesMainAdapter;
import com.myapp.moviekart.Adapters.RecyclerViewInterface;
import com.myapp.moviekart.Models.MoviesMainModel;

import java.util.ArrayList;

public class Anime_movie extends AppCompatActivity implements RecyclerViewInterface {

    MoviesMainAdapter animeAdapter;
    FirebaseRecyclerOptions<MoviesMainModel> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();// hide status bar

        setContentView(R.layout.activity_anime_movie);

        Toast.makeText(Anime_movie.this,"Movies are sorted by Name for easy search", Toast.LENGTH_LONG).show();

        RecyclerView rvAnimeMain=findViewById(R.id.rv_anime_main);

        options= new FirebaseRecyclerOptions.Builder<MoviesMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("movies").child("anime")
                        .orderByChild("name"), MoviesMainModel.class)
                        .build();


        animeAdapter= new MoviesMainAdapter(options, this);
        rvAnimeMain.setAdapter(animeAdapter);
        rvAnimeMain.setLayoutManager(new GridLayoutManager(this, 3));

    }
    @Override
    protected void onStart() {
        super.onStart();
        animeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        animeAdapter.stopListening();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Anime_movie.this, MovieDetail.class);

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