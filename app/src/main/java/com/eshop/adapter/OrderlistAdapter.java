package com.eshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.model.Orderdetail;
import com.eshop.rest.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Admin on 07-11-2017.
 */

public class OrderlistAdapter extends RecyclerView.Adapter<OrderlistAdapter.ViewHolder> {

    Context context;

    ArrayList<Orderdetail> orderdetails;

    public OrderlistAdapter(Context context, ArrayList<Orderdetail> orderdetails) {

        this.context = context;
        this.orderdetails = orderdetails;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.orderlist, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (orderdetails.get(position).getName() != null) {

            holder.txtheader.setText(orderdetails.get(position).getName());
        }

        if (orderdetails.get(position).getPrice() != null) {

            holder.txtprice.setText("₹"+ orderdetails.get(position).getPrice());
        }

        if (orderdetails.get(position).getStatus() != null) {

            holder.txtstatus.setText(orderdetails.get(position).getStatus());
        }
        if (orderdetails.get(position).getCreated_at() != null) {

            holder.txtstatusdate.setText(orderdetails.get(position).getCreated_at());
        }

        if (orderdetails.get(position).getCategory() != null) {

            holder.txt_category.setText(orderdetails.get(position).getCategory());
        }


        Picasso.with(context)
                .load(ApiClient.URL_ACCOUNT_PHOTO + orderdetails.get(0).getImage())
                .placeholder(R.drawable.ecom_logo)   // optional
                .error(R.drawable.ecom_logo)      // optiona
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return orderdetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtstatus, txtprice, txtheader, txtstatusdate, txt_category;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            txtstatus = (TextView) itemView.findViewById(R.id.txt_status);
            txtprice = (TextView) itemView.findViewById(R.id.txt_price);
            txtheader = (TextView) itemView.findViewById(R.id.txt_header);
            txtstatusdate = (TextView) itemView.findViewById(R.id.txt_statusdate);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
            imageView = itemView.findViewById(R.id.imageView);

        }


    }
}
