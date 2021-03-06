package com.example.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.data.FavouriteDatabase;
import com.example.popularmovies.data.FavouriteEntity;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.movieName.setText(favList.get(position).getMoviename());
        Picasso.get().load(favList.get(position).getImage()).into(holder.favImage);
        //delete from favlist on long clicking on card
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final int movieId = favList.get(position).getMovieId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.deleteFav)
                        .setMessage(R.string.delete_message)
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        FavouriteDatabase.getInstance(context).favouriteDao().deleteFavById(movieId);

                                    }
                                });

                                //remove that row from favourite list
                                favList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Deleted from favourites", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                            }
                        }).show();
                return true;

            }
        });

        //to open detail view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FavouriteDetail.class);
                //intent.putExtra("favActivity","fromFav");
                intent.putExtra("movie_id",favList.get(position).getMovieId());
                intent.putExtra("title",favList.get(position).getMoviename());
                intent.putExtra("image_url",favList.get(position).getImage());
                intent.putExtra("description",favList.get(position).getDescription());
                intent.putExtra("date",favList.get(position).getReleaseDate());
                intent.putExtra("rating",favList.get(position).getVoteAverage());
                context.startActivity(intent);
            }
        });

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
        TextView movieName;
        ImageView favImage;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.favMovieId);
            favImage = (ImageView) itemView.findViewById(R.id.favImage);
            cardView = (CardView) itemView.findViewById(R.id.favCard);

        }
    }
}
