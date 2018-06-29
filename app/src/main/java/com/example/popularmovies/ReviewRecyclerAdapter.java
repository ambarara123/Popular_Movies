package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.utilities.ReviewModel;

import java.util.ArrayList;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {
    private ArrayList<ReviewModel> reviewsList;

    private Context context;

    public ReviewRecyclerAdapter(Context context,ArrayList<ReviewModel> reviewList) {
        this.context = context;
        this.reviewsList = reviewList;
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.review_list,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.name.setText(reviewsList.get(position).getReviewerName());

        holder.review.setText(reviewsList.get(position).getReviewDetail());

    }

    @Override
    public int getItemCount() {

        return MovieActivity.reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView name, review;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.reviewName);
            review = (TextView) itemView.findViewById(R.id.review);
        }
    }


}
