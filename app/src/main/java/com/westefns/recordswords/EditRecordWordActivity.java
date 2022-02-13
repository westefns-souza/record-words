package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.westefns.recordswords.dao.PhraseExampleDao;
import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.PhraseExample;
import com.westefns.recordswords.model.RecordWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditRecordWordActivity extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private PhraseExampleDao phraseExampleDao;
    private RecordWord recordWord;

    private EditText etvEditWord;
    private EditText etvEditTranslation;
    private EditText etvEditFraseExemplos;
    private Spinner spinnerEditClassification;

    private ListView lvEditPhrases;

    private Button btnEdit;
    private Button btnEditPhrase;

    public List<String> phraseExamples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record_word);
        recordWordDao = new RecordWordDao(EditRecordWordActivity.this);
        phraseExampleDao = new PhraseExampleDao(EditRecordWordActivity.this);

        etvEditWord = findViewById(R.id.etvEditWord);
        etvEditTranslation = findViewById(R.id.etvEditTranslation);
        etvEditFraseExemplos = findViewById(R.id.etvEditFraseExemplos);
        spinnerEditClassification = findViewById(R.id.spinnerEditClassification);

        lvEditPhrases = findViewById(R.id.lvEditPhrases);

        btnEdit = findViewById(R.id.btnEdit);
        btnEditPhrase = findViewById(R.id.btnEditPhrase);

        Intent intent = getIntent();

        recordWord = (RecordWord) intent.getExtras().getSerializable("recordWord");

        etvEditWord.setText(recordWord.getWord());
        etvEditTranslation.setText(recordWord.getTranslation());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditRecordWordActivity.this, android.R.layout.simple_spinner_item, NewRecordWordActivity.classifications);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEditClassification.setAdapter(adapter);
        spinnerEditClassification.setSelection(adapter.getPosition(recordWord.getClassification()));

        if (!recordWord.getPhrases().isEmpty()) {
            for (PhraseExample phraseExample : recordWord.getPhrases()) {
                phraseExamples.add(phraseExample.getExemple());
            }

            ArrayAdapter<String> adapterPhrases = new ArrayAdapter<>(EditRecordWordActivity.this, android.R.layout.simple_spinner_item, phraseExamples);
            adapterPhrases.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            lvEditPhrases.setAdapter(adapterPhrases);
        }

        btnEdit.setOnClickListener(v -> {
            String word = etvEditWord.getText().toString();
            String traslation = etvEditTranslation.getText().toString();
            String classification = etvEditWord.getText().toString();

            if (word != null && !word.isEmpty() && traslation != null && !traslation.isEmpty() && !phraseExamples.isEmpty()) {
                recordWord.setWord(word);
                recordWord.setTranslation(traslation);
                recordWord.setClassification(classification);

                boolean recordWordSave = recordWordDao.update(recordWord);

                if (recordWordSave) {
                    phraseExampleDao.delete(recordWord.getWord());

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

                    recordWord.setFrases(phraseExampleDao.getAllPhrasesByWord(recordWord.getWord()));

                    Intent it = new Intent(getApplicationContext(), DetailsRecordWordActivity.class);

                    it.putExtra("recordWord", recordWord);

                    startActivity(it);
                } else {
                    // Notificar erro
                }
            }
        });

        btnEditPhrase.setOnClickListener(v -> {
            String phrase = etvEditFraseExemplos.getText().toString();

            if (phrase != null && !phrase.isEmpty()) {
                phraseExamples.add(etvEditFraseExemplos.getText().toString());

                ArrayAdapter<String> phrases = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, phraseExamples);

                lvEditPhrases.setAdapter(phrases);
            }
        });

        lvEditPhrases.setOnItemClickListener((parent, view, position, idt) -> {
            new AlertDialog.Builder(this)
                    .setMessage("Tem certeza que quer deletar essa frase?")
                    .setCancelable(false)
                    .setPositiveButton("Sim, Deletar", (dialog, id) -> {
                        phraseExamples.remove(position);

                        ArrayAdapter<String> phrases = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, phraseExamples);

                        lvEditPhrases.setAdapter(phrases);
                    })
                    .setNegativeButton("Não Deletar", (dialog, id) -> dialog.dismiss())
                    .show();
        });
    }
}