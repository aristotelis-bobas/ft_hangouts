package com.example.ft_hangouts

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ft_hangouts.adapter.ContactAdapter
import com.example.ft_hangouts.adapter.MessageAdapter
import com.example.ft_hangouts.database.ContactsDatabaseHelper
import com.example.ft_hangouts.databinding.ActivityContactBinding
import com.example.ft_hangouts.datasource.MessagesSource
import com.example.ft_hangouts.model.ContactModel


class ContactActivity : AppCompatActivity() {

    private lateinit var contact: ContactModel
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        val id = intent?.extras?.getInt("contactId")
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (id != null) {
            contact = ContactsDatabaseHelper(this).getContact(id)
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
            val messages = MessagesSource(contact).getMessages(contentResolver)
            val recyclerView = binding.messagesRecyclerView
            recyclerView.adapter = MessageAdapter(this, messages.reversed())
            recyclerView.setHasFixedSize(true)

            binding.sendMessageButton.setOnClickListener {
                val message = binding.sendMessageText.text.toString()
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra("contactId", ContactsDatabaseHelper(this).getContact(id).id)
                val sms: SmsManager = SmsManager.getDefault()
                sms.sendTextMessage(contact.number, null, message, null, null)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.edit_contact_button) {
            val intent = Intent(this, EditActivity::class.java)
            //Toast.makeText(this, "contact.id in ContactActivity intent: $contact.id", Toast.LENGTH_SHORT).show()
            intent.putExtra("contactId", contact.id)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}