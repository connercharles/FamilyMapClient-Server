package com.example.familymapclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.familymapclient.R;
import com.example.familymapclient.datacache.DataCache;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // link everything
        final Switch lifeLine = findViewById(R.id.lifeSwitch);
        final Switch familyLine = findViewById(R.id.familySwitch);
        final Switch spouseLine = findViewById(R.id.spouseSwitch);
        final Switch fatherSide = findViewById(R.id.fatherSwitch);
        final Switch motherSide = findViewById(R.id.motherSwitch);
        final Switch maleEvents = findViewById(R.id.maleSwitch);
        final Switch femaleEvents = findViewById(R.id.femaleSwitch);
        TextView logout  = findViewById(R.id.logoutTxt);

        // make sure the previous settings are still in tact
        lifeLine.setChecked(DataCache.getInstance().lifeLines);
        familyLine.setChecked(DataCache.getInstance().familyLines);
        spouseLine.setChecked(DataCache.getInstance().spouseLines);
        fatherSide.setChecked(DataCache.getInstance().fatherLines);
        motherSide.setChecked(DataCache.getInstance().motherLines);
        maleEvents.setChecked(DataCache.getInstance().maleLines);
        femaleEvents.setChecked(DataCache.getInstance().femaleLines);

        // listener for switches
        CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataCache.getInstance().lifeLines = lifeLine.isChecked();
                DataCache.getInstance().familyLines = familyLine.isChecked();
                DataCache.getInstance().spouseLines = spouseLine.isChecked();
                DataCache.getInstance().fatherLines = fatherSide.isChecked();
                DataCache.getInstance().motherLines = motherSide.isChecked();
                DataCache.getInstance().maleLines = maleEvents.isChecked();
                DataCache.getInstance().femaleLines = femaleEvents.isChecked();
            }
        };

        // set up all the listeners
        lifeLine.setOnCheckedChangeListener(switchListener);
        familyLine.setOnCheckedChangeListener(switchListener);
        spouseLine.setOnCheckedChangeListener(switchListener);
        motherSide.setOnCheckedChangeListener(switchListener);
        maleEvents.setOnCheckedChangeListener(switchListener);
        femaleEvents.setOnCheckedChangeListener(switchListener);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // destroy session
                DataCache.getInstance().selfDestruct();

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

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
