package com.myapp.moviekart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.myapp.moviekart.Models.Recent_movie_model;
import com.myapp.moviekart.R;

import java.util.ArrayList;


public class RecentMovieAdapter extends FirebaseRecyclerAdapter<Recent_movie_model, RecentMovieAdapter.viewHolder> {

    private final RecyclerViewInterfaceHome recyclerViewInterfaceHome;
    public FirebaseRecyclerOptions<Recent_movie_model> option;

    public RecentMovieAdapter(@NonNull FirebaseRecyclerOptions<Recent_movie_model> options,
                              RecyclerViewInterfaceHome recyclerViewInterfaceHome) {
        super(options);
        this.option= options;
        this.recyclerViewInterfaceHome=recyclerViewInterfaceHome;
    }



    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Recent_movie_model model) {
        Glide.with(holder.movieThumbnail.getContext()).load(model.getThumbnail()).into(holder.movieThumbnail);
        holder.movieName.setText(model.getName());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout_two, parent, false);
        return new viewHolder(view, recyclerViewInterfaceHome, option);
    }


    public static class viewHolder extends RecyclerView.ViewHolder {

        ImageView movieThumbnail;
        TextView movieName;
//        TextView movieCtgry;
//        TextView movieDur;
//        TextView movieDesc;
//        String movieLink;


        public viewHolder(@NonNull View itemView, RecyclerViewInterfaceHome recyclerViewInterfaceHome, FirebaseRecyclerOptions<Recent_movie_model> option) {
            super(itemView);
            movieThumbnail =itemView.findViewById(R.id.movie_thumbnail);
            movieName =itemView.findViewById(R.id.movie_name);
//            movieCtgry = itemView.findViewById(R.id.tv_movie_category_detail);
//            movieDur = itemView.findViewById(R.id.tv_movie_duration_detail);
//            movieDesc = itemView.findViewById(R.id.tv_desc_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterfaceHome!=null){
                        int pos=getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterfaceHome.onItemClickHome(pos, option);
                        }
                    }
                }
            });
        }
    }



}
