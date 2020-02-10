package com.example.kim.navdesign;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class Handler {


    private static Handler mInstance;
    private RequestQueue requestQueue;
    private  static Context mCtx;

    private Handler(Context context) {
        mCtx= context ;
        requestQueue= getRequestQueue() ;
    }
    private RequestQueue getRequestQueue() {

        if ( requestQueue == null ){
            requestQueue=Volley.newRequestQueue ( mCtx.getApplicationContext () );
        }
        return requestQueue;
    }

    public static synchronized Handler getmInstance(Context context ) {
        if ( mInstance==null )
        {
            mInstance=new Handler ( context );
        }


        return mInstance;
    }
    public <T> void addToRequestQueue (Request<T> request)
    {
        requestQueue.add ( request );

    }
}

