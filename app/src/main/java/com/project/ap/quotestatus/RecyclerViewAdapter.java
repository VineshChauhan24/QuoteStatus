package com.project.ap.quotestatus;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    ArrayList<Bitmap> toSetBitmaps;


    private Context mContext;

    //default constructor will get names of images and images and context for us
    public RecyclerViewAdapter(ArrayList<Bitmap> bm1, Context mContext) {
        this.toSetBitmaps = bm1;
        this.mContext = mContext;
    }

    //this method is the one responsible for inflaming the views
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate( R.layout.custom_recycle, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        Glide.with(mContext).load(imageurls.get(position)).into(holder.imageView);

//        Glide.with(mContext).asBitmap().load().into(holder.imageView);

        String url = "http://funkyimg.com/i/2PsK1.png";

//        Glide.with(mContext).load(drawMultilineTextToBitmap(mContext , R.drawable.dp ,
//                String.valueOf(Html.fromHtml(quotes.get(position))))).into(holder.imageView).clearOnDetach();

        holder.imageView.setImageBitmap(toSetBitmaps.get(position));

    }

    //tells the adapter numbers of list items in your list
    @Override
    public int getItemCount() {
        return toSetBitmaps.size();
    }


    //holds the widgets, individually , its holding that view and getting ready to add next one
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_image_xml);

        }
    }
}
