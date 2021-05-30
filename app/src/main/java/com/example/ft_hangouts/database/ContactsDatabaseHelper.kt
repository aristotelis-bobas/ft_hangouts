package com.example.ft_hangouts.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ft_hangouts.model.ContactModel

class ContactsDatabaseHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "contacts.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement =
            """CREATE TABLE $CONTACTS_TABLE (
                $ID INTEGER PRIMARY KEY,
                $NAME TEXT, 
                $NUMBER TEXT, 
                $EMAIL TEXT
            )"""

        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addContact(name: String, number: String, email: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(NAME, name)
        cv.put(NUMBER, number)
        cv.put(EMAIL, email)

        return db.insert(CONTACTS_TABLE, null, cv) >= 0
    }

    fun getContacts(): List<ContactModel> {
        val list = mutableListOf<ContactModel>()
        val query = "SELECT * FROM $CONTACTS_TABLE"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val number = cursor.getString(2)
            val email = cursor.getString(3)
            list.add(ContactModel(id, name, number, email))
        }

        cursor.close()
        db.close()
        return list
    }

    fun getContact(contactId: Int): ContactModel {
        val query = """
            SELECT * FROM $CONTACTS_TABLE
            WHERE $ID = $contactId;
        """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        cursor.moveToNext()
        val id = cursor.getInt(0)
        val name = cursor.getString(1)
        val number = cursor.getString(2)
        val email = cursor.getString(3)

        return ContactModel(id, name, number, email)
    }

    fun updateName(id: Int, name: String) {
        val query = """
            UPDATE $CONTACTS_TABLE
            SET $NAME = '$name'
            WHERE $ID = $id;
        """
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
    }

    fun updateEmail(id: Int, email: String) {
        val query = """
            UPDATE $CONTACTS_TABLE
            SET $EMAIL = '$email'
            WHERE $ID = $id;
        """
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
    }

    fun updateNumber(id: Int, number: String) {
        val query = """
            UPDATE $CONTACTS_TABLE
            SET $NUMBER = '$number'
            WHERE $ID = $id;
        """
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
    }

    fun deleteContact(id: Int) {
        val query = """
            DELETE FROM $CONTACTS_TABLE
            WHERE $ID = $id
        """
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
    }

    companion object {
        const val ID = "ID"
        const val NAME = "NAME"
        const val NUMBER = "NUMBER"
        const val EMAIL = "EMAIL"
        const val CONTACTS_TABLE = "CONTACTS_TABLE"
    }
}