package com.example.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import com.example.ft_hangouts.R.layout.activity_main
import com.example.ft_hangouts.database.DataBaseHelper
import com.example.ft_hangouts.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val id = intent?.extras?.getInt("contactId")
        //Toast.makeText(this, "contact.id in EditActivity create: $id", Toast.LENGTH_SHORT).show()

        if (id != null) {
            val contact = DataBaseHelper(this).getContact(id)
            binding.nameOfContact.hint = contact.name
            binding.phoneNumberOfContact.hint = contact.number
            binding.emailOfContact.hint = contact.email

            binding.editContactButton.setOnClickListener {
                val name = binding.nameOfContact.text.toString()
                val number = binding.phoneNumberOfContact.text.toString()
                val email = binding.emailOfContact.text.toString()
                val db = DataBaseHelper(this)

                if (name.isNotEmpty()) {
                    db.updateName(contact.id, name)
                }
                if (number.isNotEmpty()) {
                    db.updateNumber(contact.id, number)
                }
                if (email.isNotEmpty()) {
                    db.updateEmail(contact.id, email)
                }

                Toast.makeText(this, getString(R.string.updated_contact), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra("contactId", contact.id)
                startActivity(intent)
            }

            binding.deleteContactButton.setOnClickListener {
                val db = DataBaseHelper(this)
                db.deleteContact(contact.id)
                Toast.makeText(this, getString(R.string.deleted_contact), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}