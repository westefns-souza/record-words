package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;

import java.util.List;

public class NewRecordWordActivity extends AppCompatActivity {
    RecordWordDao recordWordDao;

    EditText etvWord;
    EditText etvTranslation;
    Spinner spinnerClassification;

    Button btnSave;

    private String[] classifications = { "Verbo", "Substantivo", "Artigo" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record_word);
        recordWordDao = new RecordWordDao(NewRecordWordActivity.this);

        etvWord = findViewById(R.id.etvWord);
        etvTranslation = findViewById(R.id.etvTranslation);
        spinnerClassification = findViewById(R.id.spinnerClassification);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(NewRecordWordActivity.this, android.R.layout.simple_spinner_item, classifications);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerClassification.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            RecordWord recordWord = new RecordWord();
            recordWord.setWord(etvWord.getText().toString());
            recordWord.setTranslation(etvTranslation.getText().toString());
            recordWord.setClassification(spinnerClassification.getSelectedItem().toString());

            boolean recordWordSave = recordWordDao.create(recordWord);

            if (recordWordSave) {
                Intent it = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(it);
            } else {
              // Notificar erro
            }
        });
    }
}