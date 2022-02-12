package com.westefns.recordswords.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.westefns.recordswords.model.RecordWord;
import com.westefns.recordswords.util.DBHelper;

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

        String sql = "SELECT * FROM " + DBHelper.TABLE_WORDS + ";";

        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String word = cursor.getString(cursor.getColumnIndexOrThrow("word"));
            Timestamp created_at = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("created_at")));

            RecordWord recordWord = new RecordWord();
            recordWord.setId(id);
            recordWord.setWord(word);
            recordWord.setCreatedAt(created_at);

            listRecordsWord.add(recordWord);
        }

        cursor.close();

        return listRecordsWord;
    }

    public boolean create(RecordWord recordWord) {
        ContentValues cv = new ContentValues();
        cv.put("word", recordWord.getWord());

        try {
            write.insert(DBHelper.TABLE_WORDS, null, cv);

            Log.i("Info", "Palavra salva no banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao salvar a palavra no banco de dados!");

            return false;
        }
    }

    public boolean update(RecordWord recordWord) {
        ContentValues cv = new ContentValues();
        cv.put("word", recordWord.getWord());

        try {
            write.update(DBHelper.TABLE_WORDS, cv, "id = ?", new String[] { String.valueOf(recordWord.getId()) });

            Log.i("Info", "Palavra atualizada no banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao atualizar a palavra no banco de dados!");

            return false;
        }
    }

    public boolean delete(RecordWord recordWord) {
        try {
            write.delete("PESSOA", "ID = ?", new String[]{String.valueOf(recordWord.getId())});

            Log.i("Info", "Palavra deletada do banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao deletar a palavra do banco de dados!");

            return false;
        }
    }
}
