package com.example.kim.navdesign.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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

import com.example.kim.navdesign.Adapter.TipsAdapter;
import com.example.kim.navdesign.Handler;
import com.example.kim.navdesign.Model.tips;
import com.example.kim.navdesign.Adapter.TipsAdapter;
import com.example.kim.navdesign.Model.tips;
import com.example.kim.navdesign.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab1 extends Fragment  implements ListView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "tab1_fragmaent";
    private InterstitialAd interstitial;
    private InterstitialAd mInterstitialAd;
    ListView listView;
    List<tips> tipsList;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate( R.layout.activity_soccerbet,container,false);

        prepareAd();

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                Log.i("hello", "world");
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG"," Interstitial not loaded");
                        }

                        prepareAd();


                    }
                });

            }
        }, 30, 30, TimeUnit.SECONDS);







        listView =(ListView)v.findViewById ( R.id.list_tips );
        tipsList= new ArrayList<> ();
        showlist();
        return v;

    }
    private void showlist() {

        StringRequest stringRequest= new StringRequest ( Request.Method.GET,
                " http://tipsscorepro.com/php%20json/dailytips.php",
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object= new JSONObject ( response );
                            JSONArray array= object.getJSONArray ( "data" );
                            for (int i =0;i <array.length ();i ++)
                            {
                                JSONObject tipsObj= array.getJSONObject(i);
                                tips p= new tips ( ("")+tipsObj.getString ( "Time" ),
                                        ("")+tipsObj.getString ( "league" ),
                                        ("")+tipsObj.getString ( "Match" ),
                                        ("Tip:")+tipsObj.getString ( "Tips" ),
                                        ("Odd:")+tipsObj.getString ( "Odds" ),
                                        ("Results:")+tipsObj.getString ( "Results" )




                                );

                                tipsList.add ( p );
                            }
                            TipsAdapter tipsAdapter= new TipsAdapter (tipsList,getContext ());
                            listView.setAdapter (tipsAdapter );

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
    public void  prepareAd(){

        mInterstitialAd = new InterstitialAd(getContext ());

        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}

