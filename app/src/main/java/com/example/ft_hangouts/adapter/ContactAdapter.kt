package com.example.ft_hangouts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.R
import com.example.ft_hangouts.R.layout.contacts_list_item
import com.example.ft_hangouts.model.ContactModel

class ContactAdapter(
    private val context: Context,
    private val list: List<ContactModel>
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.contact_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(contacts_list_item, parent, false)
        return ContactViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = list[position]
        holder.textView.text = contact.name

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "CARD", Toast.LENGTH_SHORT).show()
        }

        holder.textView.setOnClickListener {
            Toast.makeText(context, "TEXT", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}