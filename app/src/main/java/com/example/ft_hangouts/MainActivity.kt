package com.example.ft_hangouts

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ft_hangouts.adapter.ContactAdapter
import com.example.ft_hangouts.database.ContactsDatabaseHelper
import com.example.ft_hangouts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
        requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        requestPermissionLauncher.launch(Manifest.permission.READ_SMS)
        requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false);

        val contacts = ContactsDatabaseHelper(this).getContacts()
        val recyclerView = binding.contactsRecyclerView
        recyclerView.adapter = ContactAdapter(this, contacts)
        recyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        val contacts = ContactsDatabaseHelper(this).getContacts()
        val recyclerView = binding.contactsRecyclerView
        recyclerView.adapter = ContactAdapter(this, contacts)
        recyclerView.setHasFixedSize(true)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.add_contact_button) {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}