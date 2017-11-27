package com.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.eshop.ProductfullviewActivity;
import com.eshop.R;
import com.eshop.model.Product;
import com.eshop.rest.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Admin on 07-11-2017.
 */

public class ProductlistAdapter extends RecyclerView.Adapter<ProductlistAdapter.ViewHolder> {

    Context context;
    ArrayList<Product> products;

    public ProductlistAdapter(Context context, ArrayList<Product> products) {

        this.context = context;
        this.products = products;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.productlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Product product = products.get(position);
        holder.pd_title.setText(product.getName());
        holder.pd_descritiption.setText(product.getDescription().trim());
        holder.pd_price.setText("₹"+" "+ product.getPrice());

        Log.v("ebfefehfbefevf"," "+ApiClient.URL_ACCOUNT_PHOTO + product.getImages().get(0).getImage());


        Picasso.with(context)
                .load(ApiClient.URL_ACCOUNT_PHOTO + product.getImages().get(0).getImage())
                .placeholder(R.drawable.ecom_logo)   // optional
                .error(R.drawable.ecom_logo)      // optiona
                .into(holder.pd_image);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_viewproduct;
        ImageView pd_image;
        TextView pd_title;
        TextView pd_descritiption;
        TextView pd_price;


        public ViewHolder(View itemView) {
            super(itemView);


            btn_viewproduct = itemView.findViewById(R.id.btn_viewproduct);
            pd_image = itemView.findViewById(R.id.pd_image);
            pd_title = itemView.findViewById(R.id.pd_title);
            pd_descritiption = itemView.findViewById(R.id.pd_descritiption);
            pd_price = itemView.findViewById(R.id.pd_price);

            btn_viewproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Product product = products.get(getAdapterPosition());

                    Intent btn_viewproduct = new Intent(context, ProductfullviewActivity.class);
                    btn_viewproduct.putExtra("product_id",product.getId());
                    btn_viewproduct.putExtra("product_name",product.getName());
                    btn_viewproduct.putExtra("product_description",product.getDescription());
                    btn_viewproduct.putExtra("product_price",product.getPrice());
                    btn_viewproduct.putExtra("product_image",ApiClient.URL_ACCOUNT_PHOTO + product.getImages().get(0).getImage());
                    context.startActivity(btn_viewproduct);
                }
            });
        }


    }
}
