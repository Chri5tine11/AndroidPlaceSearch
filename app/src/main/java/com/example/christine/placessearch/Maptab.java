package com.example.christine.placessearch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Maptab extends Fragment{
    public static String placeidm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map, container, false);

        placeidm = getFragmentManager().findFragmentByTag("maptag").getArguments().getString("mapdata");
        //Log.d("mmmmmmmmmmmmmm",placeidm);


        final String urldi = "file:///android_asset/www/maptab.html";

        WebView dididi = (WebView) rootView.findViewById(R.id.ditu);
        dididi.getSettings().setJavaScriptEnabled(true);
        //dididi.setWebContentsDebuggingEnabled(true);
        dididi.loadUrl(urldi);

        dididi.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public String placeiddi() {
                return placeidm;
            }
        }, "xiandi");

        dididi.setWebChromeClient(new WebChromeClient());




        return rootView;
    }
}
