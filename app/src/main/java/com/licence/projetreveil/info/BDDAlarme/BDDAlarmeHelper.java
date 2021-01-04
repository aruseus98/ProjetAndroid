package com.licence.projetreveil.info.BDDAlarme;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDDAlarmeHelper extends SQLiteOpenHelper {
    //Variable pour la table UTILISATEUR
    public static final String TABLE_COMMENTS = "utilisateur";
    public static final String COLUMN_ID = "id";
    //public static final String COLUMN_COMMENT = "comment";
    //Variable pour la table ALARME
    public static final String TABLE_COMMENTS2 = "alarme";
    public static final String COLUMN_ID2 = "id";
    public static final String COLUMN_COMMENT1 = "horaire";
    public static final String COLUMN_COMMENT2 = "jour";
    public static final String COLUMN_COMMENT3 = "son";
    //Variable pour la table PLAYLIST
    public static final String TABLE_COMMENTS3 = "playlist";
    public static final String COLUMN_ID3 = "id";
    public static final String COLUMN_COMMENT4 = "auteur";
    public static final String COLUMN_COMMENT5 = "année";
    public static final String COLUMN_COMMENT6 = "titre";
    //Variable pour la table CITATION
    public static final String TABLE_COMMENTS4 = "citation";
    public static final String COLUMN_ID4 = "id";
    public static final String COLUMN_COMMENT7 = "auteur";
    public static final String COLUMN_COMMENT8 = "année";
    public static final String COLUMN_COMMENT9 = "texte";

    private static final String DATABASE_NAME = "Alarme.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    //TABLE UTILISATEUR
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "(" + COLUMN_ID
            + "integer primary key autoincrement);";

    //TABLE ALARME
    private static final String DATABASE_CREATE2 = "create table "
            + TABLE_COMMENTS2 + "(" + COLUMN_ID2
            + "integer primary key autoincrement, " + COLUMN_COMMENT1
            + "text not null, " + COLUMN_COMMENT2
            + "text not null, " + COLUMN_COMMENT3
            + "text not null);";

    //TABLE PLAYLIST
    private static final String DATABASE_CREATE3 = "create table "
            + TABLE_COMMENTS3 + "(" + COLUMN_ID3
            + "integer primary key autoincrement, " + COLUMN_COMMENT4
            + "text not null, " + COLUMN_COMMENT5
            + "text not null, " + COLUMN_COMMENT6
            + "text not null);";

    //TABLE CITATION
    private static final String DATABASE_CREATE4 = "create table "
            + TABLE_COMMENTS4 + "(" + COLUMN_ID4
            + "integer primary key autoincrement, " + COLUMN_COMMENT7
            + "text not null, " + COLUMN_COMMENT8
            + "text not null, " + COLUMN_COMMENT9
            + "text not null);";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
        database.execSQL(DATABASE_CREATE3);
        database.execSQL(DATABASE_CREATE4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS4);
        onCreate(db);
    }
}

//Code à rajouter
