package com.example.ft_hangouts.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ft_hangouts.model.ContactModel

class DataBaseHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "contacts.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement =
            "CREATE TABLE $CONTACTS_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT, $NUMBER TEXT, $EMAIL TEXT, $PICTURE TEXT)"

        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addContact(contact: ContactModel): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(NAME, contact.name)
        cv.put(NUMBER, contact.number)
        cv.put(EMAIL, contact.email)
        cv.put(PICTURE, contact.picture)

        return db.insert(CONTACTS_TABLE, null, cv) >= 0
    }

    companion object {
        const val NAME = "NAME"
        const val NUMBER = "NUMBER"
        const val EMAIL = "EMAIL"
        const val PICTURE = "PICTURE"
        const val CONTACTS_TABLE = "CONTACTS_TABLE"
    }
}