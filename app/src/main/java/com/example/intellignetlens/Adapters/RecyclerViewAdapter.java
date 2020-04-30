package com.example.intellignetlens.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intellignetlens.Activities.CompareActivity;
import com.example.intellignetlens.Activities.DetailActivity;
import com.example.intellignetlens.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<extra_firebase>listItems;
    private Context context;
    private TextView compare;

    static int m=0;
    ArrayList<extra_firebase>selected_items = new ArrayList<extra_firebase>();

    public RecyclerViewAdapter(List<extra_firebase> listItems,Context context,TextView compare){
        m=0;
        this.listItems = listItems;
        this.context = context;
        this.compare = compare;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_result, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final extra_firebase data = listItems.get(position);
        final extra_firebase object = new extra_firebase();
        holder.product_id.setText(data.getProduct_id());
        holder.product_name.setText(data.getProduct_name());
        String img = data.getImages();                                                              //Getting the url of the images
        Picasso.get().load(img).into(holder.product_image);                                         //Downloading the images from that particular url

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    m++;
                    object.addition(data.getProduct_name(),data.getImages());
                    selected_items.add(object);
                }
                else {
                    m--;
                    selected_items.remove(object);

                }

                if(m==2) {
                    compare.setTextColor(Color.parseColor("#e52d27"));

                }
                if(m!=2)
                    compare.setTextColor(Color.parseColor("#737373"));
            }

        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView product_name;
        public TextView product_id;
        public ImageView product_image;
        public CheckBox checkBox;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            final Context c = itemView.getContext();

            product_id = (TextView)itemView.findViewById(R.id.product_id);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);

            checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);


            compare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(compare.getCurrentTextColor()==Color.parseColor("#e52d27")) {            //Checking the Color

                        if(m==2) {
                            Intent intent = new Intent(context,CompareActivity.class);
                            intent.putExtra("First Name",selected_items.get(0).getProduct_name());
                            intent.putExtra("Second Name",selected_items.get(1).getProduct_name());
                            intent.putExtra("First Image",selected_items.get(0).getImages());
                            intent.putExtra("Second Image",selected_items.get(1).getImages());
                            context.startActivity(intent);
                        }
                    }
                    else
                        Toast.makeText(context, "Only 2 products can be compared at a time", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  int pos = getAdapterPosition();
                  Intent intent = new Intent(context,DetailActivity.class);
                  intent.putExtra("ProductName",listItems.get(pos).getProduct_name());
                  intent.putExtra("ProductID",listItems.get(pos).getProduct_id());
                  intent.putExtra("ProductDesp",listItems.get(pos).getDescription());
                  intent.putExtra("ProductImage",listItems.get(pos).getImages());
                  intent.putExtra("ProductURL",listItems.get(pos).getUrl());
                  context.startActivity(intent);
                }
            });
        }
    }
}