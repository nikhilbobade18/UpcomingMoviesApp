package com.example.nikhil.upcomingmoviesapp;

import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nikhil.upcomingmoviesapp.Adapters.MovieListAdapter;
import com.example.nikhil.upcomingmoviesapp.Models.Movies;
import com.example.nikhil.upcomingmoviesapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;

public class MovieListScreen extends AppCompatActivity implements View.OnClickListener {

    String TAG="homeAct";

    RecyclerView rec_view_movies;
    MovieListAdapter movieListAdapter;
    ArrayList<Movies> movies;

    View viewinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_screen);

        AndroidNetworking.initialize(this);
        movies = new ArrayList<>();


        hookUp();
        btnClickListener();

        parseJson();

        rec_view_movies.setLayoutManager(new LinearLayoutManager(this));
        rec_view_movies.setItemAnimator(new DefaultItemAnimator());
    }


    void hookUp() {
        viewinfo = findViewById(R.id.viewinfo);
        rec_view_movies = findViewById(R.id.rec_view_movies);


    }

    void btnClickListener() {
        viewinfo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewinfo:

                startActivity(new Intent(MovieListScreen.this, MovieInfoScreen.class));

                break;
        }
    }


    private void parseJson() {

        try {


            AndroidNetworking.post("https://api.themoviedb.org/3/movie/upcoming?api_key=b7cd3340a794e5a2f35e3abb820b497f")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                try {
                                    JSONArray jArr = response.getJSONArray("results");

                                    for (int i=0; i < jArr.length(); i++) {

                                        JSONObject member = jArr.getJSONObject(i);

                                        int vote_count=member.getInt("vote_count");
                                        int id=member.getInt("id");


                                        String title=member.getString("title");
                                        String poster_path=member.getString("poster_path");
                                        String original_language = member.getString("original_language");
                                        String original_title = member.getString("original_title");
                                        String backdrop_path = member.getString("backdrop_path");
                                        String overview = member.getString("overview");

                                        boolean video=member.getBoolean("video");
                                        boolean adult=member.getBoolean("adult");

                                        double vote_average=member.getInt("vote_average");
                                        double popularity=member.getInt("popularity");

                                        String release_date=member.getString("release_date");


                                        movies.add(new Movies( vote_count,id,title,poster_path,original_language,original_title,
                                                backdrop_path,overview,video,adult,vote_average,
                                                popularity,release_date));
                                        Log.e("ChkMovies",""+movies);
                                    }

                                } catch (Exception ex) {
                                    Log.e(TAG, "onResponse: ", ex);
                                }
                                movieListAdapter = new MovieListAdapter(MovieListScreen.this, movies);
                                rec_view_movies.setAdapter(movieListAdapter);

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (Exception ex) {
            Log.e(TAG, "onCreate: ", ex);
        }
    }
}
