package com.westefns.recordswords.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DBNAME = "db_records_words";

    public static String TABLE_WORDS = "words";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_WORDS
                + "(id INTERGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "word VARCAHR(50) NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        try {
            db.execSQL(sql);

            Log.i("WORD DB", "Sucesso ao criar o banco de dados!");
        } catch (Exception e) {
            Log.i("WORD DB", "Erro ao criar a tabela!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS" + TABLE_WORDS + ";";

        try {
            db.execSQL(sql);
        } catch (Exception e) {
            Log.i("WORD DB", "Erro ao apagar as tabelas!");
        }
    }
}
