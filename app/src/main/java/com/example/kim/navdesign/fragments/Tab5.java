package com.example.kim.navdesign.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.kim.navdesign.Adapter.JackportsAdapter;
import com.example.kim.navdesign.Adapter.TipsAdapter;
import com.example.kim.navdesign.Handler;
import com.example.kim.navdesign.Model.jacport;
import com.example.kim.navdesign.Model.tips;
import com.example.kim.navdesign.Adapter.TipsAdapter;
import com.example.kim.navdesign.Model.tips;
import com.example.kim.navdesign.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab5 extends Fragment  implements ListView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private InterstitialAd interstitial;

    ListView listView;
    List<jacport> jacportList;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_jacports,container,false);



        listView =(ListView)v.findViewById ( R.id.list_jackport );
        jacportList= new ArrayList<> ();
        showlist();
        return v;

    }
    private void showlist() {

        StringRequest stringRequest= new StringRequest ( Request.Method.GET,
                " http://tipsscorepro.com/php%20json/megaJackpot.php",
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object= new JSONObject ( response );
                            JSONArray array= object.getJSONArray ( "data" );
                            for (int i =0;i <array.length ();i ++)
                            {
                                JSONObject tipsObj= array.getJSONObject(i);


                                jacport p = new jacport (
                                        ("")+tipsObj.getString ( "Time" ),
                                        ("")+tipsObj.getString ( "id" ),

                                        ("")+tipsObj.getString ( "Match" ),
                                        ("Tip:")+tipsObj.getString ( "Tips" ),
                                        ("Results:")+tipsObj.getString ( "Results" )




                                );

                                jacportList.add ( p );
                            }
                            JackportsAdapter tipAdapter= new JackportsAdapter (jacportList,getContext ());
                            listView.setAdapter (tipAdapter );

                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        Handler.getmInstance ( getContext ()).addToRequestQueue(stringRequest);
    }



    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent , View view , int position , long id) {

    }
}
