package com.barateknologi.bbplk_cevest.Utama;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barateknologi.bbplk_cevest.Activity_Loker;
import com.barateknologi.bbplk_cevest.Utama.Berita;
import com.barateknologi.bbplk_cevest.Kejuruan.Kejuruan;
import com.barateknologi.bbplk_cevest.Kejuruan.ProgramPelatihan;
import com.barateknologi.bbplk_cevest.Kejuruan.SentFragment;


import com.barateknologi.bbplk_cevest.R;
import com.barateknologi.bbplk_cevest.frommcpm.HomeFragment;
import com.barateknologi.bbplk_cevest.frommcpm.TabFragment;

/**
 * Created by Ratan on 7/27/2015.
 */
public class TabUtamaFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items =3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
            View x =  inflater.inflate(R.layout.tab_layout,null);
            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        final int[] ICONS = new int[]{
                R.drawable.ic_menu_home,
                R.drawable.edit,
                R.drawable.edit,


        };
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                    tabLayout.setupWithViewPager(viewPager);

                tabLayout.getTabAt(0).setIcon(ICONS[0]);
                tabLayout.getTabAt(1).setIcon(ICONS[1]);
                tabLayout.getTabAt(2).setIcon(ICONS[2]);


            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
          switch (position){
              case 0 : return new Berita();
              case 1 : return new com.barateknologi.bbplk_cevest.Utama.Activity_Loker();
              case 2 : return new TabFragment();

          }
        return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "News";
                case 1 :
                    return "Job Vacancy"; //by user ...dst
                case 2 :
                     return "Information";


            }
                return null;
        }
    }


}
