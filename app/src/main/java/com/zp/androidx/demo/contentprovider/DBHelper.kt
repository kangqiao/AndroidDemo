package com.zp.androidx.demo.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by zhaopan on 4/12/21
 */
class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "persons.db"
        const val DB_VERSION = 1
        const val TABLE_NAME = "table_person"
        const val ID = "_id"
        const val NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ${TABLE_NAME} (${ID} INTEGER PRIMARY KEY NOT NULL, ${NAME} CHAR(10))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}