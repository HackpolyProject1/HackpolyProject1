package com.example.project1.hackpolyproject1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPage extends AppCompatActivity {

    ListView listView;
    static ArrayList<String> tasks = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.project1.hackpolyproject1", Context.MODE_PRIVATE);
        set = sharedPreferences.getStringSet("tasks", null);
        tasks.clear();

        if (set != null) {

            tasks.addAll(set);

        } else {

            tasks.add("I need someone to shit my lawn");
            tasks.add("I need a hitman");
            set = new HashSet<String>();
            set.addAll(tasks);
            sharedPreferences.edit().putStringSet("tasks", set).apply();

        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), EditTask.class);
                i.putExtra("noteId", position);
                startActivity(i);

            }

        });
    }
}
