package com.myapp.moviekart;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieDetail extends AppCompatActivity {

    String trailer;
    String videoId;
    VideoView movie_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Getting the info from intent
        String name = getIntent().getStringExtra("name");
        String link = getIntent().getStringExtra("link");
        String category = getIntent().getStringExtra("category");
        String duration = getIntent().getStringExtra("duration");
        String description = getIntent().getStringExtra("description");
        String thumbnail = getIntent().getStringExtra("thumbnail");
        trailer= getIntent().getStringExtra("trailer");

        // Binding the views
        ImageView banner_movie_detail= findViewById(R.id.banner_movie_detail);
        TextView tv_movie_name_detail= findViewById(R.id.tv_movie_name_detail);
        TextView tv_movie_category_detail= findViewById(R.id.tv_movie_category_detail);
        TextView tv_movie_duration_detail= findViewById(R.id.tv_movie_duration_detail);
        TextView tv_movie_desc= findViewById(R.id.tv_movie_desc);
        movie_view= findViewById(R.id.movie_view);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.movie_trailer_view);
        getLifecycle().addObserver(youTubePlayerView);

        Button btn_play_trailer= findViewById(R.id.btn_play_trailer);

        // Setting views to their details
        tv_movie_name_detail.setText(name);
        tv_movie_category_detail.setText(category);
        tv_movie_duration_detail.setText(duration);
        tv_movie_desc.setText(description);
        Picasso.get().load(thumbnail).into(banner_movie_detail);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());



        btn_play_trailer.setBackgroundColor(Color.TRANSPARENT);

        btn_play_trailer.setOnClickListener(v -> {

            try {
                banner_movie_detail.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                btn_play_trailer.setVisibility(View.GONE);

                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(YouTubePlayer youTubePlayer) {
                        videoId=getId(trailer);
                        youTubePlayer.loadVideo(videoId,0);
                    }
                }); 
            }catch (Exception e){
                Toast.makeText(MovieDetail.this, "OOPS!, Looks like trailer is not available..", Toast.LENGTH_SHORT).show();
            }
            


        });
        


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_play);
        fab.setOnClickListener(view -> {

//            movie_view.setVisibility(view.VISIBLE);
//            youTubePlayerView.setVisibility(view.GONE);


//            String LINK = null;
//            try {
//                LINK = downloadUrl(link);
//            } catch (IOException p) {
                            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
                Toast.makeText(MovieDetail.this, "Please use Google-Drive, The movie is loading....", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
//                Toast.makeText(MovieDetail.this, p.getMessage(), Toast.LENGTH_SHORT).show();
            }
////            VideoView mVideoView  = (VideoView) findViewById(R.id.videoview);
////            MediaController mc = new MediaController(this);
////            mc.setAnchorView(movie_view);
////            mc.setMediaPlayer(movie_view);
////            Uri video = Uri.parse(LINK);
////            movie_view.setMediaController(mc);
////            movie_view.setVideoURI(video);
////            movie_view.start();
//            Toast.makeText(MovieDetail.this, LINK, Toast.LENGTH_SHORT).show();
//
        );
    }

    public String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            String contentAsString = readIt(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("fmt_stream_map")) {
                sb.append(line + "\n");
                break;
            }
        }
        reader.close();
        String result = decode(sb.toString());
        String[] url = result.split("\\|");
        return url[1];
    }

    public String decode(String in) {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while (index > -1) {
            int length = working.length();
            if (index > (length - 6)) break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring, 16);
            String stringStart = working.substring(0, index);
            String stringEnd = working.substring(numFinish);
            working = stringStart + ((char) number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }


    public String getId(String link){
        String id="";
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);
        if(matcher.find()){
            id = matcher.group(1);
        }
        return id;
    }
}