package com.example.christine.placessearch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Reviewstab extends Fragment{
    public static String placeidrv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reviews, container, false);

        placeidrv = getFragmentManager().findFragmentByTag("rvtag").getArguments().getString("rvdata");
        //Log.d("rrrrrrrrrrrrrrrvvvvvvvvvvvv",placeidrv);

        final String urlrv = "file:///android_asset/www/reviewtab.html";
        WebView rvrvrv = (WebView) rootView.findViewById(R.id.pinglun);
        rvrvrv.getSettings().setJavaScriptEnabled(true);
        rvrvrv.loadUrl(urlrv);

        rvrvrv.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public String placeidreview() {
                return placeidrv;
            }
        }, "xianrv");

        rvrvrv.setWebChromeClient(new WebChromeClient());



        return rootView;
    }
}
