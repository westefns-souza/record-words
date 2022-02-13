package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView tvNoWords;

    List<RecordWord> listRecordWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordWordDao = new RecordWordDao(MainActivity.this);

        fabAddNewRecordWord = findViewById(R.id.fabAddNewRecordWord);
        lvRecordsWords = findViewById(R.id.lvRecordsWords);
        tvNoWords = findViewById(R.id.tvNoWords);

        fabAddNewRecordWord.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), NewRecordWordActivity.class);

            startActivity(it);
        });

        listRecordWords = recordWordDao.getAllRecordsWord();

        if (listRecordWords.isEmpty()) {
            tvNoWords.setVisibility(View.VISIBLE);
            lvRecordsWords.setVisibility(View.INVISIBLE);
        } else {
            tvNoWords.setVisibility(View.INVISIBLE);
            lvRecordsWords.setVisibility(View.VISIBLE);

            WordAdapter adapter = new WordAdapter(this, listRecordWords);

            lvRecordsWords.setAdapter(adapter);

            lvRecordsWords.setOnItemClickListener((parent, view, position, id) -> {
                RecordWord recordWord = (RecordWord) parent.getItemAtPosition(position);

                Intent it = new Intent(getApplicationContext(), DetailsRecordWordActivity.class);

                it.putExtra("recordWord", recordWord);

                startActivity(it);
            });
        }
    }
}