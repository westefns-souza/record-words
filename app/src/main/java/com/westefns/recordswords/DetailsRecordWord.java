package com.westefns.recordswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;

import java.text.SimpleDateFormat;

public class DetailsRecordWord extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private RecordWord recordWord;

    private TextView txDetailsWord;
    private TextView txDetailsClassification;
    private TextView txDetailsTranslate;
    private TextView txDetailsCreatAt;
    private Button btnDetailsDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_record_word);
        recordWordDao = new RecordWordDao(DetailsRecordWord.this);

        txDetailsWord = findViewById(R.id.txDetailsWord);
        txDetailsClassification = findViewById(R.id.txDetailsClassification);
        txDetailsTranslate = findViewById(R.id.txDetailsTranslation);
        txDetailsCreatAt = findViewById(R.id.txDetailsCreatAt);
        btnDetailsDelete = findViewById(R.id.btnDetailsDelete);

        Intent intent = getIntent();

        recordWord = (RecordWord) intent.getExtras().getSerializable("recordWord");

        txDetailsWord.setText(recordWord.getWord());
        txDetailsClassification.setText(recordWord.getClassification());
        txDetailsTranslate.setText(recordWord.getTranslation());
        String createAt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(recordWord.getCreateAt());

        txDetailsCreatAt.setText("Cadastrado em: " + createAt);

        btnDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean recordDeleted = recordWordDao.delete(recordWord);

                if (recordDeleted) {
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                }
            }
        });
    }
}