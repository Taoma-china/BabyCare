package com.example.babycare.layout;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;


import android.renderscript.ScriptGroup;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.AfterLogin_Main;
import com.example.babycare.MainActivity;
import com.example.babycare.R;
import com.example.babycare.database.UserAccount;
import com.example.babycare.database.UserAccountDao;
import com.example.babycare.database.UserAccountDatabase;
import com.example.babycare.database.UserRepository;


import com.example.babycare.viewmodel.UserViewModel;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_login extends Fragment {

    public static final String TAG = "fragment_login";
    private View view;
    private Button loginButton;
    private UserAccountDatabase database;
    private UserAccountDao db;
    UserRepository repository;
    private EditText usernameText;
    private EditText passwordText;
    private Button signUpButton;
    private UserViewModel userViewModel;
    private ScriptGroup.Binding binding;
    public String transferEmail;

    public fragment_login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_login.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_login newInstance(String param1, String param2) {
        fragment_login fragment = new fragment_login();




        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login,
                container, false);





        return view;

    }


    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        loginButton = view.findViewById(R.id.btn_login);
        usernameText = view.findViewById(R.id.et_email);
        passwordText = view.findViewById(R.id.et_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "tete:;";
                String re_password = "dd";
                String email = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                UserAccount newUser = new UserAccount(name, email, password, re_password);


                userViewModel.find(newUser).observe(getActivity(), new Observer<List<UserAccount>>() {
                    @Override
                    public void onChanged(List<UserAccount> userAccounts) {
                        Log.d(TAG, "onChanged" + userAccounts);
                        Log.d(TAG, "onChanged" + userAccounts.size());
                        if (userAccounts.size() != 0 && (!userAccounts.get(0).getEmail().equals("") && !userAccounts.get(0).getPassword().equals(""))) {
                            transferEmail = userAccounts.get(0).getEmail();
                            Intent intent = new Intent(getActivity(), AfterLogin_Main.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("UserEmail", transferEmail);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Input Wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });






            }
        });

    }

//    public void sendMessage(MainActivity.ICallBack callBack) {
//        callBack.get_message_from_Fragment(transferEmail);
//    }




}