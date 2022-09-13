package com.example.moneybook

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception


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

    fun modifyTransactionDate(id:Int, ei: String, value: Float, reason: String, date: String): Int {

        val db = this.writableDatabase

        val values = ContentValues()
        values.put(ID_COL, id)
        values.put(VALUE_COl, value)
        values.put(EI_COL, ei)
        values.put(REAS_COL, reason)
        values.put(DATE_COL, date)
        var idn = arrayOf(id.toString())

        val success = db.update(TABLE_NAME, values, "id = ?", idn)
        db.close()
        return success
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

    @SuppressLint("Range")
    fun getTransBetweenDate(datestart: String, datefin: String): ArrayList<Transaction>{
        val db = this.readableDatabase
        val cursor: Cursor?
        val trnsList: ArrayList<Transaction> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME WHERE $DATE_COL  BETWEEN $datestart AND $datefin"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception){
            db.execSQL(query)
            e.printStackTrace()
            return ArrayList()
        }
        var id: Int
        var value: Float
        var reason:String
        var ei:String
        var date:String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                value = cursor.getFloat(cursor.getColumnIndex("value"))
                ei = cursor.getString(cursor.getColumnIndex("ei"))
                reason = cursor.getString(cursor.getColumnIndex("reason"))
                date = cursor.getString(cursor.getColumnIndex("date"))

                val trns = Transaction(id, ei ,value, reason, date )
                trnsList.add(trns)

            } while (cursor.moveToNext())
        }
        return trnsList
    }

    @SuppressLint("Range")
    fun getTransByDateAndReas(datestart: String, datefin: String, reason: String): ArrayList<Transaction>{


        val db = this.readableDatabase
        val cursor: Cursor?
        val trnsList: ArrayList<Transaction> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME WHERE $DATE_COL BETWEEN $datestart AND $datefin AND $REAS_COL =  ?"

        try {
            cursor = db.rawQuery(query, arrayOf(reason))
        } catch (e: Exception){
            db.execSQL(query)
            e.printStackTrace()
            return ArrayList()
        }
        var id: Int
        var value: Float
        var reason:String
        var ei:String
        var date:String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                value = cursor.getFloat(cursor.getColumnIndex("value"))
                ei = cursor.getString(cursor.getColumnIndex("ei"))
                reason = cursor.getString(cursor.getColumnIndex("reason"))
                date = cursor.getString(cursor.getColumnIndex("date"))

                val trns = Transaction(id, ei ,value, reason, date )
                trnsList.add(trns)

            } while (cursor.moveToNext())
        }
        return trnsList
    }

    @SuppressLint("Range")
    fun getTransByReason (reason: String): ArrayList<Transaction>{
        val db = this.readableDatabase
        val trnsList: ArrayList<Transaction> = ArrayList()
        val cursor: Cursor?
        val query = "SELECT * FROM $TABLE_NAME WHERE reason = ?"

        try {
            cursor = db.rawQuery(query, arrayOf(reason))
        } catch (e: Exception){
            db.execSQL(query)
            e.printStackTrace()
            return ArrayList()
        }

        var id: Int
        var value: Float
        var reason:String
        var ei:String
        var date:String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                value = cursor.getFloat(cursor.getColumnIndex("value"))
                ei = cursor.getString(cursor.getColumnIndex("ei"))
                reason = cursor.getString(cursor.getColumnIndex("reason"))
                date = cursor.getString(cursor.getColumnIndex("date"))

                val trns = Transaction(id, ei ,value, reason, date )
                trnsList.add(trns)

            } while (cursor.moveToNext())
        }
        return trnsList
    }


    fun modifyTransaction(id:Int, ei: String, value: Float, reason: String): Int {

        val db = this.writableDatabase

        val values = ContentValues()
        values.put(ID_COL, id)
        values.put(VALUE_COl, value)
        values.put(EI_COL, ei)
        values.put(REAS_COL, reason)
        var idn = arrayOf(id.toString())

        val success = db.update(TABLE_NAME, values, "id = ?", idn)
        db.close()
        return success
    }

    fun deleteTransaction(id:Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_COL, id)

        val success = db.delete(TABLE_NAME,"id=$id", null)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getTransactions(): ArrayList<Transaction> {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val trnsList: ArrayList<Transaction> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME "
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }

        var id: Int
        var value: Float
        var reason:String
        var ei:String
        var date:String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                value = cursor.getFloat(cursor.getColumnIndex("value"))
                ei = cursor.getString(cursor.getColumnIndex("ei"))
                reason = cursor.getString(cursor.getColumnIndex("reason"))
                date = cursor.getString(cursor.getColumnIndex("date"))

                val trns = Transaction(id, ei ,value, reason, date )
                trnsList.add(trns)

            } while (cursor.moveToNext())
        }

        return trnsList
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