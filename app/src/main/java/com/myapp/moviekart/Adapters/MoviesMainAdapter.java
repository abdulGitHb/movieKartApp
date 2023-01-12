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
import com.myapp.moviekart.Models.MoviesMainModel;
import com.myapp.moviekart.R;

import java.util.ArrayList;


public class MoviesMainAdapter extends FirebaseRecyclerAdapter<MoviesMainModel, MoviesMainAdapter.viewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    public MoviesMainAdapter(@NonNull FirebaseRecyclerOptions<MoviesMainModel> options,
                             RecyclerViewInterface recyclerViewInterface) {
        super(options);
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull MoviesMainModel model) {
        Glide.with(holder.movieThumbnailMain.getContext()).load(model.getThumbnail()).into(holder.movieThumbnailMain);
        holder.movieNameMain.setText(model.getName());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout, parent, false);

        return new viewHolder(view, recyclerViewInterface);
    }


//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(context).inflate(R.layout.movie_layout, parent, false);
//
//        return new viewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
//
//        MoviesMainModel moviesMainModel= list.get(position);
//        holder.imageView.setImageResource(moviesMainModel.getPic());
//        holder.textView.setText(moviesMainModel.getName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        ImageView movieThumbnailMain;
        TextView movieNameMain;

        public viewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            movieThumbnailMain=itemView.findViewById(R.id.movie_thumbnail_main);
            movieNameMain = itemView.findViewById(R.id.movie_name_main);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
