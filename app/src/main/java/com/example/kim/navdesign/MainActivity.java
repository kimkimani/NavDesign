package com.example.kim.navdesign;


import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;


import android.view.MenuItem;
import android.view.View;

import com.example.kim.navdesign.NavDrawer.Bet365Fragment;
import com.example.kim.navdesign.NavDrawer.BetPawaFragment;
import com.example.kim.navdesign.NavDrawer.BetikaFragment;
import com.example.kim.navdesign.NavDrawer.BetinFragment;
import com.example.kim.navdesign.NavDrawer.BetwayFragment;
import com.example.kim.navdesign.NavDrawer.HomeFragment;
import com.example.kim.navdesign.NavDrawer.IxBetFragment;
import com.example.kim.navdesign.NavDrawer.LiveScoreFragment;
import com.example.kim.navdesign.NavDrawer.LollyFragment;
import com.example.kim.navdesign.NavDrawer.MatchDabbiesFragment;
import com.example.kim.navdesign.NavDrawer.NaijaFragment;
import com.example.kim.navdesign.NavDrawer.NairaBetFragment;
import com.example.kim.navdesign.NavDrawer.SpesaFragment;
import com.example.kim.navdesign.Update.ForceUpdateChecker;
import com.example.kim.navdesign.fragments.TabFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, ForceUpdateChecker.OnUpdateNeededListener {

    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private InterstitialAd interstitial;

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        setContentView ( R.layout.activity_main );

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);

        //banner


        if(!isNetworkAvailable())
        {
            AlertDialog.Builder CheckBuilder = new AlertDialog.Builder ( MainActivity.this );
            CheckBuilder.setIcon ( R.drawable.ic_warning_black_24dp );
            CheckBuilder.setTitle ( "No Internet Connection" );
            CheckBuilder.setMessage ("You need Internet Connection to access this. " +
                    "Check you mobile data and try again" );

            CheckBuilder.setPositiveButton ( "Retry" , new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialog , int which) {
                    Intent intent = getIntent ();
                    fileList ();
                    startActivity ( intent );

                }
            } );
            AlertDialog alert = CheckBuilder.create ();
            alert.show();

        }

        else {

            if ( !isNetworkAvailable( ))

            {
                Thread timer= new Thread()
                {
                    @Override
                    public void run() {
                        try {
                            sleep ( 4000 );
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }

                    }
                };
                timer.start();
            }

        }

        setContentView(R.layout.activity_main);

        FloatingActionButton fab= findViewById(R.id.fab);
        fab.setBackgroundColor ( Color.BLUE );
        fab.setColorFilter ( Color.WHITE );
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Intent intent = new Intent("android.intent.action.SEND");
                intent.setType ( "text/plan" );


                intent.putExtra ( "android.intent.extra.SUBJECT" , "TIPSSCOREPRO.COM" );
                intent.putExtra ( "android.intent.extra.TEXT" , "TIPSSCOREPRO FREE TIPS App" + "# Download it at : http://www.tipsscorepro.com" + "# Share And Enjoy .... :)" );

                startActivity ( Intent.createChooser ( intent , "Share Via" ) );

            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);





        mNavigationView = (NavigationView) findViewById(R.id.main_drawer) ;
        View header=mNavigationView.getHeaderView ( 0 );
        MobileAds.initialize ( this,"ca-app-pub-6945892941084575~3061196009" );


        AdView ad = header.findViewById(R.id.adVie);

        AdRequest adReque=  new AdRequest.Builder ().build ();
        ad.loadAd ( adReque );



        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        params.width = width;
        mNavigationView.setLayoutParams(params);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();






        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setTitle(menuItem.getTitle());


                if (menuItem.getItemId() ==R.id.nav_home) {

                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new HomeFragment () );
                    ft.commit ( );
                }

                if (menuItem.getItemId() == R.id.nav_bet365) {

                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container, new Bet365Fragment () );
                    ft.commit ( );
                }
                if (menuItem.getItemId() == R.id.nav_matchdabbies) {

                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new MatchDabbiesFragment () );
                    ft.commit ( );
                }
                if (menuItem.getItemId() == R.id.nav_spesa){
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container, new SpesaFragment  () );
                    ft.commit ( );

                }

                if (menuItem.getItemId() == R.id.nav_1x){
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new IxBetFragment () );
                    ft.commit ( );
                }

                if (menuItem.getItemId() == R.id.nav_betika) {
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new BetikaFragment () );
                    ft.commit ( );
                }

                if (menuItem.getItemId() == R.id.nav_betin) {
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new BetinFragment () );
                    ft.commit ( );
                }

                if (menuItem.getItemId() == R.id.nav_LiveScore) {
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new LiveScoreFragment ()
                    );
                    ft.commit ( );
                }


                if (menuItem.getItemId() == R.id.nav_lollybet) {
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new LollyFragment () );
                    ft.commit ( );
                }
                if (menuItem.getItemId() ==R.id.nav_naija) {
                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new NaijaFragment () );
                    ft.commit ( );
                }

                if (menuItem.getItemId() == R.id.nav_Betpawa) {

                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new BetPawaFragment () );
                    ft.commit ( );
                }
                if (menuItem.getItemId() == R.id.nav_betway) {

                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new BetwayFragment () );
                    ft.commit ( );
                }
                if (menuItem.getItemId() == R.id.nav_naira) {

                    interstitial = new InterstitialAd(getApplicationContext());
                    interstitial.setAdUnitId(getString(R.string.admob_interstetial_ad));
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener () {
                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                    });
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager ().beginTransaction ();
                    ft.replace ( R.id.frame_container,new NairaBetFragment () );
                    ft.commit ( );


                }

                return false;
            }

        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle
                (this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();



    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater ().inflate ( R.menu.main,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    public  void  setActionBarTittle(String tittle){
        getSupportActionBar ().setTitle ( tittle );

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        int id=item.getItemId ();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings ) {
            return true;
        }
        return super.onOptionsItemSelected ( item );

    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager)this.getSystemService ( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo ();
        return  activeNetworkInfo !=null;
    }
    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue reposting.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No,thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
        dialog.show();

    }
    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}