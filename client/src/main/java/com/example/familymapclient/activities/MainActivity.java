package com.example.familymapclient.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.familymapclient.R;
import com.example.familymapclient.fragments.LoginFragment;
import com.example.familymapclient.fragments.MapFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    MapFragment mapFragment;
    // 10.0.2.2 is the FREAKING ip address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // display login or show map fragment based on logged in
        FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = new LoginFragment();
        fm.beginTransaction()
                .add(R.id.fragmentContainer, loginFragment)
                .commit();

        Iconify.with(new FontAwesomeModule());

    }

    // replaces whatever fragment with the map frag.
    public void callMap(){
        mapFragment = new MapFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainer, mapFragment).commit();
    }

}

