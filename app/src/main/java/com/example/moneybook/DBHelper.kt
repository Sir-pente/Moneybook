package com.example.moneybook

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                VALUE_COl + " TEXT," +
                EI_COL + " TEXT," +
                REAS_COL + " TEXT," +
                DATE_COL + " TEXT " + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addItem(value: Float, ei: String, reason: String, date: String) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(VALUE_COl, value)
        values.put(EI_COL, ei)
        values.put(REAS_COL, reason)
        values.put(DATE_COL, date)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase


        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "DBMONEYBOOK"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "mb_table2"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for money value  column
        val VALUE_COl = "value"

        // below is the variable for reason column
        val REAS_COL = "reason"

        // below is the variable for date column
        val DATE_COL = "date"

        // below is the variable for expense/income column
        val EI_COL = "ei"
    }
}