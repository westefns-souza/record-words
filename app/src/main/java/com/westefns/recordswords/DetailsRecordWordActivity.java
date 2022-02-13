package com.westefns.recordswords;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.westefns.recordswords.dao.PhraseExampleDao;
import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.PhraseExample;
import com.westefns.recordswords.model.RecordWord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailsRecordWordActivity extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private PhraseExampleDao phraseExampleDao;
    private RecordWord recordWord;

    private TextView txDetailsWord;
    private TextView txDetailsClassification;
    private TextView txDetailsTranslate;
    private TextView txDetailsCreatAt;

    private ListView lvDetailsPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_record_word);
        recordWordDao = new RecordWordDao(DetailsRecordWordActivity.this);
        phraseExampleDao = new PhraseExampleDao(DetailsRecordWordActivity.this);

        txDetailsWord = findViewById(R.id.txDetailsWord);
        txDetailsClassification = findViewById(R.id.txDetailsClassification);
        txDetailsTranslate = findViewById(R.id.txDetailsTranslation);
        txDetailsCreatAt = findViewById(R.id.txDetailsCreatAt);
        lvDetailsPhrase = findViewById(R.id.lvDetailsPhrase);

        Intent intent = getIntent();

        recordWord = (RecordWord) intent.getExtras().getSerializable("recordWord");

        txDetailsWord.setText(recordWord.getWord());
        txDetailsClassification.setText(recordWord.getClassification());
        txDetailsTranslate.setText(recordWord.getTranslation());
        String createAt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(recordWord.getCreateAt());

        txDetailsCreatAt.setText("Cadastrado em: " + createAt);

        List<String> phrases = new ArrayList<>();

        for (PhraseExample phraseExample : recordWord.getPhrases()){
            phrases.add(phraseExample.getExemple());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailsRecordWordActivity.this, android.R.layout.simple_spinner_item, phrases);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lvDetailsPhrase.setAdapter(adapter);

        lvDetailsPhrase.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialogConfirmDeletePhrase dialog = new AlertDialogConfirmDeletePhrase(phraseExampleDao, recordWord.getPhrases().get(position));
            dialog.show(getSupportFragmentManager(), "DeletePhrase");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deltails, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.memuDetailDelete:
                AlertDialogConfirmDelete dialog = new AlertDialogConfirmDelete(recordWordDao, recordWord);
                dialog.show(getSupportFragmentManager(), "DeleteWord");
                break;
            case R.id.memuDetailEdit:
                Intent it = new Intent(getApplicationContext(), EditRecordWordActivity.class);

                it.putExtra("recordWord", recordWord);

                startActivity(it);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}