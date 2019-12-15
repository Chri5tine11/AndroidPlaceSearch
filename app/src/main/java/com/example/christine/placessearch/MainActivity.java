package com.example.christine.placessearch;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardt);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.getTabAt(0).setIcon(R.drawable.searchicon);
        //tabLayout.getTabAt(1).setIcon(R.drawable.heart_fill_white);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
       // mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }








    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable;
            String title;
            switch (position) {
                case 0: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.searchicon);
                    title = "SEARCH";
                    break;
                case 1: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill_white);
                    title = "FAVORITES";
                    break;
                default: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.searchicon);
                    title = "SEARCH";
                    break;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            SpannableString spannableString = new SpannableString(" " + title);
            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    searchtab tab1 = new searchtab();
                    return tab1;
                case 1:
                    favoritetab tab2 = new favoritetab();
                    return tab2;

                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}