package tomerbu.edu.sqlitefirebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tomerbuzaglo on 01/08/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
public class TodosDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "TodosDB";

    public TodosDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String c_TODOS = "CREATE TABLE Todos(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Title TEXT NOT NULL, Description TEXT NOT NULL)";
        sqLiteDatabase.execSQL(c_TODOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Todos");
        onCreate(sqLiteDatabase);
    }
}
