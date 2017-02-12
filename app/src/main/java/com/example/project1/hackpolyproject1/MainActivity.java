package com.example.project1.hackpolyproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    final String TAG = "TEST";

    ListView listView;
    static ArrayAdapter<String> arrayAdapter;
    static String value;
    private Firebase Mref;
    private TextView Mvalue;
    static ArrayList<String> tasks = new ArrayList<>();
    static Set<String> set;

    /*
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
    */

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //////////////////This is where my code starts//////////////////////
    // Configure Google Sign In
    //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public EditText email;
    public EditText password;


    ///////////////////END OF MY CODE////////////////////////////

    //This is a comment added by Stanleyr001

    // change for testing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (EditText) findViewById(R.id.edittext_email);
        password = (EditText) findViewById(R.id.edittext_password);



        /////////////More code I added////////////////
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d(TAG, user.toString());
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            };

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
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();






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
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //launch activity on button click;
        Button signUp = (Button) findViewById(R.id.button_singup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAccount(email.getText().toString(),password.getText().toString());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();

                    // Check if user's email is verified
                    boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
                } else {
                    Log.d(TAG, "user=null");
                }

                //Intent i = new Intent(getApplicationContext(), MainPage.class);
                //startActivity(i);
            }
        });

        //launch activity on button click
        Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn(email.getText().toString(), password.getText().toString());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();

                    // Check if user's email is verified
                    boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();

                //Intent i = new Intent(getApplicationContext(), MainPage.class);
                //startActivity(i);
                }
            }
        });

    }

    private void createAccount(String email, final String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "create fail");
                            CharSequence text = "The account could not be created: " + task.getException();
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "Success");
                            Intent i = new Intent(getApplicationContext(), MainPage.class);
                            startActivity(i);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            CharSequence text = "Log on Unsuccessful: " + task.getException();
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "Success");
                            Intent i = new Intent(getApplicationContext(), MainPage.class);
                            startActivity(i);
                        }
                    }
                });
        // [END sign_in_with_email]
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
