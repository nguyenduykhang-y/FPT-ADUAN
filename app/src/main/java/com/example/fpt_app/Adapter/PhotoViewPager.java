package com.example.fpt_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fpt_app.R;

import java.util.List;

public class PhotoViewPager extends PagerAdapter {

    private List<Silder> mSilders;

    public PhotoViewPager(List<Silder> mSilders) {
        this.mSilders = mSilders;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.itemanh, container, false);

        ImageView imageView = view.findViewById(R.id.img_silder);
        Silder silder = mSilders.get(position);
        imageView.setImageResource(silder.getResourceId());
        //add view
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (mSilders != null){
            return mSilders.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
