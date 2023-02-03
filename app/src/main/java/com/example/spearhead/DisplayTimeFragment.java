package com.example.spearhead;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayTimeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private long timeLeftInMilliseconds = 300000;
    private TextView timerValue;
    private TextView startButton;
    private TextView stopButton;
    private boolean timerRunning;
    private TextView pauseButton;
    private long startTime = 0L;
    private CountDownTimer countDownTimer;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayTimeFragment() {
        // Required empty public constructor
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startButton.setText("Start");
                startButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.INVISIBLE);
            }
        }.start();

        timerRunning = true;
        startButton.setText("Pause");
        stopButton.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText("Start");
        stopButton.setVisibility(View.VISIBLE);
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        timeLeftInMilliseconds = 30000;
        updateTimer();
        startButton.setText("Start");
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        timerValue.setText(timeLeftText);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayTimeFragment newInstance(String param1, String param2) {
        DisplayTimeFragment fragment = new DisplayTimeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_time, container, false);
        timerValue = (TextView) view.findViewById(R.id.timerValue);
        startButton = (TextView) view.findViewById(R.id.startButton);
        stopButton = (TextView) view.findViewById(R.id.stopButton);
        pauseButton = (TextView) view.findViewById(R.id.pauseButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        return view;
    }
}