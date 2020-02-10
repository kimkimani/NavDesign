package com.example.kim.navdesign.NavDrawer;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.example.kim.navdesign.MainActivity;
import com.example.kim.navdesign.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetinFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    WebView webView;

    private boolean isRedirected;
    private SwipeRefreshLayout swipe;
    private ProgressBar progress;
    String url = "09";

    public BetinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity ()).setActionBarTittle ( "Betin.co.ke" );
        // Inflate the layout for this fragment

        View v=inflater.inflate ( R.layout.fragment_lolly , container , false );
        webView=(WebView) v.findViewById ( R.id.webView );
        swipe = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipe.setOnRefreshListener ( this );

        WebSettings webSettings = webView.getSettings ();
        webView.getSettings ().setRenderPriority ( WebSettings.RenderPriority.HIGH );
        webView.getSettings ().setCacheMode (WebSettings.LOAD_CACHE_ELSE_NETWORK  );
        webView.setScrollBarStyle ( View.SCROLLBARS_INSIDE_OVERLAY );
        webSettings.setDomStorageEnabled ( true );
        webSettings.setLayoutAlgorithm ( WebSettings.LayoutAlgorithm.NARROW_COLUMNS );
        webSettings.setSavePassword ( true );
        webSettings.setEnableSmoothTransition ( true );
        webSettings.setSaveFormData ( true );



        webView.getSettings ().setJavaScriptEnabled ( true );
        webView.getSettings ().setAppCacheEnabled ( true );
        webView.loadUrl ( "https://sports.betin.co.ke/mobile#/" );
        webView.setWebViewClient ( new WebViewClient () {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                isRedirected = true;
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isRedirected = false;
            }

            public void onLoadResource (WebView view, String url) {
                if (!isRedirected) {
                    if (progressDialog == null) {
                        progressDialog=new  ProgressDialog( getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setTitle ("Loading...");
                        progressDialog.setMessage("please wait ...");
                        progressDialog.show();
                    }
                }

            }
            public void onPageFinished(WebView view, String url) {
                try{
                    isRedirected=true;

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }



                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
            public void onReceivedError(WebView view , int errorCode , String description , String failingUrl) {

                webView.loadUrl ( "file:///android_asset/error.html" );

            }
        });

        return v;
    }


    @Override
    public void onRefresh() {
        webView.reload ();
        swipe.setRefreshing ( false );
    }
}
