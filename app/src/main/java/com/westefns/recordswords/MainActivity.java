package com.westefns.recordswords;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.westefns.recordswords.dao.PhraseExampleDao;
import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;
import com.westefns.recordswords.services.AlertService;
import com.westefns.recordswords.util.WordAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private PhraseExampleDao phraseExampleDao;

    private FloatingActionButton fabAddNewRecordWord;
    private ListView lvRecordsWords;
    private TextView tvNoWords;

    private List<RecordWord> listRecordWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MainActivity.class));

        recordWordDao = new RecordWordDao(MainActivity.this);
        phraseExampleDao = new PhraseExampleDao(MainActivity.this);

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
                recordWord.setFrases(phraseExampleDao.getAllPhrasesByWord(recordWord.getWord()));

                Intent it = new Intent(getApplicationContext(), DetailsRecordWordActivity.class);

                it.putExtra("recordWord", recordWord);

                startActivity(it);
            });
        }
    }
}