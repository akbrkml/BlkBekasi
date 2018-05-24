package com.barateknologi.bbplk_cevest.Kejuruan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.barateknologi.bbplk_cevest.Activity_Absen;
import com.barateknologi.bbplk_cevest.Kejuruan_Activity;
import com.barateknologi.bbplk_cevest.MainActivityBaru;
import com.barateknologi.bbplk_cevest.R;

/**
 * Created by Ratan on 7/27/2015.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items =4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
            View x =  inflater.inflate(R.layout.tab_layout_daftar,null);
            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        final int[] ICONS = new int[]{
                R.drawable.spam,
                R.drawable.senta,
                R.drawable.sent,
                R.drawable.ss,

        };

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(ICONS[0]);
                tabLayout.getTabAt(1).setIcon(ICONS[1]);
                tabLayout.getTabAt(2).setIcon(ICONS[2]);
                tabLayout.getTabAt(3).setIcon(ICONS[3]);
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
              case 0 : return new SentFragment();
              case 1 : return new Kejuruan();
              case 2 : return new SubKejuruan();
              case 3 : return new ProgramPelatihan();

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
                    return "PROFIL";
                case 1:
                    return "KEJURUAN";
                case 2 :
                    return "SUB KEJURUAN"; //by user ...dst
                case 3 :
                    return "PROG. PELATIHAN";


            }
                return null;
        }
    }


}
