package com.eshop;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Admin on 07-11-2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;

    int[] mResources = {
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch
    };


    public CustomPagerAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.home_items);
        imageView.setImageResource(mResources[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
