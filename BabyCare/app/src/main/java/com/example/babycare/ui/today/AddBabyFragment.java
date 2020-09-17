package com.example.babycare.ui.today;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.babycare.R;
import com.example.babycare.viewmodel.UserViewModel;

public class AddBabyFragment extends Fragment {

    private Button submit;
    private EditText babyName;
    private EditText babyBirthday;
    private String babySex;
    private RadioGroup radioGroup;
    private TodayViewModel addBabyInfo;
    private String email;
    private UserViewModel model;
    private RadioButton radioButton;







    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_addbaby, container, false);
        addBabyInfo =  ViewModelProviders.of(this).get(TodayViewModel.class);






        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addBabyInfo = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        addBabyInfo.getUserEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                email = s;
            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        submit = view.findViewById(R.id.infor_submit);
        babyName = view.findViewById(R.id.baby_name);
        babyBirthday = view.findViewById(R.id.baby_date);
        radioGroup = view.findViewById(R.id.selectGender);



        model = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        radioButton = radioGroup.findViewById(selectedId);
                        if (radioButton == null) {
                            Toast.makeText(getActivity(), "Please select gender", Toast.LENGTH_SHORT).show();
                        }else {

                            babySex = radioButton.getText().toString();
                        }
                        if(babyName.getText().toString().isEmpty() || babyBirthday.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please fill information", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            model.updateBabyName(babyName.getText().toString(), email);
                            model.updataBabyBirth(babyBirthday.getText().toString(), email);
                            model.updataBabySex(babySex, email);
                            addBabyInfo.setBabyName(babyName.getText().toString());
                            //model.updateBabyDate(babyBirthday.getText().toString(), )
                            Log.d("name", "input baby button ::::" + babyBirthday.getText());
                            Log.d("name", "input baby sex ::::" + babySex);
                            Log.d("name", "input baby name ::::" + babyName.getText().toString());
                            Log.d("name", "input baby eeee ::::" + email);


                            getActivity().onBackPressed();
                        }
            }
        });
    }


}