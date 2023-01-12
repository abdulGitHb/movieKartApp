package com.myapp.moviekart.Adapters;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.myapp.moviekart.Models.Recent_movie_model;
import com.myapp.moviekart.Models.SliderModel;

import java.util.ArrayList;

public interface RecyclerViewInterfaceSlider {
    void onItemClickSlider(int position, ArrayList<SliderModel> sliderList);
}
