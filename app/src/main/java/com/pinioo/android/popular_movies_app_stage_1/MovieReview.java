package com.pinioo.android.popular_movies_app_stage_1;

/**
 * Created by bhatt on 22-05-2017.
 */

class MovieReview {
    String AuthorName;
    String Review;

    MovieReview(String AuthorName,String Review){

        this.AuthorName = AuthorName;
        this.Review = Review;

    }

    public String getAuthorName(){
        return AuthorName;
    }
    public String getReview(){
        return Review;
    }
}
