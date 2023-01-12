package com.myapp.moviekart.Adapters;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.myapp.moviekart.Models.Recent_movie_model;

public interface RecyclerViewInterfaceHome {
    void onItemClickHome(int position, FirebaseRecyclerOptions<Recent_movie_model> options);
}
