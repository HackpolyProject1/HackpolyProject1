package com.example.project1.hackpolyproject1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class EditTask extends AppCompatActivity implements TextWatcher {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        EditText editText = (EditText) findViewById(R.id.editContent);

        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);

        if (noteId != -1) {

            editText.setText(TitlesActivity.tasks.get(noteId));


        }

        editText.addTextChangedListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // save user entered data
                /*SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.user.notes", Context.MODE_PRIVATE);
                String note = editText.getText().toString();
                sharedPreferences.edit().putString("note", note).apply();

                LoginActivity.arrayList.add(note);
                LoginActivity.arrayAdapter.notifyDataSetChanged();*/
                // back button in action bar pressed, go to parent activity
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        TitlesActivity.tasks.set(noteId, String.valueOf(s));
        TitlesActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.project1.hackpolyproject1", Context.MODE_PRIVATE);

        if (TitlesActivity.set == null) {

            TitlesActivity.set = new HashSet<String>();

        } else {

            TitlesActivity.set.clear();

        }


        TitlesActivity.set.addAll(TitlesActivity.tasks);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes", TitlesActivity.set).apply();


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}