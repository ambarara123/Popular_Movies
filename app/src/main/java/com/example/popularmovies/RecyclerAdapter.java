package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ItemListener listener;
    Context context;
    public ArrayList<Movie> moviesList;
    public ArrayList<String> images;


    public interface ItemListener{
        void onItemClick(int click);
    }

    //parameters will be changed later

    public RecyclerAdapter(ItemListener listener, ArrayList<Movie> moviesList, ArrayList<String> images) {
       // this.context = context;
        this.moviesList = moviesList;
        this.images = images;
        this.listener = listener;


    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movies_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().
                load(MainActivity.images.get(position))
                .into(holder.imgThumbnail);


    }



    @Override
    public int getItemCount() {
        //to be updated
        return MainActivity.moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            listener.onItemClick(adapterPosition);

        }
    }



}
