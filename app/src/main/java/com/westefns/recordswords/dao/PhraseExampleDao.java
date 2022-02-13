package com.westefns.recordswords.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.westefns.recordswords.model.PhraseExample;
import com.westefns.recordswords.util.DBHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PhraseExampleDao {
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public PhraseExampleDao(Context context) {
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    public List<PhraseExample> getAllPhrasesByWordId(Long id){
        List<PhraseExample> listPhraseExample = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_PHRASEEXAMPLE + " WHERE idword = " + id;

        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            Long idphrase = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String phrase = cursor.getString(cursor.getColumnIndexOrThrow("phrase"));
            Timestamp create_at = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("create_at")));

            PhraseExample phraseExample = new PhraseExample();
            phraseExample.setId(idphrase);
            phraseExample.setRecordWordId(id);
            phraseExample.setExemple(phrase);
            phraseExample.setCreateAt(create_at);

            listPhraseExample.add(phraseExample);
        }

        cursor.close();

        return listPhraseExample;
    }

    public boolean create(PhraseExample phraseExample) {
        final String SQL_CREATE = "INSERT INTO " + DBHelper.TABLE_PHRASEEXAMPLE + " (idword, phrase) " +
                "VALUES ("
                + phraseExample.getRecordWordId() + ", '"
                + phraseExample.getExemple() + "')";

        try {
            write.execSQL(SQL_CREATE);

            Log.i("Info", "Frase salva no banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao salvar a frase no banco de dados!");

            return false;
        }
    }

    public boolean update(PhraseExample phraseExample) {
        final String SQL_UPDATE = "UPDATE " + DBHelper.TABLE_PHRASEEXAMPLE
                + " SET "
                + "phrase = '" + phraseExample.getExemple() + "' "
                + "WHERE id = " + phraseExample.getId();

        try {
            write.execSQL(SQL_UPDATE);

            Log.i("Info", "Frase atualizada no banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao atualizar a frase no banco de dados!");

            return false;
        }
    }

    public boolean delete(PhraseExample phraseExample) {
        final String SQL_UPDATE = "DELETE FROM " + DBHelper.TABLE_PHRASEEXAMPLE
                + " WHERE id = " + phraseExample.getId();

        try {
            write.execSQL(SQL_UPDATE);

            Log.i("Info", "Frase deletada do banco de dados!");

            return true;
        } catch (Exception e) {
            Log.i("Info", "Erro ao deletar a frase do banco de dados!");

            return false;
        }
    }
}
