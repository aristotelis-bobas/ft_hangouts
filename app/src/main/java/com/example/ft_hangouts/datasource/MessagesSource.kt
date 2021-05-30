package com.example.ft_hangouts.datasource

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Message
import android.widget.Toast
import com.example.ft_hangouts.model.ContactModel
import com.example.ft_hangouts.model.MessageModel

class MessagesSource(
    val contact: ContactModel
) {

    fun getMessages(contentResolver: ContentResolver): List<MessageModel> {
        val list = mutableListOf<MessageModel>()
        val cursor =
            contentResolver.query(
                Uri.parse("content://sms/"),
                null,
                null,
                null,
                null
            )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val address = cursor.getString(cursor.getColumnIndex("address"))
                    if (address != contact.number)
                        continue
                    val id = cursor.getString(cursor.getColumnIndex("_id"))
                    val message = cursor.getString(cursor.getColumnIndex("body"))
                    val date = cursor.getString(cursor.getColumnIndex("date"))
                    val received = cursor.getString(cursor.getColumnIndex("type")).toInt() == 1
                    list.add(MessageModel(id, address, message, date, received))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return list
    }
}