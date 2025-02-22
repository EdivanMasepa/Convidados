package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object{
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        /*db.execSQL("CREATE TABLE Guest(id integer primary key autoincrement, name text, presence integer);")*/

        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + "(" +
                    DataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, " +
                    DataBaseConstants.GUEST.COLUMNS.NAME + " text, " +
                    DataBaseConstants.GUEST.COLUMNS.MARRIED + " integer, " +
                    DataBaseConstants.GUEST.COLUMNS.AGE + " integer, "+
                    DataBaseConstants.GUEST.COLUMNS.GENDER + " text);"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion == 1){

        }else if(oldVersion == 2){

        }else if(oldVersion == 3){

        }else{

        }
    }
}