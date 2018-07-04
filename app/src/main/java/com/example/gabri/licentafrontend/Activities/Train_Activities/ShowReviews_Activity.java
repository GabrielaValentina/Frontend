package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.gabri.licentafrontend.Adapters.PagerAdapter_Review;
import com.example.gabri.licentafrontend.Fragments.RouteReview_Fragment;
import com.example.gabri.licentafrontend.Fragments.TrainReview_Fragment;
import com.example.gabri.licentafrontend.R;

/**
 * Created by gabri on 5/2/2018.
 */

public class ShowReviews_Activity extends AppCompatActivity implements TrainReview_Fragment.OnFragmentInteractionListener,
        RouteReview_Fragment.OnFragmentInteractionListener{

         private TabLayout tabLayout;
         private ViewPager viewPager;
         private PagerAdapter_Review pagerAdapter;

        private String url;

        @Override
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.show_reviews_activity);

                url = getIntent().getExtras().getString("url");
                //set tabs
                this.tabLayout = (TabLayout) findViewById(R.id.tabLayoutSHOWREVIEWACTIVITY);
                tabLayout.addTab(tabLayout.newTab().setText("Tren"));
                tabLayout.addTab(tabLayout.newTab().setText("Ruta"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                viewPager = (ViewPager) findViewById(R.id.viewPagerSHOWREVIEWACTIVITY);
                pagerAdapter = new PagerAdapter_Review(getSupportFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

                }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

                }
                });
                }

        @Override
        public void onFragmentInteraction(Uri uri) {

                }

        public String getUrl(){
                return url;
        }
}
