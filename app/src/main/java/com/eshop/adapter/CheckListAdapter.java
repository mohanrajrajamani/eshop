package com.eshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.eshop.CheckListActivity;
import com.eshop.R;
import com.eshop.activeandroid.CheckList;
import com.eshop.model.Product;
import com.eshop.rest.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 09-11-2017.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder>{

    List<CheckList> stringList;
    CheckListActivity checkListActivity;

    public CheckListAdapter(CheckListActivity checkListActivity, List<CheckList> stringList) {
        this.stringList=stringList;
        this.checkListActivity=checkListActivity;


    }

    @Override
    public CheckListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(checkListActivity)
                .inflate(R.layout.item_check_list, parent, false);
        return new CheckListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CheckListAdapter.ViewHolder holder, int position) {


        CheckList checkList = stringList.get(position);
        holder.pd_title.setText(checkList.getProduct_name());
        holder.pd_descritiption.setText(checkList.getProduct_description().trim());


        int total=Integer.valueOf(checkList.getPrice())*Integer.valueOf(checkList.getProduct_count());

        holder.pd_price.setText("₹"+" "+ total);
            holder.pd_qty.setText("QTY:"+" "+ checkList.getProduct_count());


        Picasso.with(checkListActivity)
                .load(ApiClient.URL_ACCOUNT_PHOTO + checkList.getImage())
                .placeholder(R.drawable.ecom_logo)   // optional
                .error(R.drawable.ecom_logo)      // optiona
                .into(holder.pd_image);



    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_viewproduct;
        ImageView pd_image;
        TextView pd_title;
        TextView pd_descritiption;
        TextView pd_price;
        TextView pd_qty;


        public ViewHolder(View itemView) {
            super(itemView);

            btn_viewproduct = itemView.findViewById(R.id.btn_viewproduct);
            pd_image = itemView.findViewById(R.id.pd_image);
            pd_title = itemView.findViewById(R.id.pd_title);
            pd_descritiption = itemView.findViewById(R.id.pd_descritiption);
            pd_price = itemView.findViewById(R.id.pd_price);
            pd_qty = itemView.findViewById(R.id.pd_qty);

            btn_viewproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CheckList checkList = stringList.get(getAdapterPosition());

                    stringList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    new Delete().from(CheckList.class).where("product_id = ?", checkList.getProduct_id()).execute();

                }
            });

        }

    }
}
