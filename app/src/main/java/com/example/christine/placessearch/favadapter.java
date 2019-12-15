package com.example.christine.placessearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class favadapter extends SimpleAdapter{
    String tagtext;
    String iconurl;
    private Context context1;
    private List<? extends Map<String, ?>> data1;


    public favadapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        context1=context;
        data1=data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = super.getView(position,convertView,parent);
        ImageView imageView = view.findViewById(R.id.pic);
        iconurl = (String) ((Map)getItem(position)).get("pic");
        Picasso.get().load(iconurl).into(imageView);
        imageView.setTag(iconurl);



        final ImageButton liebiaoxin= view.findViewById(R.id.picm);
        liebiaoxin.setTag("like");
        liebiaoxin.setImageResource(R.drawable.heart_fill_red);
        final TextView diming=view.findViewById(R.id.textname);
        //final TextView addr=view.findViewById(R.id.textdizhi);
        final TextView idplace=view.findViewById(R.id.placeid);


        liebiaoxin.setOnClickListener(new View.OnClickListener(){
            String placenm=(String) diming.getText();
            //String placeaddr=(String) addr.getText();
            String placeidi=(String) idplace.getText();
            //String jilu="{\"icon\":\""+iconurl+"\","+"\"placename\":\""+placenm+"\","+"\"address\":\""+placeaddr+"\","+"\"placeid\":\""+placeidi+"\"}";

            @Override
            public void onClick(View v) {
                tagtext= (String)liebiaoxin.getTag();
                    SharedPreferences sharep;
                    sharep = PreferenceManager.getDefaultSharedPreferences(context1);
                    SharedPreferences.Editor editor=sharep.edit();
                    editor.remove(placeidi);
                    editor.apply();
                    liebiaoxin.setTag("notlike");
                    liebiaoxin.setImageResource(R.drawable.heart_outline_black);
                    data1.remove(position);
                    notifyDataSetChanged();
                Toast.makeText(context1,placenm+"was removed from favorites", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}
