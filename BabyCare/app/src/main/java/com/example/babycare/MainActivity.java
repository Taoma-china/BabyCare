package com.example.babycare;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;


import com.example.babycare.layout.fragment_login;
import com.example.babycare.layout.fragment_register;
import com.example.babycare.viewmodel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdater pagerAdater = new AuthenticationPagerAdater(getSupportFragmentManager());
        fragment_login fragment_login = new fragment_login();
        fragment_register fragment_register = new fragment_register();
        pagerAdater.addFragment(fragment_login);
        pagerAdater.addFragment(fragment_register);
        viewPager.setAdapter(pagerAdater);

//        fragment_login.sendMessage(new ICallBack() {
//            @Override
//            public void get_message_from_Fragment(String string) {
//                receivedEmail = string;
//                while(receivedEmail!=null) {
//                    Intent intent = new Intent(MainActivity.this, AfterLogin_Main.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("UserEmail", receivedEmail);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            }
//        });




    }


//    public interface ICallBack {
//        void get_message_from_Fragment(String string);
//    }

//    public void clickLogin(View view) {
//        Intent intent = new Intent(MainActivity.this, AfterLogin_Main.class);
//        startActivity(intent);
//    }


//    public void add_baby(View view) {
////        Navigation.findNavController(view).navigate(R.id.babyInfo);
//        Intent intent = new Intent(this, AddBaby.class);
//        startActivity(intent);
//
//    }



    class AuthenticationPagerAdater extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragmentList = new ArrayList<>();


        public AuthenticationPagerAdater(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public AuthenticationPagerAdater(FragmentManager fm) {
            super(fm);


        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}