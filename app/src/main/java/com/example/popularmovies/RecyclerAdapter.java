package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    int numberOfItems;

    public static final String IMAGE_URL_BASE_PATH = "https://image.tmdb.org/t/p/w342//";


    ItemListener listener;
    Context context;

    public interface ItemListener{
        void onItemClick(String data);
    }

    //parameters will be changed later
    public RecyclerAdapter(Context context,int numberOfItems) {
        this.context = context;
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

        String url = IMAGE_URL_BASE_PATH ;

        Picasso.get()
                .load(url)
                .into(holder.imgThumbnail);

    }



    @Override
    public int getItemCount() {
        //to be updated
        return numberOfItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();



        }
    }



}
