package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;

import java.util.Arrays;

public class EditRecordWordActivity extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private RecordWord recordWord;

    private EditText etvEditWord;
    private EditText etvEditTranslation;
    private Spinner spinnerEditClassification;

    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record_word);
        recordWordDao = new RecordWordDao(EditRecordWordActivity.this);

        etvEditWord = findViewById(R.id.etvEditWord);
        etvEditTranslation = findViewById(R.id.etvEditTranslation);
        spinnerEditClassification = findViewById(R.id.spinnerEditClassification);
        btnEdit = findViewById(R.id.btnEdit);

        Intent intent = getIntent();

        recordWord = (RecordWord) intent.getExtras().getSerializable("recordWord");

        etvEditWord.setText(recordWord.getWord());
        etvEditTranslation.setText(recordWord.getTranslation());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditRecordWordActivity.this, android.R.layout.simple_spinner_item, NewRecordWordActivity.classifications);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEditClassification.setAdapter(adapter);
        spinnerEditClassification.setSelection(adapter.getPosition(recordWord.getClassification()));

        btnEdit.setOnClickListener(v -> {
            recordWord.setWord(etvEditWord.getText().toString());
            recordWord.setTranslation(etvEditTranslation.getText().toString());
            recordWord.setClassification(spinnerEditClassification.getSelectedItem().toString());

            boolean recordWordSave = recordWordDao.update(recordWord);

            if (recordWordSave) {
                Intent it = new Intent(getApplicationContext(), DetailsRecordWordActivity.class);

                it.putExtra("recordWord", recordWord);

                startActivity(it);
            } else {
                // Notificar erro
            }
        });
    }
}