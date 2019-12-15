package com.example.christine.placessearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.RectangularBounds;

import org.json.JSONException;
import org.json.JSONObject;


public class searchtab extends Fragment {

    //private EditText mTextview;
    private Button searchbutton;
    private Button clearbutton;
    private EditText kfield;
    private Spinner spinner;
    private EditText disfield;
    //private EditText lfield;
    private AutoCompleteTextView lfield;
    private RadioButton radiobtn1;
    private RadioButton radiobtn2;
    private RadioGroup rg;
    int ableflag = 0;
    String herelat;
    String herelon;
    private String result;
    String slfield;
    private String nextpagetk;
    ///////////////////
    private PlaceAutocompleteAdapter mPlaceAutoAdap;

    private static final RectangularBounds LAT_LNG_BOUNDS=
            RectangularBounds.newInstance(new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136)));
    
    
    
    
      public static final String extra_result="com.example.christine.placessearch.extra_result";
      public static final String extra_next ="com.example.christine.placessearch.extra_next";
//    public static final String EXTRA_TEXT = "com.example.christine.placessearch.EXTRA_TEXT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.searchtab, container, false);

        searchbutton = (Button) rootView.findViewById(R.id.searchbtn);
        clearbutton = (Button) rootView.findViewById(R.id.clearbtn);
        spinner = (Spinner) rootView.findViewById(R.id.spinner1);

        final String category1= (String) spinner.getSelectedItem();

        rg = (RadioGroup) rootView.findViewById(R.id.radiogroup1);
        radiobtn1=(RadioButton) rootView.findViewById(R.id.radioBtn1);
        radiobtn2=(RadioButton) rootView.findViewById(R.id.radioBtn2);
        //lfield=(EditText) rootView.findViewById(R.id.locationfield);
        lfield=(AutoCompleteTextView) rootView.findViewById(R.id.locationfield);
        disfield = (EditText) rootView.findViewById(R.id.distancefield);
        final ProgressDialog pgbarrs = new ProgressDialog(getActivity());
     //////////////////////////////////

        mPlaceAutoAdap=new PlaceAutocompleteAdapter(getActivity(), LAT_LNG_BOUNDS);
        lfield.setAdapter(mPlaceAutoAdap);

        kfield = (EditText)rootView.findViewById(R.id.keywordfield);

        //mTextview = (EditText) rootView.findViewById(R.id.text);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                if(checkedId==radiobtn1.getId()){
                    lfield.setEnabled(false);
                    ableflag = 0;
                    rootView.findViewById(R.id.enter2_red).setVisibility(rootView.GONE);
                }else if(checkedId==radiobtn2.getId()){
                    lfield.setEnabled(true);
                    ableflag = 1;
                }
            }
        });


        String url = "http://ip-api.com/json";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            herelat = response.getString("lat");
                            herelon = response.getString("lon");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });

        queue.add(jsonObjectRequest);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String skfield = kfield.getText().toString();
                skfield=skfield.trim();
                if (skfield.length()== 0){
                    rootView.findViewById(R.id.enter1_red).setVisibility(rootView.VISIBLE);
                    Toast toast = Toast.makeText(getActivity(),"Please fix all fields with errors",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    rootView.findViewById(R.id.enter1_red).setVisibility(rootView.GONE);
                }
                if (ableflag == 1){

                    slfield = lfield.getText().toString();
                    slfield=slfield.trim();
                    if (slfield.length()== 0){
                        rootView.findViewById(R.id.enter2_red).setVisibility(rootView.VISIBLE);
                        Toast toast = Toast.makeText(getActivity(),"Please fix all fields with errors",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        rootView.findViewById(R.id.enter2_red).setVisibility(rootView.GONE);
                    }
                }

                final String dist = disfield.getText().toString();
                
                if(rootView.findViewById(R.id.enter1_red).getVisibility()==rootView.GONE && rootView.findViewById(R.id.enter2_red).getVisibility()==rootView.GONE)
                {
                    
                    pgbarrs.setMessage("Fetching results");
                    pgbarrs.show();
                    
                    String url1;
                    if(ableflag==0) {
                        url1 = "http://christine.us-west-1.elasticbeanstalk.com:8000/?todo=placetable&key_word=" + skfield + "&category=" + category1 + "&distance=" + dist + "&Locationchoice=option1&herela=" + herelat + "&herelo=" + herelon;
                    }else{
                        url1 = "http://christine.us-west-1.elasticbeanstalk.com:8000/?todo=placetable&key_word=" + skfield + "&category=" + category1 + "&distance=" + dist + "&Locationchoice=option2&locationname="+ slfield;
                    }
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        result = response.getString("results");
                                        //Log.d("rrrrrrrrrrrrrrrrrrr",result);
                                        if(response.has("next_page_token")){
                                            nextpagetk=response.getString("next_page_token");
                                        }else {
                                            nextpagetk="kong";
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    pgbarrs.dismiss();
                                    Intent intent = new Intent(getContext(),liebiaoActivity.class);
                                    intent.putExtra(extra_result, result);
                                    intent.putExtra(extra_next,nextpagetk);
                                    //intent.putExtra(EXTRA_TEXT, skfield);
                                    startActivity(intent);
                                    //System.out.println(result);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();

                                }
                            });
                    queue.add(jsonObjectRequest);
                    
                }
            }
        });

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rootView.findViewById(R.id.enter1_red).setVisibility(rootView.GONE);
                rootView.findViewById(R.id.enter2_red).setVisibility(rootView.GONE);

                kfield.setText("");
                lfield.setText("");
                radiobtn1.setChecked(true);
                lfield.setEnabled(false);
                disfield.setText("");

                SpinnerAdapter apsAdapter= spinner.getAdapter();
                int k= apsAdapter.getCount();
                for(int i=0;i<k;i++){
                    if("Default".equals(apsAdapter.getItem(i).toString())){
                        spinner.setSelection(i,true);
                        break;
                    }
                }
            }
        });




        return rootView;
    }

    
}