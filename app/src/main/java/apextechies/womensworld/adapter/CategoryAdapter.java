package apextechies.womensworld.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import apextechies.womensworld.R;
import apextechies.womensworld.model.CategoryModel;

/**
 * Created by shankar on 3/4/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context ;
    private ArrayList<CategoryModel> list;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> list) {
        this.context=context;
        this.list=list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dot, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("inside","change");
        //Glide.with(context).load(list.get(position).getProduct_image()).into(holder.img_dot);

        holder.cat_name.setText(list.get(position).getCat_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //shopDescription.sendpos(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder  {

        TextView cat_name;

        ViewHolder(View itemView) {
            super(itemView);

            cat_name=(TextView) itemView.findViewById(R.id.cat_name);


        }




    }

}

