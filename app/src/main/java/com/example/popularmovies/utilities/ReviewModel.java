package com.example.popularmovies.utilities;

public class ReviewModel {
    private String reviewerName;
    private String reviewDetail;

    public ReviewModel() {
        this.reviewerName = "";
        this.reviewDetail = "";
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }
}
