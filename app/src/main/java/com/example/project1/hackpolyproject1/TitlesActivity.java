package com.example.project1.hackpolyproject1;

import android.content.Intent;
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
import java.util.Set;

public class TitlesActivity extends AppCompatActivity {

    ListView listView;
    static String value;
    public Firebase Mref;
    static ArrayList<String> tasks = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    static Set<String> set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_titles);
        listView = (ListView) findViewById(R.id.listView);
        Firebase();
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
