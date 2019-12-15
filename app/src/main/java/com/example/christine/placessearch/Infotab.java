package com.example.christine.placessearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Infotab extends Fragment{
    public static String placeidin;
    private Button dianhuabt;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info, container, false);


        placeidin = getFragmentManager().findFragmentByTag("infotag").getArguments().getString("infodata");
        //Log.d("infoinfoiiiiiiiiii",placeidin);

        final String urlin = "file:///android_asset/www/infotab.html";
        WebView ininin = (WebView) rootView.findViewById(R.id.xinxi);
        ininin.getSettings().setJavaScriptEnabled(true);

        ininin.loadUrl(urlin);


        dianhuabt = (Button) rootView.findViewById(R.id.phonebt);


        ininin.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public String placeidinfo() {
                return placeidin;
            }
        }, "xianin");

        ininin.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void dadianhua(String phonenm) {
              dianhuabt.setText(phonenm);
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dianhuabt.getText()));
                startActivity(dialIntent);
            }
        }, "dianhua");

        ininin.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view,int newProgress){
                if(newProgress==100){
                    closeDialog();
                }else{
                    openDialog();
                }
            }

            private void closeDialog(){
                if(dialog!=null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog=null;
                }
            }

            private void openDialog(){
                if (dialog==null){
                    dialog=new ProgressDialog(getActivity());
                    dialog.setMessage("Fetching details");
                    dialog.show();
                }

            }

        });





        return rootView;
    }

}
