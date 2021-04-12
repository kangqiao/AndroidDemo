package com.zp.androidx.demo.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri


/**
 * Created by zhaopan on 4/12/21
 */
class CustomContentProvider: ContentProvider() {
    companion object {
        //这里的AUTHORITY就是我们在AndroidManifest.xml中配置的authorities
        const val AUTHORITY = "com.zp.androidx.demo.customContentProvider"
        const val PERSON = 1
        const val PERSON_NUMBER = 2
        const val PERSON_TEXT = 3
        val matcher by lazy { UriMatcher(UriMatcher.NO_MATCH).apply {
            //添加我们需要匹配的uri
            // content://programandroid/person
            addURI(AUTHORITY, "person", PERSON);
            // # 代表任意数字content://programandroid/person/4
            addURI(AUTHORITY, "person/#", PERSON_NUMBER);
            // * 代表任意文本 content://programandroid/person/filter/ssstring
            addURI(AUTHORITY, "person/filter/*", PERSON_TEXT);
        } } //匹配不成功返回NO_MATCH(-1)
        val NOTIFY_URI = Uri.parse("content://${AUTHORITY}/student")
    }

    private lateinit var db : SQLiteDatabase

    override fun onCreate(): Boolean {
        val helper = DBHelper(context!!)
        db = helper.writableDatabase
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // 过滤URI
        val match: Int = matcher.match(uri)
        when (match) {
            PERSON ->            // content://autoname/person
                return db.query(
                    DBHelper.TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder
                )
            PERSON_NUMBER -> {
            }
            PERSON_TEXT -> {
            }
            else -> {
            }
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // 过滤URI
        val match = matcher.match(uri)
        when (match) {
            PERSON -> {
                // content://autoname/person
                val id = db.insert(DBHelper.TABLE_NAME, null, values)
                // 将原有的uri跟id进行拼接从而获取新的uri
                return ContentUris.withAppendedId(uri, id)
            }
            PERSON_NUMBER -> {
            }
            PERSON_TEXT -> {
            }
            else -> {
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}