package com.example.project1.hackpolyproject1;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.client.Firebase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static ArrayAdapter<String> arrayAdapter;
    static String value;
    private Firebase Mref;
    private TextView Mvalue;
    static ArrayList<String> tasks = new ArrayList<>();
    static Set<String> set;
    public class User {

        public String name;
        public String email;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }


    //This is a comment added by Stanleyr001

    // change for testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button signIn = (Button) findViewById(R.id.button2);
        Button login = (Button) findViewById(R.id.button3);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(getApplicationContext(), MainPage.class);
                //startActivity(i);
                Firebase();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //test to pull from main.






    }

    public void Firebase(){
        Mref = new Firebase("https://hackpoly-project.firebaseio.com/tasks/current/");
        com.firebase.client.ValueEventListener valueEventListener = Mref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                long valuei = dataSnapshot.getChildrenCount();

                Toast.makeText(getApplicationContext(), String.valueOf(valuei), Toast.LENGTH_SHORT).show();



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
        setContentView(R.layout.activity_main_page);
        String insert = value;
        listView = (ListView) findViewById(R.id.listView);
        Intent i = getIntent();

        //if (set != null) {

          //  tasks.addAll(set);
//            Toast.makeText(getApplicationContext(), "I Ran Null", Toast.LENGTH_SHORT).show();

  //      } else {
            Toast.makeText(getApplicationContext(), "I Ran Else", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
