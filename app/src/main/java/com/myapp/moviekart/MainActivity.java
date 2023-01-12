package com.myapp.moviekart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.moviekart.Adapters.RecentMovieAdapter;
import com.myapp.moviekart.Adapters.RecyclerViewInterface;
import com.myapp.moviekart.Adapters.RecyclerViewInterfaceHome;
import com.myapp.moviekart.Adapters.RecyclerViewInterfaceSlider;
import com.myapp.moviekart.Adapters.SliderAdapter;
import com.myapp.moviekart.Models.Recent_movie_model;
import com.myapp.moviekart.Models.SliderModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterfaceHome, RecyclerViewInterfaceSlider {

    RecyclerView rvBollywood;
    RecyclerView rvHollywood;
    RecyclerView rvAnime;

    TextView tv_bollywood_viewAll;
    TextView tv_hollywood_viewAll;
    TextView tv_anime_viewAll;

    RecentMovieAdapter HollyMovieAdapter;
    RecentMovieAdapter BollyMovieAdapter;
    RecentMovieAdapter AnimeMovieAdapter;
    SliderAdapter sliderAdapter;

    Button uploadButton, btnFeedback;


    FirebaseRecyclerOptions<Recent_movie_model> optionsRecent;
    FirebaseRecyclerOptions<Recent_movie_model> optionsHolly;
    FirebaseRecyclerOptions<Recent_movie_model> optionsBolly;
    FirebaseRecyclerOptions<Recent_movie_model> optionsAnime;

    DatabaseReference refBolly;
    DatabaseReference refHolly;
    DatabaseReference refAnime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#121212")));
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));

        setContentView(R.layout.activity_main);

        rvBollywood=findViewById(R.id.rv_bollywood_movie);
        rvHollywood=findViewById(R.id.rv_hollywood_movie);
        rvAnime=findViewById(R.id.rv_anime_movie);

        tv_bollywood_viewAll = findViewById(R.id.tv_bollywood_view_all);
        tv_hollywood_viewAll = findViewById(R.id.tv_hollywood_view_all);
        tv_anime_viewAll = findViewById(R.id.tv_anime_view_all);

        SliderView sliderView = findViewById(R.id.slider);
        uploadButton=findViewById(R.id.btnAddMovie);
        btnFeedback= findViewById(R.id.btnFeedback);




        // Slider image loading
        ArrayList<SliderModel> sliderList = new ArrayList<>(2);
        SliderModel[] arr= new SliderModel[2];



        uploadButton.setOnClickListener(v -> {

            Toast.makeText(MainActivity.this, "Only Admin Can Upload Movies...", Toast.LENGTH_SHORT).show();

            try {
            startActivity(new Intent(MainActivity.this, UploadMovie.class));
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityFeedback.class));
            }
        });


        // adding onclick listener on "view all" text

        tv_hollywood_viewAll.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Hollywood_movies.class)));
        tv_bollywood_viewAll.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Bollywood_movies.class)));
        tv_anime_viewAll.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Anime_movie.class)));


        // Setting up data in slider adapter
        sliderAdapter = new SliderAdapter(this, sliderList, this);
        refBolly= FirebaseDatabase.getInstance().getReference().child("movies").child("bollywood");
        refHolly= FirebaseDatabase.getInstance().getReference().child("movies").child("hollywood");
        refAnime= FirebaseDatabase.getInstance().getReference().child("movies").child("anime");

        Random rd = new Random();
        // Adding the data into SliderList from all the three categories

        //Adding random Bollywood movies in the list
        refBolly.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot dsnapshot : snapshot.getChildren()) {
                    if(i==3){
                        break;
                    }
                    if(rd.nextBoolean()){
                        SliderModel user = dsnapshot.getValue(SliderModel.class);
                        sliderList.add(user);
                        i++;
                    }
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});

        //Adding random Hollywood movies in the list
        refHolly.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot dsnapshot : snapshot.getChildren()) {
                    if(i==3){
                        break;
                    }
                    if(rd.nextBoolean()){
                        SliderModel user = dsnapshot.getValue(SliderModel.class);
                        sliderList.add(user);
                        i++;
                    }
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});

        //Adding random Anime in the list
        refAnime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot dsnapshot : snapshot.getChildren()) {
                    if(i==3){
                        break;
                    }
                    if(rd.nextBoolean()){
                        SliderModel user = dsnapshot.getValue(SliderModel.class);
                        sliderList.add(user);
                        i++;
                    }
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});// Data addition for sliderView ends here!


        // Setting the sliderView here to display stored data
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        // to start autocycle
        sliderView.startAutoCycle();




        // Getting all movie details for holly, bolly, anime
        optionsHolly = new FirebaseRecyclerOptions.Builder<Recent_movie_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference("movies").child("hollywood")
                .limitToFirst(10), Recent_movie_model.class)
                .build();
        optionsBolly = new FirebaseRecyclerOptions.Builder<Recent_movie_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference("movies").child("bollywood")
                .limitToFirst(10), Recent_movie_model.class)
                .build();
        optionsAnime = new FirebaseRecyclerOptions.Builder<Recent_movie_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference("movies").child("anime")
                .limitToFirst(10), Recent_movie_model.class)
                .build();



        // Setting Adapters and Layout Managers

        HollyMovieAdapter = new RecentMovieAdapter(optionsHolly, this);
        rvHollywood.setAdapter(HollyMovieAdapter);
        rvHollywood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        BollyMovieAdapter = new RecentMovieAdapter(optionsBolly, this);
        rvBollywood.setAdapter(BollyMovieAdapter);
        rvBollywood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        AnimeMovieAdapter = new RecentMovieAdapter(optionsAnime, this);
        rvAnime.setAdapter(AnimeMovieAdapter);
        rvAnime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    }

    @Override
    protected void onStart() {
        super.onStart();
        HollyMovieAdapter.startListening();
        BollyMovieAdapter.startListening();
        AnimeMovieAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        HollyMovieAdapter.stopListening();
        BollyMovieAdapter.stopListening();
        AnimeMovieAdapter.stopListening();
    }

    @Override
    public void onItemClickHome(int position, FirebaseRecyclerOptions<Recent_movie_model> options) {
        Intent intent = new Intent(this, MovieDetail.class);

        intent.putExtra("name", options.getSnapshots().get(position).getName());
        intent.putExtra("link", options.getSnapshots().get(position).getLink());
        intent.putExtra("category", options.getSnapshots().get(position).getCategory());
        intent.putExtra("duration", options.getSnapshots().get(position).getDuration());
        intent.putExtra("description", options.getSnapshots().get(position).getDescription());
        intent.putExtra("thumbnail", options.getSnapshots().get(position).getThumbnail());
        intent.putExtra("trailer", options.getSnapshots().get(position).getTrailer());

        startActivity(intent);
    }

    @Override
    public void onItemClickSlider(int position, ArrayList<SliderModel> sliderList) {
        Intent intent = new Intent(this, MovieDetail.class);

        intent.putExtra("name", sliderList.get(position).getName());
        intent.putExtra("link", sliderList.get(position).getLink());
        intent.putExtra("category", sliderList.get(position).getCategory());
        intent.putExtra("duration", sliderList.get(position).getDuration());
        intent.putExtra("description", sliderList.get(position).getDescription());
        intent.putExtra("thumbnail", sliderList.get(position).getThumbnail());
        intent.putExtra("trailer", sliderList.get(position).getTrailer());

        startActivity(intent);
    }
}