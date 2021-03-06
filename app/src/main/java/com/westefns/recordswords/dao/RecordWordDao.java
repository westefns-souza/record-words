package com.westefns.recordswords.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.westefns.recordswords.model.RecordWord;
import com.westefns.recordswords.util.DBHelper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RecordWordDao {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public RecordWordDao(Context context) {
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public List<RecordWord> getAllRecordsWord(){
        List<RecordWord> listRecordsWord = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_WORDS;

        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String word = cursor.getString(cursor.getColumnIndexOrThrow("word"));
            String classification = cursor.getString(cursor.getColumnIndexOrThrow("classification"));
            String translation = cursor.getString(cursor.getColumnIndexOrThrow("translation"));
            Timestamp create_at = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("create_at")));

            RecordWord recordWord = new RecordWord();
            recordWord.setId(id);
            recordWord.setWord(word);
            recordWord.setClassification(classification);
            recordWord.setTranslation(translation);
            recordWord.setCreateAt(create_at);

            listRecordsWord.add(recordWord);
        }

        cursor.close();

        return listRecordsWord;
    }

    public Long getIdByWord(String word) {
        String sql = "SELECT id FROM " + DBHelper.TABLE_WORDS + " WHERE word LIKE '" + word + "'";

        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();

        Long id = 0l;

        while (cursor.moveToNext()) {
            id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        }

        return id;
    }

    public boolean create(RecordWord recordWord) {
        final String SQL_CREATE = "INSERT INTO " + DBHelper.TABLE_WORDS + " (word, classification, translation) " +
                "VALUES ('"
                    + recordWord.getWord() + "', '"
                    + recordWord.getClassification() + "', '"
                    + recordWord.getTranslation() + "')";
        try {
            write.execSQL(SQL_CREATE);

            Log.i("Info", "Palavra salva no banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao salvar a palavra no banco de dados!");

            return false;
        }
    }

    public boolean update(RecordWord recordWord) {
        final String SQL_UPDATE = "UPDATE " + DBHelper.TABLE_WORDS
                + " SET "
                + "word = '" + recordWord.getWord() + "', "
                + "classification = '" + recordWord.getClassification() + "', "
                + "translation = '" + recordWord.getTranslation() + "' "
                + "WHERE id = " + recordWord.getId();

        try {
            write.execSQL(SQL_UPDATE);

            Log.i("Info", "Palavra atualizada no banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao atualizar a palavra no banco de dados!");

            return false;
        }
    }

    public boolean delete(RecordWord recordWord) {
        final String SQL_UPDATE = "DELETE FROM " + DBHelper.TABLE_WORDS
                + " WHERE id = " + recordWord.getId();

        try {
            write.execSQL(SQL_UPDATE);

            Log.i("Info", "Palavra deletada do banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao deletar a palavra do banco de dados!");

            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean getHaveWordToday() {
        String data = java.time.LocalDate.now().toString();

        String sql = "SELECT TOP 1 id FROM " + DBHelper.TABLE_WORDS + " WHERE create_at >= '" + data + " 00:00:00'";

        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();

        boolean haveWordToday = false;

        while (cursor.moveToNext()) {
            haveWordToday = true;
        }

        return haveWordToday;
    }
}
