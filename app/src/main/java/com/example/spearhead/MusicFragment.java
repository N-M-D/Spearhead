package com.example.spearhead;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment implements View.OnClickListener, RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerList;
    MusicListAdapter musicListAdapter;
    List<Music> musicList = new ArrayList<>();
    ImageView trackImgSmall;
    TextView songTitle;
    LinearLayout musicControlLayout;
    ImageView trackImgBig;
    LinearLayout trackControl;
    ImageView backBtn;
    ImageView playBtn;
    ImageView skipBtn;


    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
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
        getActivity().setTitle("Music");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        LinearLayout allMusic = (LinearLayout) view.findViewById(R.id.AllMusicButton);
        LinearLayout playlist = (LinearLayout) view.findViewById(R.id.PlaylistsButton);
        allMusic.setOnClickListener(this::onClick);

        //Add Temp Music
        musicList.add(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOAsobi"));
        musicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku"));
        musicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State"));
        musicList.add(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOAsobi"));
        musicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku"));
        musicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State"));
        musicList.add(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOAsobi"));
        musicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku"));
        musicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State"));

        recyclerList= (RecyclerView) view.findViewById(R.id.recentMusicList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerList.setLayoutManager(linearLayoutManager);
        musicListAdapter= new MusicListAdapter(musicList, this);
        recyclerList.setAdapter(musicListAdapter);

        musicControlLayout = (LinearLayout) view.findViewById(R.id.MusicControl);
        trackImgSmall = (ImageView) view.findViewById(R.id.musicControlBg);
        songTitle = (TextView) view.findViewById(R.id.currentSongTitle);
        trackImgBig = (ImageView) view.findViewById(R.id.trackImgBig);
        trackControl = (LinearLayout) view.findViewById(R.id.trackControl);
        backBtn = (ImageView) view.findViewById(R.id.backBtn);
        playBtn = (ImageView) view.findViewById(R.id.playBtn);
        skipBtn = (ImageView) view.findViewById(R.id.skipBtn);
        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.coordinator);
        View nowPlayingLayout =  coordinatorLayout.findViewById(R.id.musicControlLayout);
        BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(nowPlayingLayout);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Toast.makeText(getActivity(), "Collapsed", Toast.LENGTH_SHORT);
                        //trackImgSmall.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            trackImgSmall.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(70)));
                            trackImgSmall.setRenderEffect(null);
                        }
                        musicControlLayout.setOrientation(LinearLayout.HORIZONTAL);
                        musicControlLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(70)));

                        trackControl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        backBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        playBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        skipBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Toast.makeText(getActivity(), "Dragging", Toast.LENGTH_SHORT);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Toast.makeText(getActivity(), "Expanded", Toast.LENGTH_SHORT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            trackImgSmall.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                            trackImgSmall.setRenderEffect(RenderEffect.createBlurEffect(20, 20, Shader.TileMode.MIRROR));
                        }
                        musicControlLayout.setOrientation(LinearLayout.VERTICAL);
                        musicControlLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                        trackControl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
                        backBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        playBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        skipBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                        trackImgBig.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 100));
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Toast.makeText(getActivity(), "Hidden", Toast.LENGTH_SHORT);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Toast.makeText(getActivity(), "Settling", Toast.LENGTH_SHORT);
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        Toast.makeText(getActivity(), "Half Expanded", Toast.LENGTH_SHORT);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v){
        Toast.makeText(getContext(), "Test", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(int pos) {
        changeNowPlaying(musicList.get(pos));
    }

    void changeNowPlaying(Music music){
        musicControlLayout.setBackgroundResource(R.drawable.music_control_gradient);
        trackImgSmall.setImageResource(music.getImg());
        songTitle.setText(music.getName());
    }

    int dpToPx(int dps){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }
}