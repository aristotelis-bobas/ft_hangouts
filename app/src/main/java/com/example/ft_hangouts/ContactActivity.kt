package com.example.ft_hangouts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ft_hangouts.database.DataBaseHelper
import com.example.ft_hangouts.databinding.ActivityContactBinding
import com.example.ft_hangouts.model.ContactModel


class ContactActivity : AppCompatActivity() {

    private lateinit var contact: ContactModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityContactBinding.inflate(layoutInflater)
        val id = intent?.extras?.getInt("contactId")
        //Toast.makeText(this, "contact.id in ContactActivity create: $id", Toast.LENGTH_SHORT).show()

        if (id != null) {
            contact = DataBaseHelper(this).getContact(id)
            binding.contactName.text = contact.name
            binding.contactEmailAddress.text = contact.email
            binding.contactPhoneNumber.text = contact.number

            binding.contactPhoneNumber.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:" + binding.contactPhoneNumber.text.toString())
                )
                startActivity(intent)
            }

            if (contact.email.isNotEmpty()) {
                binding.contactEmailAddress.setOnClickListener {
                    val intent = Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + binding.contactEmailAddress.text.toString())
                    )
                    startActivity(intent)
                }
            }
        }
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val contactId = contact.id

        if (id == R.id.edit_contact_button) {
            val intent = Intent(this, EditActivity::class.java)
            //Toast.makeText(this, "contact.id in ContactActivity intent: $contactId", Toast.LENGTH_SHORT).show()
            intent.putExtra("contactId", contactId)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}