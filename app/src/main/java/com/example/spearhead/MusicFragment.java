package com.example.spearhead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    List<Playlist> playlists = new ArrayList<>();
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
    Boolean threadDone = false;
    public static final String UserPREFRENCE = "UserPref";
    public static final String UID = "UserID";
    int userID;
    FloatingActionButton addPlaylistBtn;

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
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(UserPREFRENCE, Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt(UID, 0);
        getActivity().setTitle("Music");
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(nowPlaying != null){
            changeNowPlaying(nowPlaying);
        }
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseHandler db = new DatabaseHandler(getActivity());
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        LinearLayout allMusicBtn = (LinearLayout) view.findViewById(R.id.AllMusicButton);
        LinearLayout playlistBtn = (LinearLayout) view.findViewById(R.id.PlaylistsButton);
        allMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerList.setAdapter(musicListAdapter);
                addPlaylistBtn.setVisibility(View.GONE);
                musicPage = true;
            }
        });
        //playlistAdapter = new MusicListAdapter(playlists, this);

        musicList = db.getAllMusic();
        playlists = db.getPlaylists(userID);

        playlistsAdapter = new PlaylistsAdapter(this, playlists);
        playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerList.setAdapter(playlistsAdapter);
                musicPage = false;
                addPlaylistBtn.setVisibility(View.VISIBLE);
            }
        });

        addPlaylistBtn = view.findViewById(R.id.addPLaylistBtn);
        addPlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sheetUp){
                    String[] titleList = new String[musicList.size()];
                    boolean[] checkedItems = new boolean[musicList.size()];
                    List<String> selectedItems = Arrays.asList(titleList);

                    for(int i = 0; i < musicList.size(); i++){
                        titleList[i] = musicList.get(i).getName();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    //Set title for dialog
                    builder.setTitle("Add Playlist");

                    //Set icon for the dialog
                    builder.setIcon(R.drawable.app_logo);

                    EditText input = new EditText(getActivity());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setHint("Playlist name");

                    // now this is the function which sets the alert dialog for multiple item selection ready
                    builder.setMultiChoiceItems(titleList, checkedItems, (dialog, which, isChecked) -> {
                        checkedItems[which] = isChecked;
                        String currentItem = selectedItems.get(which);
                    });

                    builder.setView(input);

                    // alert dialog shouldn't be cancellable
                    builder.setCancelable(false);

                    // handle the positive button of the dialog
                    builder.setPositiveButton("Done", (dialog, which) -> {
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        List<Music> selectedMusic = new ArrayList<>();
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                Log.d("Music Selected", musicList.get(i).getArtist());
                                Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                                selectedMusic.add(musicList.get(i));
                            }
                        }
                        String name = input.getText().toString();
                        db.addPlaylist(userID, selectedMusic, name);

                    });

                    // handle the negative button of the alert dialog
                    builder.setNegativeButton("CANCEL", (dialog, which) -> {});

                    // handle the neutral button of the dialog to clear the selected items boolean checkedItem
                    builder.setNeutralButton("CLEAR ALL", (dialog, which) -> {
                        Arrays.fill(checkedItems, false);
                    });

                    // create the builder
                    builder.create();

                    // create the alert dialog with the alert dialog builder instance
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

//        TextView recent = view.findViewById(R.id.textView2);
//        recent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseHandler db = new DatabaseHandler(getActivity());
//                db.addMusic(new Music(R.drawable.into_the_night, "Yoru Ni Kakeru", "YOASOBI", R.raw.yoru_ni_kakeru));
//                db.addMusic(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates of State", R.raw.i_wanna_run));
//                db.addMusic(new Music(R.drawable.kyoukaisen, "Kyoukaisen", "Amazashi", R.raw.kyoukaisen));
//                db.addMusic(new Music(R.drawable.lilas, "LilaS", "Lena", R.raw.lilas));
//                db.addMusic(new Music(R.drawable.romeo_and_cinderella, "Romeo and Cinderella", "Hatsune Miku", R.raw.romeo_and_cinderella));
//                db.addMusic(new Music(R.drawable.calc, "Calc", "Hatsune Miku", R.raw.calc));
//
//            }
//        });

//        List<Music> testMusicList = new ArrayList<>();
//        testMusicList.add(new Music(R.drawable.i_wanna_run, "I Wanna Run", "Mates Of State", R.raw.i_wanna_run));
//        testMusicList.add(new Music(R.drawable.calc, "Calc", "Hatsune Miku", R.raw.calc));




        recyclerList= (RecyclerView) view.findViewById(R.id.recentMusicList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerList.setLayoutManager(linearLayoutManager);
        musicListAdapter= new MusicListAdapter(musicList, this);
        recyclerList.setAdapter(musicListAdapter);

        if(mediaPlayer == null){
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
        }


        musicControlLayout = (LinearLayout) view.findViewById(R.id.musicControl);
        trackImgSmall = (ImageView) view.findViewById(R.id.musicControlBg);
        songTitle = (TextView) view.findViewById(R.id.currentSongTitle);
        trackImgBig = (ImageView) view.findViewById(R.id.trackImgBig);
        trackControl = (LinearLayout) view.findViewById(R.id.trackControl);
        backBtn = (ImageView) view.findViewById(R.id.backBtn);
        playBtn = (ImageView) view.findViewById(R.id.playBtn);
        skipBtn = (ImageView) view.findViewById(R.id.skipBtn);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        setUpSeekBar();

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
                Log.d("Pos", pos + "");
                Log.d("Size", playlists.get(pos).getMusicList().size() + "");
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
            Log.d("File", music.getFile() + "");
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

    void setUpSeekBar(){
        if(!threadDone){
            new Thread(() -> {
                while (mediaPlayer != null) {
                    try {
                        Thread.sleep(1000);
                        if (mediaPlayer.isPlaying()) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition();
                            Log.d("Current Position", mCurrentPosition + "");
                            seekBar.setProgress(mCurrentPosition);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            threadDone = true;
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
        }else{
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
        }
    }
}