package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.popularmovies.utilities.TrailerModel;
import com.example.popularmovies.utilities.UTILs;

import java.util.ArrayList;

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.TrailerViewHolder>{

    private Context context;
    private ArrayList<TrailerModel> trailers;

    public TrailerRecyclerAdapter(Context context, ArrayList<TrailerModel> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_list,parent,false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
            holder.trailerName.setText(trailers.get(position).getTrailerName());
            //building youtube url with base and id
            final String youtubeUrl = UTILs.YOUTUBE_URL + trailers.get(position).getValue();

        Log.d("name",trailers.get(position).getTrailerName());

            holder.trailerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));

                    youtubeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    youtubeIntent.setPackage("com.google.android.youtube");

                    context.startActivity(youtubeIntent);
                }
            });

        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, youtubeUrl);
                context.startActivity(Intent.createChooser(intent, "share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
Log.d("length",String.valueOf(trailers.size()));
        return MovieActivity.trailers.size();

    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder{

        TextView trailerName;
        CardView trailerLayout;
        ImageButton shareImage;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailerName);
            trailerLayout = itemView.findViewById(R.id.trailerView);
            shareImage = itemView.findViewById( R.id.shareImage);
        }
    }

}
