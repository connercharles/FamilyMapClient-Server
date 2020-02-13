package com.example.familymapclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapclient.R;
import com.example.familymapclient.datacache.DataCache;
import com.example.familymapclient.model.EventCli;
import com.example.familymapclient.model.PersonCli;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Iconify.with(new FontAwesomeModule());

        final RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()){
                    ArrayList<PersonCli> people = DataCache.getInstance().searchPeople(query.toLowerCase());
                    ArrayList<EventCli> events = DataCache.getInstance().searchEvents(query.toLowerCase());

                    searchAdapter adapter = new searchAdapter(people, events);
                    recyclerView.setAdapter(adapter);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    ArrayList<PersonCli> people = DataCache.getInstance().searchPeople(newText.toLowerCase());
                    ArrayList<EventCli> events = DataCache.getInstance().searchEvents(newText.toLowerCase());

                    searchAdapter adapter = new searchAdapter(people, events);
                recyclerView.setAdapter(adapter);
                }

                return false;
            }
        });
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

    private class searchAdapter extends RecyclerView.Adapter<searchViewHolder> {
        private final List<PersonCli> personList;
        private final List<EventCli> eventList;


        searchAdapter(List<PersonCli> personList, List<EventCli> eventList) {
            this.personList = personList;
            this.eventList = eventList;
        }

        @Override
        public int getItemViewType(int position) {
            return position < personList.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.person_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.event_item, parent, false);
            }

            return new searchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull searchViewHolder holder, int position) {
            if(position < personList.size()) {
                holder.bind(personList.get(position));
            } else {
                holder.bind(eventList.get(position - personList.size()));
            }
        }

        @Override
        public int getItemCount() {
            return personList.size() + eventList.size();
        }
    }

    private class searchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView info;
        private final ImageView icon;

        private final int viewType;
        private PersonCli person;
        private EventCli event;

        searchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                name = itemView.findViewById(R.id.person_name);
                info = null;
                icon = itemView.findViewById(R.id.person_icon);
            } else {
                name = itemView.findViewById(R.id.event_info);
                info = itemView.findViewById(R.id.event_person);
                icon = itemView.findViewById(R.id.event_icon);
            }
        }

        private void bind(PersonCli person) {
            this.person = person;
            name.setText(person.getFullName());

            // for genders
            if (person.getGender().equals("m")){
                icon.setImageDrawable(new IconDrawable(SearchActivity.this,
                        FontAwesomeIcons.fa_male).colorRes(R.color.male_icon));
            } else {
                icon.setImageDrawable(new IconDrawable(SearchActivity.this,
                        FontAwesomeIcons.fa_female).colorRes(R.color.female_icon));
            }
        }

        private void bind(EventCli event) {
            this.event = event;
            name.setText(event.toStringNameless());
            info.setText(event.getPerson().getFullName());
            icon.setImageDrawable(new IconDrawable(SearchActivity.this,
                    FontAwesomeIcons.fa_map_marker));
        }

        @Override
        public void onClick(View view) {
            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                DataCache.getInstance().currentPerson = person;
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                startActivity(intent);
            } else {
                DataCache.getInstance().currentEvent = event;
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                startActivity(intent);
            }
        }
    }


}
