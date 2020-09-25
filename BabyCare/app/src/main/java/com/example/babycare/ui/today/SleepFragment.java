package com.example.babycare.ui.today;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.babycare.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SleepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SleepFragment extends Fragment {

    private Button finish;
    private TodayViewModel todayViewModel;

    private TextView mint;
    private TextView sec;
    private Button start;
    private Button reset;
    private long timeusedinsec;
    private boolean isstop =false;
    @SuppressLint("HandlerLeak")

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!isstop) {
                        updateView();
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case 0:
                    break;
            }
        }
    };



    public SleepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SleepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SleepFragment newInstance(String param1, String param2) {
        SleepFragment fragment = new SleepFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sleep, container, false);

        mint = (TextView) view.findViewById(R.id.mint);
        sec = (TextView) view.findViewById(R.id.sec);
        reset = (Button) view.findViewById(R.id.reset);
        start = (Button) view.findViewById(R.id.start);
        initViews();


        todayViewModel = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        finish = view.findViewById(R.id.finish2);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leftTime = mint.getText()+":"+sec.getText();

                todayViewModel.setSleepTime(leftTime);
                Date date = new Date();
                String strDateFormat = "HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                todayViewModel.setSleepDate(sdf.format(date).toString());
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();



            }
        });
        return view;
    }





    private void initViews() {

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                mint.setText("00");
                sec.setText("00");
                start.setText("start");
                timeusedinsec=0;
                isstop=true;
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mHandler.removeMessages(1);
                String aaa=start.getText().toString();
                if(aaa.equals("start")){
                    mHandler.sendEmptyMessage(1);
                    isstop = false;
                    start.setText("pause");
                }else {
                    mHandler.sendEmptyMessage(0);
                    isstop = true;
                    start.setText("start");
                }

            }
        });
    }
    private void updateView() {
        timeusedinsec += 1;
        int minute = (int) (timeusedinsec / 60)%60;
        int second = (int) (timeusedinsec % 60);
        if (minute < 10)
            mint.setText("0" + minute);
        else
            mint.setText("" + minute);
        if (second < 10)
            sec.setText("0" + second);
        else
            sec.setText("" + second);

    }

}


