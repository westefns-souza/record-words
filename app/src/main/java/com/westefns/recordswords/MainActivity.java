package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;
import com.westefns.recordswords.util.WordAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecordWordDao recordWordDao;

    FloatingActionButton fabAddNewRecordWord;
    ListView lvRecordsWords;

    List<RecordWord> listRecordWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordWordDao = new RecordWordDao(MainActivity.this);

        fabAddNewRecordWord = findViewById(R.id.fabAddNewRecordWord);
        lvRecordsWords = findViewById(R.id.lvRecordsWords);

        fabAddNewRecordWord.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), NewRecordWordActivity.class);

            startActivity(it);
        });

        listRecordWords = recordWordDao.getAllRecordsWord();

        WordAdapter adapter = new WordAdapter(this, listRecordWords);

        lvRecordsWords.setAdapter(adapter);

        lvRecordsWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordWord recordWord = (RecordWord) parent.getItemAtPosition(position);

                Intent it = new Intent(getApplicationContext(), DetailsRecordWord.class);

                it.putExtra("recordWord", recordWord);

                startActivity(it);
            }

        });
    }
}