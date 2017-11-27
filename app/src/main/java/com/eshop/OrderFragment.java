package com.eshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.adapter.OrderlistAdapter;
import com.eshop.model.Orderdetail;
import com.eshop.model.Orderlist;
import com.eshop.rest.ApiClient;
import com.eshop.rest.ApiInterface;
import com.eshop.rest.MyCallback;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

    LinearLayoutManager layoutManager;

    OrderlistAdapter orderlistAdapter;

    RecyclerView recycle_orderlist;
    ArrayList<Orderdetail> orderdetails;
    TextView txt_nodata;

    AppCompatActivity appCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_order, container, false);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Order");


        appCompatActivity=(AppCompatActivity)getActivity();

        //recyclerview initialization

        recycle_orderlist = (RecyclerView) getActivity().findViewById(R.id.recycle_orderlist);
        recycle_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycle_orderlist.setLayoutManager(layoutManager);

        txt_nodata = (TextView)view.findViewById(R.id.txt_nodata);


        //arraylist

        orderdetails = new ArrayList<>();

        loadorderlist();


    }


    //Account detail set

    Call<Orderlist> call;

    private void loadorderlist() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        call = apiService.orderdetail(Pref.getemail());

        call.enqueue(new MyCallback<Orderlist>((appCompatActivity), MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<Orderlist> value, Response<Orderlist> response) {
                if (response.body().getSuccess() == 1) {

                    orderdetails.addAll(response.body().getOrderdetails());

                    if(orderdetails!=null)
                    {

                        txt_nodata.setVisibility(View.GONE);
                        orderlistAdapter = new OrderlistAdapter(getActivity(), orderdetails);
                        recycle_orderlist.setAdapter(orderlistAdapter);

                    } else
                    {
                        txt_nodata.setVisibility(View.VISIBLE);
                    }


                }

            }

            @Override
            public void onFailed(Call<Orderlist> call, Throwable t) {

            }
        });



    }
}
