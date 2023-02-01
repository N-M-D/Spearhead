package com.example.spearhead;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.spearhead.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MusicFragment musicFragment = new MusicFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment(), false);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homepage:
                    replaceFragment(new HomeFragment(), false);
                    break;
                case R.id.timerpage:
                    replaceFragment(new TimerFragment(), false);
                    break;
                case R.id.musicpage:
                    replaceFragment(new MusicFragment(), true);
                    break;
                case R.id.profilepage:
                    replaceFragment(new ProfileFragment(), false);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment, Boolean isMusicFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
//        MusicFragment musicFragment = (MusicFragment) fragmentManager.findFragmentByTag("FragmentTag");
//
//        if(musicFragment == null){
//            musicFragment = new MusicFragment();
//        }
        String fragmentTag = "";
        if(isMusicFragment){
            if(fragmentManager.findFragmentByTag("FragmentTag") == null){
                musicFragment = (MusicFragment) fragment;
                Log.d("Is Music Fragment null", "True");
            }
            fragment = musicFragment;
            Log.d("Is Music Fragment", isMusicFragment.toString());
            fragmentTag = "FragmentTag";
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, fragmentTag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}