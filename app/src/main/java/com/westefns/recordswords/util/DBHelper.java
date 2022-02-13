package com.westefns.recordswords.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DBNAME = "db_records_words.db";

    public static String TABLE_WORDS = "words";
    public static String TABLE_PHRASEEXAMPLE = "phrase_exemple";

    private static final String SQL_CREATE_TABLE_WORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_WORDS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "word VARCHAR(50) NOT NULL, "
                + "classification VARCHAR(50) NOT NULL, "
                + "translation VARCHAR(50) NOT NULL, "
                + "create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)";

    private static final String SQL_CREATE_TABLE_PHRASEEXAMPLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PHRASEEXAMPLE
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "word VARCHAR(100) NOT NULL, "
            + "phrase VARCHAR(100) NOT NULL, "
            + "create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)";

    private static final String SQL_DROP_TABLE_WORDS =
            "DROP TABLE IF EXISTS " + TABLE_WORDS;

    private static final String SQL_DROP_TABLE_PHRASEEXAMPLE =
            "DROP TABLE IF EXISTS " + TABLE_PHRASEEXAMPLE;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_TABLE_WORDS);
            db.execSQL(SQL_CREATE_TABLE_PHRASEEXAMPLE);

            Log.i("WORD DB", "Sucesso ao criar o banco de dados!");
        } catch (Exception e) {
            Log.i("WORD DB", "Erro ao criar a tabela!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DROP_TABLE_WORDS);
            db.execSQL(SQL_DROP_TABLE_PHRASEEXAMPLE);

            onCreate(db);
        } catch (Exception e) {
            Log.i("WORD DB", "Erro ao apagar as tabelas!");
        }
    }
}
