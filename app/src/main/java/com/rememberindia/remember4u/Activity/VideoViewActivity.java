package com.rememberindia.remember4u.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.VideoView;

import com.rememberindia.remember4u.R;

import static com.rememberindia.remember4u.Utility.Common_Class.BASE_URL_YOUTUBE_PREFIX;
import static com.rememberindia.remember4u.Utility.Common_Class.BASE_URL_YOUTUBE_SUFIX;

public class VideoViewActivity extends AppCompatActivity {

    // SimpleExoPlayer simpleExoPlayer  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        WebView webViewMedia = findViewById(R.id.webViewMedia);
        VideoView videoView = findViewById(R.id.videoView);
        TextView Message_1 = findViewById(R.id.Message_1);
        String flag = getIntent().getStringExtra("Flag");
        Message_1.setText( getIntent().getStringExtra("Message_1"));


        if(flag.equals("Video")){
            webViewMedia.setVisibility(View.GONE);
            videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("URL_1") + ""));
            videoView.start();
        } else {
            videoView.setVisibility(View.GONE);
            webViewMedia.getSettings().setJavaScriptEnabled(true);
            webViewMedia.setWebViewClient(new WebViewClient());
            webViewMedia.loadData(
                    BASE_URL_YOUTUBE_PREFIX + getIntent().getStringExtra("URL_2") + BASE_URL_YOUTUBE_SUFIX,
                    "text/html",
                    "utf-8"
            );
        }

    }
}
