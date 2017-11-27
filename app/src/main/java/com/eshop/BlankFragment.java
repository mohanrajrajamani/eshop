package com.eshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.eshop.model.BasicResponse;
import com.eshop.model.Catagorey;
import com.eshop.rest.MyCallback;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;


public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ExpandableListViewAdapter expandableListViewAdapter;
    ExpandableListView expandable_recyclerview;
    ArrayList<Catagorey> faqLists;

    int previousGroup;

    AppCompatActivity appCompatActivity;


    //private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        getActivity().setTitle("ESHOP");

        appCompatActivity = (AppCompatActivity) getActivity();

        faqLists = new ArrayList<>();


        expandable_recyclerview = view.findViewById(R.id.expandable_recyclerview);

        //defining the behavior when any group is clicked in expandable listview
        expandable_recyclerview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    if (groupPosition != previousGroup) {
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }

                parent.smoothScrollToPosition(groupPosition);
                return true;
            }

        });

        loadCatagories();


        return view;


    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }


    Call<BasicResponse> load_Catagories;

    public void loadCatagories() {

        this.load_Catagories = AppController.apiInterface.loadFaq();
        this.load_Catagories.enqueue(new MyCallback<BasicResponse>(appCompatActivity, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<BasicResponse> value, Response<BasicResponse> response) {

                BasicResponse basicResponse = response.body();
                if (basicResponse.getSuccess() == 1) {
                    faqLists.addAll(basicResponse.getCatagories());
                    expandableListViewAdapter = new ExpandableListViewAdapter(appCompatActivity, faqLists);
                    expandable_recyclerview.setAdapter(expandableListViewAdapter);
                } else {
                    Toast.makeText(getContext(), basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailed(Call<BasicResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroy() {
        if(load_Catagories!=null){
            load_Catagories.cancel();
        }

        super.onDestroy();
    }
}
