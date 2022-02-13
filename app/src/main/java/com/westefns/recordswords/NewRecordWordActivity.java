package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.westefns.recordswords.dao.PhraseExampleDao;
import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.PhraseExample;
import com.westefns.recordswords.model.RecordWord;
import com.westefns.recordswords.util.WordAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewRecordWordActivity extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private PhraseExampleDao phraseExampleDao;

    private EditText etvWord;
    private EditText etvTranslation;
    private EditText etvFraseExemplos;
    private Spinner spinnerClassification;

    private ListView lvPhrases;

    private Button btnSave;
    private Button btnAddPhrase;

    public static final String[] classifications = { "Verbo", "Substantivo", "Artigo" };
    public List<String> phraseExamples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record_word);
        recordWordDao = new RecordWordDao(NewRecordWordActivity.this);
        phraseExampleDao = new PhraseExampleDao(NewRecordWordActivity.this);

        etvWord = findViewById(R.id.etvWord);
        etvTranslation = findViewById(R.id.etvTranslation);
        spinnerClassification = findViewById(R.id.spinnerClassification);
        etvFraseExemplos = findViewById(R.id.etvFraseExemplos);

        lvPhrases = findViewById(R.id.lvPhrases);

        btnSave = findViewById(R.id.btnSave);
        btnAddPhrase = findViewById(R.id.btnAddPhrase);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewRecordWordActivity.this, android.R.layout.simple_spinner_item, classifications);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerClassification.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String word = etvWord.getText().toString();
            String traslation = etvTranslation.getText().toString();
            String classification = etvWord.getText().toString();

            if (word != null && !word.isEmpty() && traslation != null && !traslation.isEmpty() && !phraseExamples.isEmpty()) {
                RecordWord recordWord = new RecordWord();
                recordWord.setWord(word);
                recordWord.setTranslation(traslation);
                recordWord.setClassification(classification);

                boolean recordWordSave = recordWordDao.create(recordWord);

                if (recordWordSave) {
                    List<PhraseExample> listPhraseExample = new ArrayList<>();

                    for (String phrase : phraseExamples) {
                        PhraseExample phraseExample = new PhraseExample();
                        phraseExample.setExemple(phrase);
                        phraseExample.setRecordWord(word);
                        listPhraseExample.add(phraseExample);
                    }

                    for (PhraseExample phrase : listPhraseExample) {
                        phraseExampleDao.create(phrase);
                    }

                    Intent it = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(it);
                } else {
                    // Notificar erro
                }
            }
        });

        btnAddPhrase.setOnClickListener(v -> {
            String phrase = etvFraseExemplos.getText().toString();

            if (phrase != null && !phrase.isEmpty()) {
                phraseExamples.add(etvFraseExemplos.getText().toString());

                ArrayAdapter<String> phrases = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, phraseExamples);

                lvPhrases.setAdapter(phrases);
            }
        });
    }
}