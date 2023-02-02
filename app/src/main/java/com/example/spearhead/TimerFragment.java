package com.example.spearhead;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatEditText;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment implements  View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean condition = true;
    TextView changeTypes;
    TextView timerTitle;
    Time studyTime;
    Time restTime;
    ConstraintLayout body;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
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
        getActivity().setTitle("Timer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        condition= true;
        studyTime= new Time(55,25,15,10,5);
        restTime= new Time(55,25,15,10,5);
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

         body = (ConstraintLayout) view.findViewById(R.id.body);
        timerTitle = (TextView) view.findViewById(R.id.timerTitle);
        changeTypes = (TextView) view.findViewById(R.id.changeType);

        changeTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    condition= !condition;
                if(condition){// change to study timer set
                    timerTitle.setText("Grind time?");
                    body.setBackground(getResources().getDrawable(R.drawable.blue_gradient));

                }
                else{// change to rest timer set
                    timerTitle.setText("Have a break?");
                    body.setBackground(getResources().getDrawable(R.drawable.purple_gradient));

                }

            }
        });
//         Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}