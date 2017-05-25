package com.pinioo.android.popular_movies_app_stage_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhatt on 22-05-2017.
 */

public class ListAdpater extends ArrayAdapter<MovieReview> {

    public ListAdpater(@NonNull Context context, ArrayList<MovieReview> MovieReview) {
        super(context, 0,MovieReview);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listviewforreview,parent,false);
        }

        MovieReview movieReview = getItem(position);

        TextView AuthorId = (TextView)listItemView.findViewById(R.id.AuthorId);
        AuthorId.setText(movieReview.getAuthorName());

        TextView reviewBox = (TextView)listItemView.findViewById(R.id.reviewBox);
        reviewBox.setText(movieReview.getReview());


        return listItemView;
    }
}
