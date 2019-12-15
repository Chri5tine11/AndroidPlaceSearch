package com.example.christine.placessearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class favoritetab extends Fragment implements AdapterView.OnItemClickListener{
    private ListView fvlistview;
    private favadapter fvsimp_adapter;
    private List<Map<String,Object>> fvdataList;
    String placename;
    String iconurl;
    String vici;
    String placeid;
    String sss;
    private TextView nofav;

    public static final String extra_iconicon ="com.example.christine.placessearch.extra_iconicon";
    public static final String extra_placename ="com.example.christine.placessearch.extra_placename";
    public static final String extra_placeid="com.example.christine.placessearch.extra_placeid";
    public static final String extra_placeadd="com.example.christine.placessearch.extra_placeadd";

    private List<Map<String,Object>> getfvData(){
        SharedPreferences sharep;
        sharep = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Map<String, ?> allContent = sharep.getAll();

        for(Map.Entry<String, ?>  entry : allContent.entrySet()){
                sss= (String) entry.getValue();
            try {
                JSONObject placeitem= new JSONObject(sss);
                iconurl = placeitem.getString("icon");
                //Log.d("iiiiiiiiiiiiii",iconurl);
                placename = placeitem.getString("placename");
                vici = placeitem.getString("address");
                placeid=placeitem.getString("placeid");
                Map<String,Object>map = new HashMap<String, Object>();
                map.put("pic",iconurl);
                map.put("textname",placename);
                map.put("textdizhi",vici);
                map.put("placeid",placeid);
                map.put("picm",R.drawable.heart_fill_red);
                fvdataList.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            }//for
        return fvdataList;
    }

    @Override
    public void onResume(){
        super.onResume();
        getfvData().removeAll(fvdataList);
        initfav();
    }

    public void initfav(){

        SharedPreferences sharep;
        sharep = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Map<String, ?> allContent = sharep.getAll();

        if(allContent.size()==0){
            fvlistview.setVisibility(View.GONE);
            nofav.setVisibility(View.VISIBLE);

        }else {
            nofav.setVisibility(View.GONE);
            fvlistview.setVisibility(View.VISIBLE);
        }

        fvsimp_adapter = new favadapter(getActivity(),getfvData(),R.layout.liebiaoitem,new String[]{"pic","textname","textdizhi","placeid","picm"},new int[]{R.id.pic,R.id.textname,R.id.textdizhi,R.id.placeid,R.id.picm});
        fvlistview.setAdapter(fvsimp_adapter);
        fvlistview.setDivider(null);//去除listview item之间的边框
        fvlistview.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favoritetab, container, false);

        nofav = (TextView)rootView.findViewById(R.id.nofavtext);

        fvdataList = new ArrayList<Map<String,Object>>();
        fvlistview = (ListView) rootView.findViewById(R.id.fvbiaoge);

        initfav();

//        SharedPreferences sharep;
//        sharep = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        Map<String, ?> allContent = sharep.getAll();
//
//        if(allContent.size()==0){
//            fvlistview.setVisibility(View.GONE);
//            nofav.setVisibility(View.VISIBLE);
//
//        }else {
//            nofav.setVisibility(View.GONE);
//            fvlistview.setVisibility(View.VISIBLE);
//        }
//
//        fvsimp_adapter = new favadapter(getActivity(),getfvData(),R.layout.liebiaoitem,new String[]{"pic","textname","textdizhi","placeid","picm"},new int[]{R.id.pic,R.id.textname,R.id.textdizhi,R.id.placeid,R.id.picm});
//        fvlistview.setAdapter(fvsimp_adapter);
//        fvlistview.setDivider(null);//去除listview item之间的边框
//        fvlistview.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String placenamename = (String) ((TextView)view.findViewById(R.id.textname)).getText();
        String placeidid = (String) ((TextView)view.findViewById(R.id.placeid)).getText();
        String placedizhi= (String) ((TextView)view.findViewById(R.id.textdizhi)).getText();
        ImageView imageView = view.findViewById(R.id.pic);
        String iconlian=(String)imageView.getTag();

        //Toast.makeText(this,"position="+position+"内容="+placedizhi, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(),Detailpage.class);
        intent.putExtra(extra_iconicon,iconlian);
        intent.putExtra(extra_placeadd,placedizhi);
        intent.putExtra(extra_placename,placenamename);
        intent.putExtra(extra_placeid,placeidid);
        startActivity(intent);

    }
}
