package com.example.gabri.licentafrontend.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.gabri.licentafrontend.Fragments.DetailsAboutTrainFragment;
import com.example.gabri.licentafrontend.Fragments.GoogleMapFragment;

/**
 * Created by gabri on 4/2/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                return googleMapFragment;
            case 1:
                DetailsAboutTrainFragment detailsAboutTrainFragment = new DetailsAboutTrainFragment();
                return detailsAboutTrainFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}
