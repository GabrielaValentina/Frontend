package com.example.gabri.licentafrontend.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.gabri.licentafrontend.Fragments.RouteReview_Fragment;
import com.example.gabri.licentafrontend.Fragments.TrainReview_Fragment;

/**
 * Created by gabri on 5/2/2018.
 */

public class PagerAdapter_Review extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public PagerAdapter_Review(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TrainReview_Fragment trainReview_fragment = new TrainReview_Fragment();
                return trainReview_fragment;
            case 1:
                RouteReview_Fragment routeReview_fragment = new RouteReview_Fragment();
                return routeReview_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}
