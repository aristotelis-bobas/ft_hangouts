package com.example.ft_hangouts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ft_hangouts.R
import com.example.ft_hangouts.R.layout.messages_list_item
import com.example.ft_hangouts.model.MessageModel

class MessageAdapter(
    private val context: Context,
    private val list: List<MessageModel>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.message_card_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(messages_list_item, parent, false)
        return MessageViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = list[position]
        if (!message.received)
            holder.textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        holder.textView.text = message.message
    }

    override fun getItemCount(): Int {
        return list.size
    }

}