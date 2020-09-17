package com.example.babycare.ui.today;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.babycare.MainActivity;
import com.example.babycare.R;
import com.example.babycare.database.UserAccount;
import com.example.babycare.viewmodel.UserViewModel;


import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.babycare.layout.fragment_login.TAG;

public class TodayFragment extends Fragment {

    Button addBaby;
    private UserAccount userAccount;
    private UserViewModel userViewModel;
    private TodayViewModel todayViewModel;
    public String babyName;
    String babyDate;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_today, container, false);
        final TextView textView = root.findViewById(R.id.text_home);





        addBaby = root.findViewById(R.id.addBaby);


        return root;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        addBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TodayFragment.this)
                        .navigate(R.id.navigation_addBaby);
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        todayViewModel = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        userAccount = new UserAccount(todayViewModel.getUserEmail().getValue());



            userViewModel.checkUser(userAccount).observe(getActivity(), new Observer<List<UserAccount>>() {
                @Override
                public void onChanged(List<UserAccount> userAccounts) {
                    babyName = userAccounts.get(0).getBabyName();
                    babyDate = userAccounts.get(0).getBabyBirthday();
                    Log.i("LOG_TAG", "babyName..." + babyName);

                    Log.i("LOG_TAG", "babyName.222.." + babyName);
                    if (todayViewModel.getBabyName().getValue() != null || babyName != null) {
                        Log.d("babyName", "babyname" + todayViewModel.getBabyName().getValue());
                        todayViewModel.setBabyName(babyName);
                        todayViewModel.setBabyDate(babyDate);
                        addBaby.setVisibility(View.GONE);
//
//                            insertNestedFragment();



                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_today, new TodayBabyinfo())
//                                        .addToBackStack(null)
                                        .commit();


                    }

                }
            });



    }



//    private void insertNestedFragment() {
//
//        Fragment babyInfoFragment = getChildFragmentManager().findFragmentById(R.id.fragment_babyinfo);
//        if (babyInfoFragment == null) {
//            Log.i("LOG_TAG", "add new baby information !!");
//            babyInfoFragment = new TodayBabyinfo();
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.fragment_today, babyInfoFragment).commit();
//
//        } else {
//            Log.i("LOG_TAG", "found existing baby information, no need to add it again !!");
//        }





//    }



}