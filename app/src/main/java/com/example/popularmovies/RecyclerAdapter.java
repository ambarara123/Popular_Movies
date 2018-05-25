package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    int numberOfItems;


    public RecyclerAdapter(int numberOfItems) {
        this.numberOfItems = numberOfItems;
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
        holder.imgThumbnail.setImageResource(R.drawable.ic_launcher_background);

    }



    @Override
    public int getItemCount() {
        //to be updated
        return numberOfItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
        }
    }


}
