package com.example.babycare.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.FragmentTransitionSupport;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babycare.AfterLogin_Main;
import com.example.babycare.MainActivity;
import com.example.babycare.R;
import com.example.babycare.database.UserAccount;
import com.example.babycare.database.UserRepository;
import com.example.babycare.viewmodel.UserViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_register extends Fragment {

    String name, email, password, re_password;

    private Button regiserButton;
    private EditText fullName;
    private EditText usernameText;
    private EditText passwordText;
    private EditText re_passwordText;
    private UserViewModel userViewModel;
    UserRepository repository;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_register.
     */
    // TODO: Rename and change types and number of parameters
    public fragment_register newInstance(String param1, String param2) {
        fragment_register fragment = new fragment_register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_register, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        fullName = (EditText)view.findViewById(R.id.et_name);
        usernameText = (EditText)view.findViewById(R.id.et_email);
        passwordText = view.findViewById(R.id.et_password);
//        passwordText.setOnEditorActionListener(this);
//        passwordText.requestFocus();

        re_passwordText = view.findViewById(R.id.et_repassword);
//        re_passwordText.setOnEditorActionListener(this);
//        re_passwordText.requestFocus();



        regiserButton = (Button)view.findViewById(R.id.btn_register);






        regiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = fullName.getText().toString().trim();
                email = usernameText.getText().toString().trim();
                password = passwordText.getText().toString().trim();
                re_password = re_passwordText.getText().toString().trim();
                final UserAccount newUser = new UserAccount(name, email, password, re_password);

                if(!password.equals(re_password)) {
                    Toast.makeText(getActivity(), "Password don't match", Toast.LENGTH_SHORT).show();
                }else {


                    userViewModel.checkUser(newUser).observe(getActivity(), new Observer<List<UserAccount>>() {
                        @Override
                        public void onChanged(List<UserAccount> userAccounts) {

                            if (userAccounts.size() != 0) {


                                Toast.makeText(getActivity(), "Account existed", Toast.LENGTH_SHORT).show();
                            } else {
                                userViewModel.insert(newUser);
                                Toast.makeText(getActivity(), "Successful Created", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });













                }

            }
        });

    }


}