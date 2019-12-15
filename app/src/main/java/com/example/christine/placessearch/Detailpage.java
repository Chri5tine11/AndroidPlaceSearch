package com.example.christine.placessearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class Detailpage extends AppCompatActivity {
    String weburl="kong";
    String toolbartext;
    String placeaddress;
    String icondizhi;
    String tturl;
    String dtjilu;
    String dtplaceadd;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static String placeidtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);


        //final String placeidtext;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardt);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        toolbartext= intent.getStringExtra(liebiaoActivity.extra_placename);
        placeaddress= intent.getStringExtra(liebiaoActivity.extra_placeadd);
        toolbar.setTitle(toolbartext);
        icondizhi = intent.getStringExtra(liebiaoActivity.extra_iconicon);
        placeidtext = intent.getStringExtra(liebiaoActivity.extra_placeid);

       dtjilu="{\"icon\":\""+icondizhi+"\","+"\"placename\":\""+toolbartext+"\","+"\"address\":\""+placeaddress+"\","+"\"placeid\":\""+placeidtext+"\"}";



        final String urlindex = "file:///android_asset/www/indexdt.html";
        WebView dtweb = (WebView) findViewById(R.id.dtyincangweb);
        dtweb.getSettings().setJavaScriptEnabled(true);
        dtweb.loadUrl(urlindex);

        dtweb.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public String placeiddt() {
                return placeidtext;
            }
        }, "chuandt");

        dtweb.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void huoqudizhi(String dizhi) {
                dtplaceadd=dizhi;

            }
        }, "huodedizhi");

        dtweb.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void huoquweb(String website) {
                weburl=website;
                //Log.d("wwwwwwwwwwwww",weburl);
            }
        }, "huodeweb");

        dtweb.setWebChromeClient(new WebChromeClient());

//////////////////////////
        Infotab infofg = new Infotab();
        Bundle bundlein = new Bundle();
        bundlein.putString("infodata",placeidtext);
        infofg.setArguments(bundlein);

        FragmentManager fManagerin=getSupportFragmentManager();
        FragmentTransaction fTransactionin=fManagerin.beginTransaction();
        fTransactionin.add(R.id.container,infofg,"infotag");
        fTransactionin.commit();
/////////////////////////////
        Photos photosfg = new Photos();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",placeidtext);
        photosfg.setArguments(bundle);

        FragmentManager fManager=getSupportFragmentManager();
        FragmentTransaction fTransaction=fManager.beginTransaction();
        fTransaction.add(R.id.container,photosfg,"myTag");
        fTransaction.commit();
//////////////////////////
        Maptab mapfg = new Maptab();
        Bundle bundlem = new Bundle();
        bundlem.putString("mapdata",placeidtext);
        mapfg.setArguments(bundlem);

        FragmentManager fManagerm=getSupportFragmentManager();
        FragmentTransaction fTransactionm=fManagerm.beginTransaction();
        fTransactionm.add(R.id.container,mapfg,"maptag");
        fTransactionm.commit();
/////////////////////////////
        Reviewstab rvfg = new Reviewstab();
        Bundle bundlerv = new Bundle();
        bundlerv.putString("rvdata",placeidtext);
        rvfg.setArguments(bundlerv);

        FragmentManager fManagerrv=getSupportFragmentManager();
        FragmentTransaction fTransactionrv=fManagerrv.beginTransaction();
        fTransactionrv.add(R.id.container,rvfg,"rvtag");
        fTransactionrv.commit();
/////////////////////////////

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailpage, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        SharedPreferences sharep;
        sharep = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharep.contains(placeidtext)){
            menu.getItem(1).setIcon(R.drawable.heart_fill_white);
        }else {
            menu.getItem(1).setIcon(R.drawable.heart_outline_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_twitter:
                //Toast.makeText(this,"推特",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                if(!weburl.equals("kong")) {
                     tturl = "https://twitter.com/intent/tweet?text=" + "Check out " + toolbartext + " located at " + dtplaceadd + "." + " Website:" + weburl;
                }else {
                     tturl = "https://twitter.com/intent/tweet?text=" + "Check out " + toolbartext + " located at " + dtplaceadd + ".";
                }
                Uri content_url = Uri.parse(tturl);
                intent.setData(content_url);
                startActivity(intent);
                return true;
            case R.id.menu_shoucang:
                SharedPreferences sharep;
                sharep = PreferenceManager.getDefaultSharedPreferences(this);
                if(sharep.contains(placeidtext)){
                    item.setIcon(R.drawable.heart_outline_white);
                    SharedPreferences.Editor editor=sharep.edit();
                    editor.remove(placeidtext);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),toolbartext+"was moved from favorites", Toast.LENGTH_SHORT).show();
                }else {
                   item.setIcon(R.drawable.heart_fill_white);
                    SharedPreferences.Editor editor=sharep.edit();
                    editor.putString(placeidtext,dtjilu);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),toolbartext+"was added to favorites", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable;
            String title;
            switch (position) {
                case 0: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.infopic);
                    title = "INFO";
                    break;
                case 1: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.photopic);
                    title = "PHOTOS";
                    break;
                case 2: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.mappic);
                    title = "MAP";
                    break;
                case 3: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.reviewpic);
                    title = "REVIEWS";
                    break;
                default: drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.infopic);
                    title = "INFO";
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
            switch (position){
                case 0:
                    Infotab tabi= new Infotab();
                    return tabi;
                case 1:
                    Photos phototab = new Photos();
                    return phototab;
                case 2:
                    Maptab maptabm = new Maptab();
                    return maptabm;
                case 3:
                    Reviewstab reviewtab = new Reviewstab();
                    return reviewtab;
                default:
                    return null;
            }

        }


        @Override
        public int getCount() {
            return 4;
        }
    }
}
