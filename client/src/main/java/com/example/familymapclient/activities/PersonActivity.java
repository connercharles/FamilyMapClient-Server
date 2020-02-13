package com.example.familymapclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familymapclient.R;
import com.example.familymapclient.datacache.DataCache;
import com.example.familymapclient.model.EventCli;
import com.example.familymapclient.model.PersonCli;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.Collections;
import java.util.List;


public class PersonActivity extends AppCompatActivity {

    private PersonCli currentPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Iconify.with(new FontAwesomeModule());


        currentPerson = DataCache.getInstance().currentPerson;

        ExpandableListView expandEvent = findViewById(R.id.expandableEvents);

        expandEvent.setAdapter(new ExpandableListAdapter(currentPerson.getFilteredEvents(),
                currentPerson.getFamily()));

        // update display
        TextView personFirstName = findViewById(R.id.firstName_person);
        TextView personLastName = findViewById(R.id.lastName_person);
        TextView personGender = findViewById(R.id.gender_person);
        personFirstName.setText(currentPerson.getFirstName());
        personLastName.setText(currentPerson.getLastName());
        // make gender nicer
        if (currentPerson.getGender().equals("m")){
            personGender.setText("Male");
        } else{
            personGender.setText("Female");
        }

    }

    // I love stealing code from the professors :)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENT_GROUP_POSITION = 0;
        private static final int PERSON_GROUP_POSITION = 1;
        private static final int FATHER = 0;
        private static final int MOTHER = 1;
        private static final int SPOUSE = 2;
        private static final String FATHER_S = "Father";
        private static final String MOTHER_S = "Mother";
        private static final String SPOUSE_S = "Spouse";
        private static final String CHILD_S = "Child";

        private final List<EventCli> eventsList;
        private final List<PersonCli> familyList;

        ExpandableListAdapter(List<EventCli> eventsList, List<PersonCli> familyList) {
            this.eventsList = eventsList;
            Collections.sort(eventsList);

            this.familyList = familyList;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return currentPerson.getEvents().size();
                case PERSON_GROUP_POSITION:
                    return currentPerson.getFamily().size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return getString(R.string.eventsTitle);
                case PERSON_GROUP_POSITION:
                    return getString(R.string.personsTitle);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return eventsList.get(childPosition);
                case PERSON_GROUP_POSITION:
                    return familyList.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    titleView.setText(R.string.eventsTitle);
                    break;
                case PERSON_GROUP_POSITION:
                    titleView.setText(R.string.personsTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            switch(groupPosition) {
                case EVENT_GROUP_POSITION:
                    convertView = getLayoutInflater().inflate(R.layout.event_item, parent, false);
                    initializeEventView(convertView, childPosition);
                    break;
                case PERSON_GROUP_POSITION:
                    convertView = getLayoutInflater().inflate(R.layout.person_item, parent, false);
                    initializePersonView(convertView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        private void initializeEventView(View eventItemView, final int childPosition) {
            if (eventsList.size() == 0) {
                return;
            }

            TextView eventNameView = eventItemView.findViewById(R.id.event_info);
            eventNameView.setText(eventsList.get(childPosition).toStringNameless());

            TextView eventPersonView = eventItemView.findViewById(R.id.event_person);
            eventPersonView.setText(eventsList.get(childPosition).getPerson().getFullName());

            ImageView iconView = eventItemView.findViewById(R.id.event_icon);
            iconView.setImageDrawable(new IconDrawable(PersonActivity.this,
                    FontAwesomeIcons.fa_map_marker));

            eventItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataCache.getInstance().currentEvent = eventsList.get(childPosition);
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    startActivity(intent);
                }
            });
        }

        private void initializePersonView(View personItemView, final int childPosition) {
            TextView personNameView = personItemView.findViewById(R.id.person_name);
            personNameView.setText(familyList.get(childPosition).getFullName());

            TextView personRelationView = personItemView.findViewById(R.id.person_relation);

            // for the relation to the current person
            switch (childPosition) {
                case FATHER:
                    personRelationView.setText(FATHER_S);
                    break;
                case MOTHER:
                    personRelationView.setText(MOTHER_S);
                    break;
                case SPOUSE:
                    personRelationView.setText(SPOUSE_S);
                    break;
                default:
                    personRelationView.setText(CHILD_S);
                    break;
            }

            ImageView iconView = personItemView.findViewById(R.id.person_icon);

            // for genders
            if (familyList.get(childPosition).getGender().equals("m")){
                iconView.setImageDrawable(new IconDrawable(PersonActivity.this,
                        FontAwesomeIcons.fa_male).colorRes(R.color.male_icon));
            } else {
                iconView.setImageDrawable(new IconDrawable(PersonActivity.this,
                        FontAwesomeIcons.fa_female).colorRes(R.color.female_icon));
            }

            personItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataCache.getInstance().currentPerson = familyList.get(childPosition);
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
