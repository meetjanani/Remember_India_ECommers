package com.rememberindia.shoppingportal.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.klinker.android.simple_videoview.SimpleVideoView;
import com.rememberindia.shoppingportal.Bean.Shared_Post_Bean.Shared_Post_Details_Bean;
import com.rememberindia.shoppingportal.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class Shared_Post_Adapter extends RecyclerView.Adapter<Shared_Post_Adapter.MyViewHolder> {

    private Context context;
    private List<Shared_Post_Details_Bean> Records;

    public Shared_Post_Adapter(Context context, List<Shared_Post_Details_Bean> productList) {
        this.context = context;
        //  this.listener = listener;
        this.Records = productList;
    }

    @NonNull
    @Override
    public Shared_Post_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pattern_shared_post, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Shared_Post_Adapter.MyViewHolder holder, final int i) {


        if (Records.get(i).getFlag().equals("Video")) {
            holder.videoview.setVisibility(View.GONE);
            try {
                holder.product_image.setImageBitmap(retriveVideoFrameFromVideo(Records.get(i).getURL1() + ""));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

//            holder.product_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    if (holder.videoview.isPlaying()) {
//                        holder.videoview.pause();
//
//                    }
//
//                    holder.videoview.setVideoURI(Uri.parse(Records.get(i).getURL1() + ""));
//
//
//                    holder.videoview.start();
//                    holder.videoview.setVisibility(View.VISIBLE);
//                    holder.product_image.setVisibility(View.GONE);
//
//
//                }
//            });

//            holder.videoview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                        holder.videoview.pause();
//
//                    holder.videoview.setVisibility(View.GONE);
//                    holder.product_image.setVisibility(View.VISIBLE);
//
//
//                }
//            });


            //            holder.videoview.setVideoURI(Uri.parse(Records.get(i).getURL1() + ""));

//            holder.video_view.start(Records.get(i).getURL1() + "");
//            holder.video_view.release();

            //holder.LL_Image.setVisibility(View.GONE);


            //holder.videoview.start();

            holder.Btn_Play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.videoview.isPlaying()) {
                        holder.videoview.pause();
                    }

                    holder.videoview.setVisibility(View.VISIBLE);
                    holder.product_image.setVisibility(View.GONE);
                  holder.videoview.setVideoURI(Uri.parse(Records.get(i).getURL1() + ""));
                    holder.videoview.start();
                    //holder.video_view.pause();
                }
            });

            holder.Btn_Stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.videoview.setVisibility(View.GONE);
                    holder.product_image.setVisibility(View.VISIBLE);
                    if (holder.videoview.isPlaying()) {
                        holder.videoview.pause();
                    }
                }
            });

        } else {

            holder.LL_Btn_Image.setVisibility(View.GONE);
            holder.videoview.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(Records.get(i).getURL1() + "")
                    .placeholder(R.drawable.ic_menu_gallery)
                    .resize(250, 250)
                    .centerCrop()
                    //.transform(transformation)
                    .into(holder.product_image);
        }
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
        LinearLayout LL_Video, LL_Image , LL_Btn_Image;
        SimpleVideoView video_view;
        VideoView videoview;
        Button Btn_Stop, Btn_Play;
        ImageView product_image, Img_Accept, Img_Dispatched, Img_Deliverer;
        View View_Accept, View_Dispatched;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.IMG_1);

            Btn_Play = itemView.findViewById(R.id.Btn_Play);
            Btn_Stop = itemView.findViewById(R.id.Btn_Stop);
            video_view = itemView.findViewById(R.id.video_view_1);
            videoview = itemView.findViewById(R.id.videoview);

            LL_Video = itemView.findViewById(R.id.LL_Video);
            LL_Image = itemView.findViewById(R.id.LL_Image);
            LL_Btn_Image = itemView.findViewById(R.id.LL_Btn_Image);
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
