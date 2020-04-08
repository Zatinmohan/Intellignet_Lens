package com.example.intellignetlens.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intellignetlens.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<extra_firebase>listItems;
    private Context context;

    public RecyclerViewAdapter(List<extra_firebase> listItems,Context context){
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_result, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        extra_firebase data = listItems.get(position);
        holder.product_id.setText(data.getProduct_id());
        holder.product_name.setText(data.getProduct_name());
        String img = data.getImages();                                                              //Getting the url of the images
        Picasso.get().load(img).into(holder.product_image);                                         //Downloading the images from that particular url
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView product_name;
        public TextView product_id;
        public ImageView product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id = (TextView)itemView.findViewById(R.id.product_id);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);
        }
    }
}