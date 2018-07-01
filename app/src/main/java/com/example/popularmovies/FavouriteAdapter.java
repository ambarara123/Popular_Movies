package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.data.FavouriteEntity;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private Context context;
    private List<FavouriteEntity> favList;

    public FavouriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fav_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.movieId.setText(favList.get(position).getMoviename());

    }

    @Override
    public int getItemCount() {
        if (favList == null){
            return 0;
        }
        return favList.size();
    }

    public List<FavouriteEntity> getFavList() {
        return favList;
    }

    public void setFavList(List<FavouriteEntity> favList) {
        this.favList = favList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieId;
        public ViewHolder(View itemView) {
            super(itemView);
            movieId = (TextView) itemView.findViewById(R.id.favMovieId);
        }
    }
}
