package com.example.christine.placessearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class liebiaoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private SimpleAdapter simp_adapter;
    private FrameLayout listContainer;
    private ListView listview;
    private TextView noresults;
    private List<Map<String,Object>>dataList;
    private List<Map<String,Object>>dataListnt;
    private List<Map<String,Object>>dataListp1;
    private List<Map<String,Object>>dataListp2;
    private String tbresult;
    private JSONArray tbarray;
    String placename;
    String iconurl;
    String vici;
    String placeid;
    private Button prebtn;
    private Button nextbtn;
    public String nexttk;
    private int pageno;
    private String jieguo;
    public static String nextpageone="kong";
    private String nextpagetwo="kong";
    private String tableone;
    private String tabletwo;

    public static final String extra_iconicon ="com.example.christine.placessearch.extra_iconicon";
    public static final String extra_placename ="com.example.christine.placessearch.extra_placename";
    public static final String extra_placeid="com.example.christine.placessearch.extra_placeid";
    public static final String extra_placeadd="com.example.christine.placessearch.extra_placeadd";


    private List<Map<String,Object>> getData(){
        Intent intent = getIntent();
        tbresult= intent.getStringExtra(searchtab.extra_result);
        if(tbresult!=null) {
            //Log.d("ttttttttttttttttt",tbresult);
            try {
                tbarray = new JSONArray(tbresult);
                for (int i = 0; i < tbarray.length(); i++) {
                    JSONObject placeitem = tbarray.getJSONObject(i);
                    iconurl = placeitem.getString("icon");
                    placename = placeitem.getString("name");
                    vici = placeitem.getString("vicinity");
                    placeid = placeitem.getString("place_id");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("pic", iconurl);
                    map.put("textname", placename);
                    map.put("textdizhi", vici);
                    map.put("placeid", placeid);
                    map.put("picm", R.drawable.heart_outline_black);
                    dataList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }//if
            return dataList;
    }

    private List<Map<String,Object>> getDatanext(){
        String tbresult = jieguo;
        try {
            tbarray = new JSONArray(tbresult);
            for(int i=0;i<tbarray.length();i++){
                JSONObject placeitem = tbarray.getJSONObject(i);
                iconurl = placeitem.getString("icon");
                placename = placeitem.getString("name");
                placeid = placeitem.getString("place_id");
                vici = placeitem.getString("vicinity");
                Map<String,Object>map = new HashMap<String, Object>();
                map.put("pic",iconurl);
                map.put("textname",placename);
                map.put("textdizhi",vici);
                map.put("placeid",placeid);
                map.put("picm",R.drawable.heart_outline_black);
                dataListnt.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataListnt;
    }

    private List<Map<String,Object>> getDatap1(){
        String tbresult = tableone;
        try {
            tbarray = new JSONArray(tbresult);
            for(int i=0;i<tbarray.length();i++){
                JSONObject placeitem = tbarray.getJSONObject(i);
                iconurl = placeitem.getString("icon");
                placename = placeitem.getString("name");
                placeid = placeitem.getString("place_id");
                vici = placeitem.getString("vicinity");
                Map<String,Object>map = new HashMap<String, Object>();
                map.put("pic",iconurl);
                map.put("textname",placename);
                map.put("textdizhi",vici);
                map.put("placeid",placeid);
                map.put("picm",R.drawable.heart_outline_black);
                dataListp1.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataListp1;
    }

    private List<Map<String,Object>> getDatap2(){
        String tbresult = tabletwo;
        try {
            tbarray = new JSONArray(tbresult);
            for(int i=0;i<tbarray.length();i++){
                JSONObject placeitem = tbarray.getJSONObject(i);
                iconurl = placeitem.getString("icon");
                placename = placeitem.getString("name");
                placeid = placeitem.getString("place_id");
                vici = placeitem.getString("vicinity");
                Map<String,Object>map = new HashMap<String, Object>();
                map.put("pic",iconurl);
                map.put("textname",placename);
                map.put("textdizhi",vici);
                map.put("placeid",placeid);
                map.put("picm",R.drawable.heart_outline_black);
                dataListp2.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataListp2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liebiaoactivity);
        initAll();

    }


    @Override
    public void onResume(){
        super.onResume();
        initAll();
    }

    public  void initAll(){
        final ProgressDialog pnext = new ProgressDialog(this);


        Intent intent = getIntent();
        nextpageone = intent.getStringExtra(searchtab.extra_next);
        tbresult= intent.getStringExtra(searchtab.extra_result);
        listContainer = findViewById(R.id.list_container);
        prebtn = (Button) findViewById(R.id.prebtn);
        nextbtn = (Button) findViewById(R.id.nextbtn);

        pageno=1;
        prebtn.setEnabled(false);
        nextbtn.setEnabled(false);

        if(nextpageone.equals("kong")){
            nextbtn.setEnabled(false);
        }else {
            nextbtn.setEnabled(true);
            tableone=tbresult;
        }
        nexttk=nextpageone;


        //final String key_word = intent.getStringExtra(searchtab.EXTRA_TEXT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noresults = (TextView)findViewById(R.id.norstext);

        dataList = new ArrayList<Map<String,Object>>();
        listview = (ListView) findViewById(R.id.biaoge);

        if(tbresult.equals("[]")){
            listContainer.setVisibility(View.GONE);
            noresults.setVisibility(View.VISIBLE);
        }else {
            noresults.setVisibility(View.GONE);
            listContainer.setVisibility(View.VISIBLE);
        }

        simp_adapter = new newsimpleadapter(this,getData(),R.layout.liebiaoitem,new String[]{"pic","textname","textdizhi","placeid","picm"},new int[]{R.id.pic,R.id.textname,R.id.textdizhi,R.id.placeid,R.id.picm});
        listview.setAdapter(simp_adapter);
        listview.setDivider(null);//去除listview item之间的边框
        listview.setOnItemClickListener(this);

        prebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pageno=pageno-1;
                if(pageno==1){
                    prebtn.setEnabled(false);
                    nextbtn.setEnabled(true);
                    nexttk=nextpageone;
                    dataListp1 = new ArrayList<Map<String,Object>>();
                    simp_adapter = new newsimpleadapter(getBaseContext(),getDatap1(),R.layout.liebiaoitem,new String[]{"pic","textname","textdizhi","placeid","picm"},new int[]{R.id.pic,R.id.textname,R.id.textdizhi,R.id.placeid,R.id.picm});
                    listview.setAdapter(simp_adapter);
                    listview.setDivider(null);//去除listview item之间的边框
                    //
                }else if(pageno==2){
                    prebtn.setEnabled(true);
                    nextbtn.setEnabled(true);
                    nexttk=nextpagetwo;
                    dataListp2 = new ArrayList<Map<String,Object>>();
                    simp_adapter = new newsimpleadapter(getBaseContext(),getDatap2(),R.layout.liebiaoitem,new String[]{"pic","textname","textdizhi","placeid","picm"},new int[]{R.id.pic,R.id.textname,R.id.textdizhi,R.id.placeid,R.id.picm});
                    listview.setAdapter(simp_adapter);
                    listview.setDivider(null);//去除listview item之间的边框
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageno=pageno+1;

                pnext.setMessage("Fetching next page");
                pnext.show();

                String url = "http://christine.us-west-1.elasticbeanstalk.com:8000/?todo=nextrq&nextpage="+nexttk;
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pnext.dismiss();
                                try {
                                    jieguo = response.getString("results");
                                    if(response.has("next_page_token")&&pageno==2){
                                        nextbtn.setEnabled(true);
                                        prebtn.setEnabled(true);
                                        nextpagetwo=response.getString("next_page_token");
                                        nexttk=nextpagetwo;
                                        tabletwo=jieguo;
                                    }else if(!response.has("next_page_token")&&pageno==3){
                                        prebtn.setEnabled(true);
                                        nextbtn.setEnabled(false);
                                    }else if(!response.has("next_page_token")&&pageno==2){
                                        nextbtn.setEnabled(false);
                                        prebtn.setEnabled(true);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dataListnt = new ArrayList<Map<String,Object>>();
                                simp_adapter = new newsimpleadapter(getBaseContext(),getDatanext(),R.layout.liebiaoitem,new String[]{"pic","textname","textdizhi","placeid","picm"},new int[]{R.id.pic,R.id.textname,R.id.textdizhi,R.id.placeid,R.id.picm});
                                listview.setAdapter(simp_adapter);
                                listview.setDivider(null);//去除listview item之间的边框

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();

                            }
                        });

                queue.add(jsonObjectRequest);

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String placenamename = (String) ((TextView)view.findViewById(R.id.textname)).getText();
        String placeidid = (String) ((TextView)view.findViewById(R.id.placeid)).getText();
        String placedizhi= (String) ((TextView)view.findViewById(R.id.textdizhi)).getText();
        ImageView imageView = view.findViewById(R.id.pic);
        String iconlian=(String)imageView.getTag();

        //Toast.makeText(this,"position="+position+"内容="+iconlian, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),Detailpage.class);
        intent.putExtra(extra_iconicon,iconlian);
        intent.putExtra(extra_placeadd,placedizhi);
        intent.putExtra(extra_placename,placenamename);
        intent.putExtra(extra_placeid,placeidid);
        startActivity(intent);
    }
}
