package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;

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

        ArrayAdapter<RecordWord> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listRecordWords);

        lvRecordsWords.setAdapter(adapter);
    }
}