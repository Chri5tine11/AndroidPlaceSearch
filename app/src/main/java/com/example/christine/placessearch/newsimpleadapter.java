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

public class newsimpleadapter extends SimpleAdapter{
    String tagtext;
    String iconurl;
    private Context context1;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public newsimpleadapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        context1=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position,convertView,parent);
        ImageView imageView = view.findViewById(R.id.pic);
        iconurl = (String) ((Map)getItem(position)).get("pic");
        Picasso.get().load(iconurl).into(imageView);
        imageView.setTag(iconurl);


        final ImageButton liebiaoxin= view.findViewById(R.id.picm);

        final TextView diming=view.findViewById(R.id.textname);
        final TextView addr=view.findViewById(R.id.textdizhi);
        final TextView idplace=view.findViewById(R.id.placeid);
        String placeidi=(String) idplace.getText();
        SharedPreferences sharep;
        sharep = PreferenceManager.getDefaultSharedPreferences(context1);
        if(sharep.contains(placeidi)){
            liebiaoxin.setTag("like");
            liebiaoxin.setImageResource(R.drawable.heart_fill_red);
        }else {
            liebiaoxin.setTag("notlike");
            liebiaoxin.setImageResource(R.drawable.heart_outline_black);
        }


        liebiaoxin.setOnClickListener(new View.OnClickListener(){
            String placenm=(String) diming.getText();
            String placeaddr=(String) addr.getText();
            String placeidi=(String) idplace.getText();
            String jilu="{\"icon\":\""+iconurl+"\","+"\"placename\":\""+placenm+"\","+"\"address\":\""+placeaddr+"\","+"\"placeid\":\""+placeidi+"\"}";

            @Override
            public void onClick(View v) {
                tagtext= (String)liebiaoxin.getTag();
                //Log.d("uuuuuuuuuuuuuuu",jilu);
                if(tagtext.equals("notlike")){
                    SharedPreferences sharep;
                    sharep = PreferenceManager.getDefaultSharedPreferences(context1);
                    SharedPreferences.Editor editor=sharep.edit();
                    editor.putString(placeidi,jilu);
                    editor.apply();

//                    String value = sharep.getString(placeidi,"Null");
//                    Log.d("vvvvvvvvvvvvv",value);

                    liebiaoxin.setTag("like");
                    liebiaoxin.setImageResource(R.drawable.heart_fill_red);
                    Toast.makeText(context1,placenm+"was added to favorites", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences sharep;
                    sharep = PreferenceManager.getDefaultSharedPreferences(context1);
                    SharedPreferences.Editor editor=sharep.edit();
                    editor.remove(placeidi);
                    editor.apply();
//                    String value = sharep.getString(placeidi,"Null");
//                    Log.d("vvvvvvvvvvvvv",value);

                    liebiaoxin.setTag("notlike");
                    liebiaoxin.setImageResource(R.drawable.heart_outline_black);
                    Toast.makeText(context1,placenm+"was removed from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
