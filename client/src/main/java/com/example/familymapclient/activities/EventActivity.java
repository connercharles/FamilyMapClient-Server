package com.example.familymapclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.familymapclient.R;
import com.example.familymapclient.fragments.MapFragment;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // display login or show map fragment based on logged in
        FragmentManager fm = this.getSupportFragmentManager();
        MapFragment mapFragment = new MapFragment();
        fm.beginTransaction()
                .add(R.id.fragmentContainerEvent, mapFragment)
                .commit();
    }

    // for the up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

}
