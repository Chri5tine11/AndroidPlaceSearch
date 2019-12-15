package com.example.christine.placessearch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Photos extends Fragment{
    public static String placeidph;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photos, container, false);

        placeidph = getFragmentManager().findFragmentByTag("myTag").getArguments().getString("DATA");
        //Log.d("pppppppppppppp",placeidph);


        final String urltu = "file:///android_asset/www/phototab.html";
        WebView tututu = (WebView) rootView.findViewById(R.id.tupian);
        tututu.getSettings().setJavaScriptEnabled(true);
          //tututu.setWebContentsDebuggingEnabled(true);
        tututu.loadUrl(urltu);

        tututu.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public String placeidphoto() {
                return placeidph;
            }
        }, "xiantu");

         tututu.setWebChromeClient(new WebChromeClient());

        return rootView;
    }
}
