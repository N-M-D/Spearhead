package com.example.spearhead;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    //EditTimeFragment fragment = new EditTimeFragment();
    String large,big,med,small,tiny;
    TextView changeTypes;
    TextView timerTitle;
    TextView largeNum,bigNum,medNum,smallNum,tinyNum;
    Time studyTime,restTime;
    TextView editBtn;
    ConstraintLayout body;
    SharedPreferences prefs;
    AlertDialog dialog;


    public static final String TimerPREFRENCE = "TimePref";
    public static final String L_UID = "L_ID";
    public static final String B_UID = "B_ID";
    public static final String M_UID = "M_ID";
    public static final String S_UID = "S_ID";
    public static final String T_UID = "T_ID";

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
        studyTime= new Time(60,25,15,10,5);
        restTime= new Time(45,20,10,5,2);
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        editBtn = (TextView) view.findViewById(R.id.editBtn);
         largeNum= (TextView) view.findViewById(R.id.largeNum);
         bigNum= (TextView) view.findViewById(R.id.bigNum);
         medNum= (TextView) view.findViewById(R.id.medNum);
         smallNum= (TextView) view.findViewById(R.id.smallNum);
         tinyNum= (TextView) view.findViewById(R.id.tinyNum);
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
                    large = studyTime.getlargeString();
                    big = studyTime.getbigString();
                    med = studyTime.getmedString();
                    small = studyTime.getsmallString();
                    tiny = studyTime.gettinyString();
                    largeNum.setText(large);
                    bigNum.setText(big);
                    medNum.setText(med);
                    smallNum.setText(small);
                    tinyNum.setText(tiny);
                    prefs = getActivity().getSharedPreferences(TimerPREFRENCE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(L_UID, large);
                    editor.putString(B_UID, big);
                    editor.putString(M_UID, med);
                    editor.putString(S_UID, small);
                    editor.putString(T_UID, tiny);
                    editor.commit();

                }
                else{// change to rest timer set
                    timerTitle.setText("Have a break?");
                    body.setBackground(getResources().getDrawable(R.drawable.purple_gradient));
                    large = restTime.getlargeString();
                    big = restTime.getbigString();
                    med = restTime.getmedString();
                    small = restTime.getsmallString();
                    tiny = restTime.gettinyString();
                    largeNum.setText(large);
                    bigNum.setText(big);
                    medNum.setText(med);
                    smallNum.setText(small);
                    tinyNum.setText(tiny);

                    prefs = getActivity().getSharedPreferences(TimerPREFRENCE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(L_UID, large);
                    editor.putString(B_UID, big);
                    editor.putString(M_UID, med);
                    editor.putString(S_UID, small);
                    editor.putString(T_UID, tiny);
                    editor.commit();

                }

            }

        });
        editBtn.setOnClickListener(new View.OnClickListener() {//open EditTimeFragment
            @Override
            public void onClick(View view) {
                EditTimeFragment editTimeFragment = new EditTimeFragment();
                editTimeFragment.show(getActivity().getSupportFragmentManager(), "EditFragment");
            }
        });

        tinyNum.setOnClickListener(new View.OnClickListener() {//open EditTimeFragment
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DisplayTimeFragment displayTimeFragment = new DisplayTimeFragment();
                fragmentTransaction.replace(R.id.body, displayTimeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
//         Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onClick(View view) {

    }
}