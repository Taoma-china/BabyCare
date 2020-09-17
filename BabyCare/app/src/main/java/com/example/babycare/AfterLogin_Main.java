package com.example.babycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.babycare.ui.discovery.DiscoveryFragment;
import com.example.babycare.ui.moment.MomentFragment;
import com.example.babycare.ui.more.MoreFragment;
import com.example.babycare.ui.today.FeedFragment;
import com.example.babycare.ui.today.TodayBabyinfo;
import com.example.babycare.ui.today.TodayFragment;
import com.example.babycare.ui.today.TodayViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AfterLogin_Main extends AppCompatActivity {
    TextView helloUser;
    private String userEmail;
    private TodayViewModel todayViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterloginmain);



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_today, R.id.navigation_moment, R.id.navigation_discovery, R.id.navigation_more)
//                .build();


        final Fragment TodayFragment = new TodayFragment();
        final Fragment MomentFragment = new MoreFragment();
        final Fragment DiscoverFragment = new DiscoveryFragment();
        final Fragment MoreFragment = new MoreFragment();
        final Fragment[] active = {TodayFragment};
        final FragmentManager fm = getSupportFragmentManager();
//        Fragment TodayBabyInfo = new TodayBabyinfo();
//        fm.beginTransaction().add(R.id.fragment_today,TodayBabyInfo);
//        Fragment FeedFragment = new FeedFragment();
//        fm.beginTransaction().add(R.id.fragment_babyinfo,FeedFragment);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_today:

                                fm.beginTransaction().hide(active[0]).show(TodayFragment).commit();
                                active[0] = TodayFragment;


                        return true;
                    case R.id.navigation_moment:
                        fm.beginTransaction().hide(active[0]).show(MomentFragment).commit();
                        return true;
                    case R.id.navigation_discovery:
                        fm.beginTransaction().hide(active[0]).show(DiscoverFragment).commit();
                        return true;
                    case R.id.navigation_more:
                        fm.beginTransaction().hide(active[0]).show(MoreFragment).commit();
                        return true;

                }


                return false;
            }
        });

//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);




        //received email from MainActivity(fragment login)
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("UserEmail");
        Log.d("email", "email---" + userEmail);


        todayViewModel = ViewModelProviders.of(this).get(TodayViewModel.class);
        todayViewModel.setUserEmail(userEmail);

    }




}