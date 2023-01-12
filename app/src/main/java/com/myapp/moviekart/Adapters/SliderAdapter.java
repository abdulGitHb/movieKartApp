package com.myapp.moviekart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.moviekart.Models.SliderModel;
import com.myapp.moviekart.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>{

    RecyclerViewInterfaceSlider recyclerViewInterfaceSlider;
    private final ArrayList<SliderModel> sliderList;
    Context context;

    public SliderAdapter(Context context, ArrayList<SliderModel> sliderList, RecyclerViewInterfaceSlider recyclerViewInterfaceSlider) {
        this.context=context;
        this.sliderList = sliderList;
        this.recyclerViewInterfaceSlider=recyclerViewInterfaceSlider;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {

        Glide.with(viewHolder.movieThumbnail.getContext()).load(sliderList.get(position).getThumbnail()).into(viewHolder.movieThumbnail);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewInterfaceSlider.onItemClickSlider(position, sliderList);
            }
        });
    }

    @Override
    public int getCount() {
        return sliderList.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView movieThumbnail;
        public SliderAdapterViewHolder(View itemView) {
            super(itemView);

            this.itemView=itemView;
            movieThumbnail=itemView.findViewById(R.id.sliderImage);
        }
    }

}
