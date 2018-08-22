package com.timemasta.nyctourguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewHolder> {

    private List<Model> modelList;
    public TextView  h3Span, link;

    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageIcon;
        public TextView h3;

        public MyViewHolder(View view) {
            super(view);
            h3 = (TextView) view.findViewById(R.id.title);
            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
        }


    }


    public ModelAdapter(List<Model> moviesList, Context ctx,OnItemClickListener mOnItemClickListener ) {
        this.modelList = moviesList;
        this.context = ctx;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        Model model = modelList.get(position);
        holder.h3.setText(model.getH3());
        Picasso.get().load(model.getImagesrc())
                .resize(120, 120)
                .into(holder.imageIcon);
        holder.h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}