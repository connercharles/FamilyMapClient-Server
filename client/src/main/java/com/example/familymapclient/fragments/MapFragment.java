package com.example.familymapclient.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.familymapclient.R;
import com.example.familymapclient.activities.EventActivity;
import com.example.familymapclient.activities.PersonActivity;
import com.example.familymapclient.activities.SearchActivity;
import com.example.familymapclient.activities.SettingsActivity;
import com.example.familymapclient.datacache.DataCache;
import com.example.familymapclient.model.EventCli;
import com.example.familymapclient.model.PersonCli;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback  {
    private GoogleMap map;
    private TextView infoText;
    private ImageView iconView;
    static private ArrayList<Polyline> lines = new ArrayList<>();

    static private final int WIDTH = 10;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        infoText = view.findViewById(R.id.mapTextView);
        iconView = view.findViewById(R.id.genderImageView);

        // listener for the bottom tab
        infoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });

        Iconify.with(new FontAwesomeModule());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getActivity().getClass() != EventActivity.class){
            super.onCreateOptionsMenu(menu, inflater);

            inflater.inflate(R.menu.main_menu, menu);

            MenuItem searchMenuItem = menu.findItem(R.id.searchMenuItem);
            searchMenuItem.setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                    .colorRes(R.color.white)
                    .actionBarSize());
            MenuItem settingsMenuItem = menu.findItem(R.id.settingMenuItem);
            settingsMenuItem.setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                    .colorRes(R.color.white)
                    .actionBarSize());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch(menu.getItemId()) {
            case R.id.searchMenuItem:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.settingMenuItem:
                Intent intentSetting = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intentSetting);
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback(this);

        drawMarkers();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // get event data
                EventCli event = DataCache.getInstance().getEvent((String)marker.getTag());

                // display info at bottom
                infoText.setText(event.toString());

                // change for male or female
                if (event.getPerson().getGender().equals("m")){
                    iconView.setImageDrawable(new IconDrawable(getActivity(),
                            FontAwesomeIcons.fa_male).colorRes(R.color.male_icon));
                } else {
                    iconView.setImageDrawable(new IconDrawable(getActivity(),
                            FontAwesomeIcons.fa_female).colorRes(R.color.female_icon));
                }

                // set current person as event called
                DataCache.getInstance().currentPerson = event.getPerson();

                // clear existing lines then draw new ones
                clearLines();
                drawFamilyLines(event, WIDTH);
                drawSpouseLines(event);
                drawLifeEvents(event.getPerson());

                return false;
            }
        });


        // if there was a clicked event
        if (DataCache.getInstance().currentEvent != null) {
            EventCli currentEvent = DataCache.getInstance().currentEvent;
            LatLng clickedEvent = new LatLng(Double.parseDouble(currentEvent.getLatitude()),
                Double.parseDouble(currentEvent.getLongitude()));
            map.animateCamera(CameraUpdateFactory.newLatLng(clickedEvent));

            // set up bottom info stuff
            infoText.setText(currentEvent.toString());

            // change for male or female
            if (currentEvent.getPerson().getGender().equals("m")){
                iconView.setImageDrawable(new IconDrawable(getActivity(),
                        FontAwesomeIcons.fa_male).colorRes(R.color.male_icon));
            } else {
                iconView.setImageDrawable(new IconDrawable(getActivity(),
                        FontAwesomeIcons.fa_female).colorRes(R.color.female_icon));
            }

            // set current person as event called
            DataCache.getInstance().currentPerson = currentEvent.getPerson();

            // clear existing lines then draw new ones
            clearLines();
            drawFamilyLines(currentEvent, 10);
            drawSpouseLines(currentEvent);
            drawLifeEvents(currentEvent.getPerson());

        }
    }

    private void drawLifeEvents(PersonCli person){
        if (DataCache.getInstance().lifeLines){
            final int color = Color.MAGENTA;

            Collections.sort(person.getEvents());

            EventCli lastEvent = null;
            for (EventCli event : person.getEvents()) {
                if (lastEvent != null){
                    drawLine(lastEvent.getCoordinates(), event.getCoordinates(), WIDTH, color);
                }
                lastEvent = event;
            }
        }
    }

    private void drawSpouseLines(EventCli currentEvent) {
        // check if gender lines are allowed
        if ((currentEvent.getPerson().getGender().equals("m") && (!DataCache.getInstance().femaleLines))
        || (currentEvent.getPerson().getGender().equals("f") && (!DataCache.getInstance().maleLines))){
            return;
        }
        if (DataCache.getInstance().spouseLines) {
            final int color = Color.RED;

            // get spouse and make sure it's not null
            PersonCli yourBoo = currentEvent.getPerson().getSpouse();

            if (yourBoo != null){
                yourBoo.setEvents(DataCache.getInstance().getEvents(yourBoo.getpersonID()));
                Collections.sort(yourBoo.getEvents());
                drawLine(currentEvent.getCoordinates(), yourBoo.getEvents().get(0).getCoordinates(), WIDTH, color);
            }
        }
    }

    private void drawFamilyLines(EventCli currentEvent, float width) {
        if (DataCache.getInstance().familyLines){
            final int color = Color.BLUE;

            // go through father's side
            PersonCli father = currentEvent.getPerson().getFather();
            if (father != null && DataCache.getInstance().maleLines) {
                // sort father events
                Collections.sort(father.getEvents());
                // get birth or earliest event
                EventCli newEvent = father.getEvents().get(0);
                drawLine(currentEvent.getCoordinates(), newEvent.getCoordinates(), width, color);

                width -= 2;
                // go recursive
                drawFamilyLines(newEvent, width);
            }

            // go through mother's side
            PersonCli mother = currentEvent.getPerson().getMother();
            if (mother != null && DataCache.getInstance().femaleLines) {
                // sort father events
                Collections.sort(mother.getEvents());
                // get birth or earliest event
                EventCli newEvent = mother.getEvents().get(0);
                drawLine(currentEvent.getCoordinates(), newEvent.getCoordinates(), width, color);

                width -= 2;
                // go recursive
                drawFamilyLines(newEvent, width);
            }
        }
    }

    // draws line between two points
    private void drawLine(LatLng point1, LatLng point2, float width, int color) {
        if (width < 1) {
            width = 1;
        }
        PolylineOptions line =
                new PolylineOptions().add(point1, point2)
                        .color(color).width(width);
        lines.add(map.addPolyline(line));
    }

    private void clearLines(){
        for (Polyline line : lines) {
            line.remove();
        }
        lines.clear();
    }


    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (map != null){
            drawMarkers();
        }
    }

    private void drawMarkers(){
        clearMarkers();

        float colorNum = 0;
        HashMap<String, Float> eventColors = new HashMap<>();

        // add all the events to the map
        for (EventCli event : DataCache.getInstance().getFilteredEvents()) {
            // change marker colors based on the event type
            Float color = eventColors.get(event.getEventType().toLowerCase());
            if (color == null){
                colorNum += 20;
                eventColors.put(event.getEventType().toLowerCase(), colorNum);
                color = colorNum;
            }

            LatLng marker = new LatLng(Double.parseDouble(event.getLatitude()),
                    Double.parseDouble(event.getLongitude()));

            // print event type
            Marker newMarker = map.addMarker(new MarkerOptions().position(marker)
                    .icon(BitmapDescriptorFactory.defaultMarker(color)));
            newMarker.setTag(event.getEventID());
            DataCache.getInstance().markers.add(newMarker);
        }

    }

    private void clearMarkers(){
        for (Marker marker : DataCache.getInstance().markers) {
            marker.remove();
        }
        DataCache.getInstance().markers.clear();
    }

}
