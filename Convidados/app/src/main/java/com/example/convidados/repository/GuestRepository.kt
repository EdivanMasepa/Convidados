package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private  constructor(context: Context){

    private val guestDataBase = GuestDataBase(context)

    companion object{
        private  lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean{
        return try {
            val db = guestDataBase.writableDatabase
            val married = if(guest.married) 1 else 0
            val values = ContentValues()

            values.put(DataBaseConstants.GUEST.COLUMNS.MARRIED, married)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.AGE, guest.age)
            values.put(DataBaseConstants.GUEST.COLUMNS.GENDER, guest.gender)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)

            true
        } catch (e: Exception){
            false
        }
    }

    fun update(guest: GuestModel): Boolean{
        return try {
            val db = guestDataBase.writableDatabase
            val married = if(guest.married) 1 else 0
            val values = ContentValues()
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            values.put(DataBaseConstants.GUEST.COLUMNS.MARRIED, married)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.AGE, guest.age)
            values.put(DataBaseConstants.GUEST.COLUMNS.GENDER, guest.gender)
            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)

            true
        } catch (e: Exception){
            false
        }
    }

    fun delete(id: Int): Boolean{
        return try {
            val db = guestDataBase.writableDatabase
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())


            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception){
            false
        }
    }

    @SuppressLint("Recycle", "Range")
    fun get(id: Int): GuestModel?{
        var guest: GuestModel? = null

        try{
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.MARRIED,
                DataBaseConstants.GUEST.COLUMNS.AGE,
                DataBaseConstants.GUEST.COLUMNS.GENDER
            )
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args, null, null, null)
            //val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence == 1 ", null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val married = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.MARRIED))
                    val age = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.AGE))
                    val gender = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GENDER))

                    guest = GuestModel(id, name, married == 1, age, gender)
                }
            }
            cursor.close()
        } catch (e: Exception){
            return  guest
        }
        return guest
    }

    @SuppressLint("Recycle", "Range")
    fun getAll(): List<GuestModel>{
        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase
            val cursor = db.rawQuery("SELECT id, name, married, age, gender FROM Guest", null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val married = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.MARRIED))
                    val age = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.AGE))
                    val gender = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GENDER))
                    val guest = GuestModel(id, name, married == 1, age, gender)

                    list.add(guest)
                }
            }
            cursor.close()
        } catch (e: Exception){
            return  list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getMarried(): List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase
            val cursor = db.rawQuery("SELECT id, name, married, age, gender FROM Guest WHERE married == 1", null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val married = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.MARRIED))
                    val age = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.AGE))
                    val gender = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GENDER))
                    val guest = GuestModel(id, name, married == 1, age, gender)

                    list.add(guest)
                }
            }
            cursor.close()
        } catch (e: Exception){
            return  list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getSingle(): List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try{
            val db = guestDataBase.readableDatabase
            val cursor = db.rawQuery("SELECT id, name, married, age, gender FROM Guest WHERE married == 0", null)

            if(cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val married = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.MARRIED))
                    val age = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.AGE))
                    val gender = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.GENDER))

                    val guest = GuestModel(id, name, married == 0, age, gender)

                    list.add(guest)
                }
            }
            cursor.close()
        } catch (e: Exception){
            return  list
        }
        return list
    }
}