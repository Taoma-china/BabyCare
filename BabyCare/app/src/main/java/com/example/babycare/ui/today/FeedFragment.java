package com.example.babycare.ui.today;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HandshakeCompletedListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private Button finish;
    private TodayViewModel todayViewModel;

    private TextView mint;
    private TextView sec;
    private Button start;
    private Button reset;
    private long timeusedinsec;
    private boolean isstop =false;

    private TextView mint2;
    private TextView sec2;
    private Button start2;
    private Button reset2;
    private long timeusedinsec2;
    private boolean isstop2 =false;

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


    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!isstop2) {
                        updateView2();
                        mHandler2.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case 0:


                    break;
            }
        }
    };



    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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


//2
private void initViews2() {

    reset2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            mint2.setText("00");
            sec2.setText("00");
            start2.setText("start");
            timeusedinsec2=0;
            isstop2=true;
        }
    });
    start2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            mHandler2.removeMessages(1);
            String aaa=start2.getText().toString();
            if(aaa.equals("start")){
                mHandler2.sendEmptyMessage(1);
                isstop2 = false;
                start2.setText("pause");
            }else {
                mHandler2.sendEmptyMessage(0);
                isstop2 = true;
                start2.setText("start");
            }

        }
    });
}
    private void updateView2() {
        timeusedinsec2 += 1;
        int minute = (int) (timeusedinsec2 / 60)%60;
        int second = (int) (timeusedinsec2 % 60);
        if (minute < 10)
            mint2.setText("0" + minute);
        else
            mint2.setText("" + minute);
        if (second < 10)
            sec2.setText("0" + second);
        else
            sec2.setText("" + second);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mint = (TextView) view.findViewById(R.id.mint);
        sec = (TextView) view.findViewById(R.id.sec);
        reset = (Button) view.findViewById(R.id.reset);
        start = (Button) view.findViewById(R.id.start);
        initViews();

        mint2 = (TextView) view.findViewById(R.id.mint2);
        sec2 = (TextView) view.findViewById(R.id.sec2);
        reset2 = (Button) view.findViewById(R.id.reset2);
        start2 = (Button) view.findViewById(R.id.start2);
        initViews2();
        todayViewModel = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        finish = view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leftTime = "(L)"+ mint.getText()+":"+sec.getText() +"(R)"+ mint2.getText()+":"+sec2.getText();

                todayViewModel.setFeedTime(leftTime);
                Date date = new Date();
                String strDateFormat = "HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                todayViewModel.setFeedDate(sdf.format(date).toString());
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();



            }
        });
        return view;

    }
}