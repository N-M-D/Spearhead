package com.example.spearhead;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
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
    PlaylistsAdapter playlistsAdapter;
    List<Music> musicList = new ArrayList<>();
    ArrayList<Playlist> playlists = new ArrayList<>();
    ImageView trackImgSmall;
    TextView songTitle;
    LinearLayout musicControlLayout;
    ImageView trackImgBig;
    LinearLayout trackControl;
    ImageView backBtn;
    ImageView playBtn;
    ImageView skipBtn;
    Music nowPlaying;
    Boolean sheetUp = false;
    Boolean musicPage = true;
    List<Music> musicQueue = new ArrayList<>();
    MediaPlayer mediaPlayer;
    SeekBar seekBar;


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
        LinearLayout allMusicBtn = (LinearLayout) view.findViewById(R.id.AllMusicButton);
        LinearLayout playlistBtn = (LinearLayout) view.findViewById(R.id.PlaylistsButton);
        allMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerList.setAdapter(musicListAdapter);
                musicPage = true;
            }
        });
        //playlistAdapter = new MusicListAdapter(playlists, this);
        playlistsAdapter = new PlaylistsAdapter(this, playlists);
        playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerList.setAdapter(playlistsAdapter);
                musicPage = false;
            }
        });

        //Add Temp Music
        musicList.add(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOAsobi", R.raw.yoru_ni_kakeru));
        musicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku", R.raw.calc));
        musicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State", R.raw.i_wanna_run));
        musicList.add(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOAsobi", R.raw.yoru_ni_kakeru));
        musicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku", R.raw.calc));
        musicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State", R.raw.i_wanna_run));
        musicList.add(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOAsobi", R.raw.yoru_ni_kakeru));
        musicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku", R.raw.calc));
        musicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State", R.raw.i_wanna_run));

        List<Music> testMusicList = new ArrayList<>();
        testMusicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State", R.raw.i_wanna_run));
        testMusicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku", R.raw.calc));

        playlists.add(new Playlist("TEst 1", testMusicList, R.drawable.calc));

        recyclerList= (RecyclerView) view.findViewById(R.id.recentMusicList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerList.setLayoutManager(linearLayoutManager);
        musicListAdapter= new MusicListAdapter(musicList, this);
        recyclerList.setAdapter(musicListAdapter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(musicQueue.size() > 0){
                    nowPlaying = musicQueue.get(0);
                    playTrack(nowPlaying);
                    changeNowPlaying(nowPlaying);
                }
            }
        });

        musicControlLayout = (LinearLayout) view.findViewById(R.id.musicControl);
        trackImgSmall = (ImageView) view.findViewById(R.id.musicControlBg);
        songTitle = (TextView) view.findViewById(R.id.currentSongTitle);
        trackImgBig = (ImageView) view.findViewById(R.id.trackImgBig);
        trackControl = (LinearLayout) view.findViewById(R.id.trackControl);
        backBtn = (ImageView) view.findViewById(R.id.backBtn);
        playBtn = (ImageView) view.findViewById(R.id.playBtn);
        skipBtn = (ImageView) view.findViewById(R.id.skipBtn);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        Thread.sleep(1000);
                        if (mediaPlayer.isPlaying()) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(mCurrentPosition);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playBtn.setImageResource(R.drawable.play_music_icon);
                }else{
                    mediaPlayer.start();
                    playBtn.setImageResource(R.drawable.pause_icon);
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0);
                }
            }
        });
        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.coordinator);
        View nowPlayingLayout =  coordinatorLayout.findViewById(R.id.musicControlLayout);
        BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(nowPlayingLayout);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Toast.makeText(getActivity(), "Collapsed", Toast.LENGTH_SHORT);
                        sheetUp = false;
                        //trackImgSmall.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            trackImgSmall.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(70)));
                            trackImgSmall.setRenderEffect(null);
                        }
                        musicControlLayout.setOrientation(LinearLayout.HORIZONTAL);
                        musicControlLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(70)));

                        trackControl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 3));
                        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        backBtn.setLayoutParams(btnParams);
                        playBtn.setLayoutParams(btnParams);
                        skipBtn.setLayoutParams(btnParams);

                        trackImgBig.setVisibility(View.GONE);
                        seekBar.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Toast.makeText(getActivity(), "Dragging", Toast.LENGTH_SHORT);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Toast.makeText(getActivity(), "Expanded", Toast.LENGTH_SHORT);
                        sheetUp = true;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            trackImgSmall.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                            trackImgSmall.setRenderEffect(RenderEffect.createBlurEffect(20, 20, Shader.TileMode.MIRROR));
                        }
                        musicControlLayout.setOrientation(LinearLayout.VERTICAL);
                        musicControlLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                        trackControl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
                        LinearLayout.LayoutParams btnParamsBig = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(70), 1);
                        backBtn.setLayoutParams(btnParamsBig);
                        playBtn.setLayoutParams(btnParamsBig);
                        skipBtn.setLayoutParams(btnParamsBig);

                        LinearLayout.LayoutParams trackParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 100);
                        trackParams.setMargins(dpToPx(50),dpToPx(50), dpToPx(50), dpToPx(50));
                        trackImgBig.setLayoutParams(trackParams);

                        trackImgBig.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);
                        if(nowPlaying != null){
                            trackImgBig.setImageResource(nowPlaying.getImg());
                        }

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
    public void onItemClick(int pos) {
        if(!sheetUp) {
            if(musicPage){
                Music selectedMusic = musicList.get(pos);
                changeNowPlaying(selectedMusic);
                musicQueue.clear();
                musicQueue.add(selectedMusic);
                playTrack(selectedMusic);
            }else{
                List<Music> playlistTracks = new ArrayList<>();
                for(int i = 0; i < playlists.get(pos).getMusicList().size(); i++){
                    playlistTracks.add(playlists.get(pos).getMusicList().get(i));
                }
                musicQueue = playlistTracks;
                Log.d("MusicQueue", playlists.get(pos).getMusicList().size() + "");
                changeNowPlaying(musicQueue.get(0));
                playTrack(musicQueue.get(0));
            }
        }
    }

    void changeNowPlaying(Music music){
        nowPlaying = music;
        musicControlLayout.setBackgroundResource(R.drawable.music_control_gradient);
        trackImgSmall.setImageResource(music.getImg());
        trackImgBig.setImageResource(music.getImg());
        songTitle.setText(music.getName());

    }

    int dpToPx(int dps){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    private void playTrack(Music music){
        try{
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getActivity(), Uri.parse("android.resource://" + getContext().getPackageName() + "/" + music.getFile()));
            mediaPlayer.prepare();
            mediaPlayer.start();
            playBtn.setImageResource(R.drawable.pause_icon);
            seekBar.setMax(mediaPlayer.getDuration());
            musicQueue.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }
}