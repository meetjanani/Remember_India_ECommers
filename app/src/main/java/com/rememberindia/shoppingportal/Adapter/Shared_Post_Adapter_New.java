package com.rememberindia.shoppingportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.rememberindia.shoppingportal.Activity.VideoViewActivity;
import com.rememberindia.shoppingportal.Bean.Shared_Post_Bean.Shared_Post_Details_Bean;
import com.rememberindia.shoppingportal.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class Shared_Post_Adapter_New extends RecyclerView.Adapter<Shared_Post_Adapter_New.MyViewHolder> {

    private Context context;
    private List<Shared_Post_Details_Bean> Records;

    public Shared_Post_Adapter_New(Context context, List<Shared_Post_Details_Bean> productList) {
        this.context = context;
        //  this.listener = listener;
        this.Records = productList;
    }

    @NonNull
    @Override
    public Shared_Post_Adapter_New.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pattern_media_slide_show, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Shared_Post_Adapter_New.MyViewHolder holder, final int i) {

        if (Records.get(i).getFlag().equals("YouTubeVideo")) {
            Picasso.with(context)
                    .load("https://img.youtube.com/vi/" + Records.get(i).getURL2() + "/0.jpg")
                    .placeholder(R.drawable.ic_menu_gallery)
                    .resize(250, 250)
                    .centerCrop()
                    //.transform(transformation)
                    .into(holder.product_image);
            holder.Btn_Play.setVisibility(View.VISIBLE);

            // https://img.youtube.com/vi/hQ6E1TkfzKA/0.jpg
        } else if (Records.get(i).getFlag().equals("Video")) {
            Picasso.with(context)
                    .load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT39_dYRRxsghk5Az0NYpSqE2f6oE9SGR-7dqcSqf-lOzHVo7tc")
                    .placeholder(R.drawable.ic_menu_gallery)
                    .resize(250, 250)
                    .centerCrop()
                    //.transform(transformation)
                    .into(holder.product_image);
            holder.Btn_Play.setVisibility(View.VISIBLE);
            try {
                holder.product_image.setImageBitmap(retriveVideoFrameFromVideo(Records.get(i).getURL1() + ""));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Picasso.with(context)
                    .load(Records.get(i).getURL1() + "")
                    .placeholder(R.drawable.ic_menu_gallery)
                    .resize(250, 250)
                    .centerCrop()
                    //.transform(transformation)
                    .into(holder.product_image);
        }

        holder.Btn_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, VideoViewActivity.class);
                in.putExtra("Flag", Records.get(i).getFlag());
                in.putExtra("URL_1", Records.get(i).getURL1());
                in.putExtra("URL_2", Records.get(i).getURL2());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Records.size();
    }

    public Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button Btn_Play;
        ImageView product_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.IMG_1);
            Btn_Play = itemView.findViewById(R.id.Btn_Play);
        }
    }

    public class LoadVideoThumbnail extends AsyncTask<String, Object, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... objectURL) {
            //return ThumbnailUtils.createVideoThumbnail(objectURL[0], Thumbnails.MINI_KIND);
            return ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(objectURL[0], MediaStore.Images.Thumbnails.MINI_KIND), 100, 100);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //img.setImageBitmap(result);
        }

    }

}
