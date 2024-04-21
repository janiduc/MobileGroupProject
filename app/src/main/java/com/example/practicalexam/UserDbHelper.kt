package com.example.practicalexam

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "AGE TEXT, " +
                "EMAIL TEXT)")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    companion object {
        private const val DATABASE_NAME = "STUDENT_RECORD"
        private const val TABLE_NAME = "STUDENT_DATA"
        private const val COL_1 = "ID"
        private const val COL_2 = "NAME"
        private const val COL_3 = "AGE"
        private const val COL_4 = "EMAIL"
    }

    fun insertData1(indexNo: String, name: String, age: String, email: String){
        val values = ContentValues()
        values.put("ID", indexNo)
        values.put("NAME", name)
        values.put("AGE", age)
        values.put("EMAIL", email)
        val db = this.writableDatabase
        db.insert(UserDbHelper.TABLE_NAME, null, values)
    }

    fun insertData(name: String, age: String, email: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_2, name)
            put(COL_3, age)
            put(COL_4, email)
        }
        val varResult = db.insert(TABLE_NAME, null, values)
        //returns the row ID of the newly inserted row if the insertion was successful
        db.close()
        return varResult != -1L
        //the expression insertedRowId != -1L effectively checks whether the insertion
        // operation was successful (true) or not (false).
    }


    fun getData(id: String): Cursor? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_1='$id'"
        return db.rawQuery(query, null)
    }

    fun updateData(id: String, name: String, age: String, email: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_1, id)
            put(COL_2, name)
            put(COL_3, age)
            put(COL_4, email)
        }
        db.update(TABLE_NAME, contentValues, "$COL_1=?", arrayOf(id))
        db.close()
        return true
    }


    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_1=?", arrayOf(id))
        db.close()
        return result
    }

    fun getAllData(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteAllData(): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, null, null)
        db.close()
        return result
    }

}