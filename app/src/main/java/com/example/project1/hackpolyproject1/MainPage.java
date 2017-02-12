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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPage extends AppCompatActivity {

    ListView listView;
    static String value;
    private Firebase Mref;
    private TextView Mvalue;
    static ArrayList<String> tasks = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    static Set<String> set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);
        listView = (ListView) findViewById(R.id.listView);
        CreateList();
    }

    public void CreateList() {
        if (set != null){
            set.clear();
        }



    }
    public void Firebase(){
        Mref = new Firebase("https://hackpoly-project.firebaseio.com/tasks/current/");
        com.firebase.client.ValueEventListener valueEventListener = Mref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                long valuei = dataSnapshot.getChildrenCount();

                //Toast.makeText(getApplicationContext(), String.valueOf(valuei), Toast.LENGTH_SHORT).show();



                if (tasks != null) {
                    tasks.clear();
                }
                if (set != null) {
                    set.clear();
                }
                for (int i = 0; i < valuei; i++) {
                    value = dataSnapshot.child(Integer.toString(i) + "/title").getValue().toString();

                    Listitup();


                }
                Toast.makeText(getApplicationContext(), String.valueOf(valuei), Toast.LENGTH_LONG).show();
                Mvalue = (TextView) findViewById(R.id.textView3);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void Listitup(){

        String insert = value;
        Intent i = getIntent();

        //if (set != null) {

        //  tasks.addAll(set);
//            Toast.makeText(getApplicationContext(), "I Ran Null", Toast.LENGTH_SHORT).show();

        //      } else {
        //Toast.makeText(getApplicationContext(), "I Ran Else", Toast.LENGTH_SHORT).show();
        tasks.add(insert);
        set = new HashSet<String>();
        set.addAll(tasks);

//        }

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
