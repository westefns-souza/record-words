package com.westefns.recordswords;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;

import java.text.SimpleDateFormat;

public class DetailsRecordWordActivity extends AppCompatActivity {
    private RecordWordDao recordWordDao;
    private RecordWord recordWord;

    private TextView txDetailsWord;
    private TextView txDetailsClassification;
    private TextView txDetailsTranslate;
    private TextView txDetailsCreatAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_record_word);
        recordWordDao = new RecordWordDao(DetailsRecordWordActivity.this);

        txDetailsWord = findViewById(R.id.txDetailsWord);
        txDetailsClassification = findViewById(R.id.txDetailsClassification);
        txDetailsTranslate = findViewById(R.id.txDetailsTranslation);
        txDetailsCreatAt = findViewById(R.id.txDetailsCreatAt);

        Intent intent = getIntent();

        recordWord = (RecordWord) intent.getExtras().getSerializable("recordWord");

        txDetailsWord.setText(recordWord.getWord());
        txDetailsClassification.setText(recordWord.getClassification());
        txDetailsTranslate.setText(recordWord.getTranslation());
        String createAt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(recordWord.getCreateAt());

        txDetailsCreatAt.setText("Cadastrado em: " + createAt);
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