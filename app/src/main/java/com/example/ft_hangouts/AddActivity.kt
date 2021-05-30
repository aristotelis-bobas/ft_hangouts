package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ft_hangouts.database.DataBaseHelper
import com.example.ft_hangouts.databinding.ActivityAddBinding
import com.example.ft_hangouts.model.ContactModel

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.createContactButton.setOnClickListener {

            val name = binding.nameOfContact.text.toString()
            val number = binding.phoneNumberOfContact.text.toString()
            val email = binding.emailOfContact.text.toString()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val db = DataBaseHelper(this)

                if (!db.addContact(name, number, email)) {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, getString(R.string.name_and_phone_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}