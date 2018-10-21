package com.example.nikhil.upcomingmoviesapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.example.nikhil.upcomingmoviesapp.Helpers.Constants;
import com.example.nikhil.upcomingmoviesapp.Models.Movies;
import com.example.nikhil.upcomingmoviesapp.MovieDetailScreen;
import com.example.nikhil.upcomingmoviesapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private Activity context;
    private ArrayList<Movies> mMovieList;

    public MovieListAdapter(Activity context, ArrayList<Movies> mMovieList) {
        this.context = context;
        this.mMovieList = mMovieList;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_movie_list_screen, parent, false);
        return new MovieListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, final int position) {
        final Movies item = mMovieList.get(position);

        final String poster = item.getPoster_path();
        final String title = item.getTitle();
        final String overview = item.getOverview();
        final boolean adult = item.isAdult();
        final DateFormat releasedate = item.getRelease_date();

        String url = Constants.baseUrl + "poster_path/" + poster;

        Picasso.get().load(url).into(holder.iv_movie);

        holder.tv_movie_name.setText(title);
        holder.tv_movie_name_desc.setText(overview);


        holder.mainLayoutMemberMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MovieDetailScreen.class);
                //i.putExtra("customerName", fullname);
                context.startActivity(i);

                Toasty.error(context, "CLicked" + title, Toast.LENGTH_SHORT).show();

                /*Intent intent = new Intent(v.getContext(), ViewMembersActivity.class);
                intent.putExtra("formId", item.getFormId());
                v.getContext().startActivity(intent);*/
            }
        });


       /* holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditCustomerActivity.class);
                intent.putExtra("customerDetails", item);
                context.startActivity(intent);
            }
        });


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseJson(item.getCustomerid(), item.getCustomerfname(), item.getCustomerlname(), item.getCustomerid());
                notifyDataSetChanged();
            }
        });*/

    }


    /*private void parseJson(int customerid, String fname, String lname, int loggedinuser) {

        try {

            String url = "http://160.153.234.207:8080/billshop/webapi/customer/manage?action=" + 0 +
                    "&" + "customerid=" + customerid +
                    "&" + "userid=" + 1 +
                    "&" + "companyid=" + 1 +
                    "&" + "csttype=" + 1 +
                    "&" + "customerfname=" + fname +
                    "&" + "customerlname=" + lname +
                    "&" + "mobile1=" + "delete" +
                    "&" + "mobile2=" + "delete" +
                    "&" + "email=" + "delete" +
                    "&" + "address=" + "delete" +
                    "&" + "logedinuserid=" + loggedinuser;

            Log.e("ChkUrl", url);


            AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            int lastinsertid = response.optInt("result");
                            String message = response.optString("message");
                            int haserror = response.optInt("haserror");

                            if (haserror == 0) {
                                Toasty.success(context, message, Toast.LENGTH_SHORT).show();
                               *//* startActivity(new Intent(AddCustomerActivity.this,CustomerActivity.class));
                                finish();*//*
                                notifyDataSetChanged();

                            } else {
                                Toasty.success(context, message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (Exception ex) {
            Log.e("parseJson", "onCreate: ", ex);
        }
    }*/

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<Movies> newList) {
        mMovieList = new ArrayList<>();
        mMovieList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout mainLayoutMemberMovie;
        ImageView iv_movie;
        TextView tv_movie_name, tv_type, tv_rel_date;
        TextView tv_movie_name_desc;
        View v_goforward;


        public MovieListViewHolder(View itemView) {
            super(itemView);

            mainLayoutMemberMovie = itemView.findViewById(R.id.mainLayoutMemberMovie);
            iv_movie = itemView.findViewById(R.id.iv_movie);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_rel_date = itemView.findViewById(R.id.tv_rel_date);
            tv_movie_name_desc = itemView.findViewById(R.id.tv_movie_name_desc);
            v_goforward = itemView.findViewById(R.id.v_goforward);


        }


    }
    //endregion


}
