package com.eshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.model.Catagorey;
import com.eshop.model.SubCatagories;

import java.util.List;


/**
 * Created by Admin on 19-09-2017.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    // TODO: config - main drawer category icon tint
    final boolean categoryIconTint = false;

    private LayoutInflater layoutInflater;
    Context context;
    int count;
    private List<Catagorey> catagoreys;
    private SubCatagories singleChild = new SubCatagories();

    public ExpandableListViewAdapter(Context context, List<Catagorey> catagoreys) {

        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.catagoreys = catagoreys;


    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return catagoreys.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return catagoreys.get(i).getSubCatagories().size();

    }

    @Override
    public Object getGroup(int i) {
        return catagoreys.get(i).getName();
    }

    @Override
    public SubCatagories getChild(int i, int i1) {

        List<SubCatagories> subCategories = catagoreys.get(i).getSubCatagories();
        return subCategories.get(i1);

    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.navi_cat, viewGroup, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.cat_desc_1);
        ImageView ivIndicator = (ImageView) view.findViewById(R.id.navi_list_indicator);
        textView.setText(getGroup(i).toString());

        ImageView ivIcon = (ImageView) view.findViewById(R.id.navi_icon);


        if (isExpanded) {
            ivIcon.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
            textView.setBackgroundColor(context.getResources().getColor(R.color.expBackground));
            textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            ivIcon.setColorFilter((context.getResources().getColor( R.color.colorAccent)), PorterDuff.Mode.SRC_IN);


        } else {
            textView.setBackgroundColor(context.getResources().getColor(R.color.expBackgroundWhite));
            ivIcon.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            textView.setTextColor(context.getResources().getColor(R.color.blackColor));
            ivIcon.setColorFilter((context.getResources().getColor( R.color.blackColor)), PorterDuff.Mode.SRC_IN);

        }





        return view;

    }


    @Override
    public View getChildView(int i, int i1, boolean isExpanded, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.navi_cat_sub, viewGroup, false);

        }

        singleChild = getChild(i, i1);

        TextView childSubCategoryquestion = (TextView) view.findViewById(R.id.cat_question);
        TextView childSubCategoryanswer = (TextView) view.findViewById(R.id.cat_answer);


       /* if (Build.VERSION.SDK_INT >= 24) {

            childSubCategoryanswer.setText(Html.fromHtml(singleChild.getName().trim(), Html.FROM_HTML_MODE_LEGACY));

        } else {
            childSubCategoryanswer.setText(Html.fromHtml(singleChild.getName().trim()));
        }*/
        childSubCategoryquestion.setText(singleChild.getName());

        childSubCategoryquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,HomeActivity.class);
                intent.putExtra("subcatagorey",singleChild.getName());
                intent.putExtra("catagorey",singleChild.getParent());
                context.startActivity(intent);


            }
        });



        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

}
