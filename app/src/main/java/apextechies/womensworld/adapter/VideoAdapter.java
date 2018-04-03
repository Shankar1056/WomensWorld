package apextechies.womensworld.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apextechies.womensworld.R;
import apextechies.womensworld.allinterface.OnClick;
import apextechies.womensworld.model.VideoModel;
import apextechies.womensworld.webservices.WebServices;

/**
 * Created by Shankar on 4/3/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context ;
    private ArrayList<VideoModel> list;
    private OnClick onClick ;

    public VideoAdapter(Context context, ArrayList<VideoModel> list, OnClick onClick) {
        this.context=context;
        this.list=list;
        this.onClick=onClick;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.videoitem_dot, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("inside","change");
        Picasso.with(context).load(WebServices.VIDEO_LINK+list.get(position).getVideo_id()+"/0.jpg").into(holder.cat_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView cat_image;

        ViewHolder(View itemView) {
            super(itemView);

            cat_image=(ImageView) itemView.findViewById(R.id.cat_image);


        }




    }

}

