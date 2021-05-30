package com.example.ft_hangouts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ft_hangouts.database.DataBaseHelper
import com.example.ft_hangouts.databinding.ActivityContactBinding


class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityContactBinding.inflate(layoutInflater)
        val id = intent?.extras?.getInt("id")
        val contacts = DataBaseHelper(this).getContacts()

        if (id != null) {
            binding.contactName.text = contacts[id].name
            binding.contactEmailAddress.text = contacts[id].email
            binding.contactPhoneNumber.text = contacts[id].number
        }

        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.contactPhoneNumber.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:" + binding.contactPhoneNumber.text.toString())
            )
            startActivity(intent)
        }

        binding.contactEmailAddress.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + binding.contactEmailAddress.text.toString())
            )
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.edit_contact_button) {

            // CHANGE TO EDIT PAGE
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}