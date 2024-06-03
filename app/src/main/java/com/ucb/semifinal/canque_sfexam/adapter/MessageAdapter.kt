package com.ucb.semifinal.canque_sfexam.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ucb.semifinal.canque_sfexam.R
import com.ucb.semifinal.canque_sfexam.databinding.CanqueMessageItemBinding
import com.ucb.semifinal.canque_sfexam.models.Message


class MessageAdapter(private val context: Context, private val messageList: MutableList<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    inner class MessageViewHolder(private val binding: CanqueMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.messageText.text = message.text
            // Differentiate between Person 1's and Person 2's messages
            if (message.senderId == 1) {
                // Person 1's message styling
                binding.messageText.setBackgroundResource(R.drawable.left_message_bg)
                binding.messageLayout.gravity = Gravity.START or Gravity.CENTER_VERTICAL
            } else if (message.senderId == 2) {
                // Person 2's message styling
                binding.messageText.setBackgroundResource(R.drawable.right_message_bg)
                binding.messageLayout.gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }

            binding.messageText.setOnLongClickListener {
                if (message.status != 0) {
                    val alertDialogBuilder = AlertDialog.Builder(context, R.style.RoundedCornersAlertDialog)
                    alertDialogBuilder.setMessage("Select Action")
                    alertDialogBuilder.setPositiveButton("Update") { dialog, _ ->
                        val inflater = LayoutInflater.from(context)
                        val view = inflater.inflate(R.layout.canque_custom_dialog, null)
                        val messageET = view.findViewById<EditText>(R.id.messageEditText)

                        val alertDialogBuilder = AlertDialog.Builder(context, R.style.RoundedCornersAlertDialog)
                        alertDialogBuilder.setView(view)
                        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                            // Retrieve the text inputted by the user
                            val messageText = messageET.text.toString().trim()
                            //tasks = sharedPref.loadTaskList().toMutableList()
                            if(!(messageText.isNullOrEmpty())) {
                                message.text = messageText
                                notifyDataSetChanged()
                                Toast.makeText(context, "Message updated!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "You didn't input a message!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.setOnShowListener {
                            alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                                ?.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                            alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                                ?.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                        }
                        alertDialog.show()
                    }

                    alertDialogBuilder.setNegativeButton("Delete") { dialog, _ ->
                        message.status = 0
                        binding.messageText.background = null
                        binding.messageText.setTextColor(ContextCompat.getColor(context, R.color.black))
                        binding.messageText.text = "Message unsent."
                    }

                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.setOnShowListener {
                        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                            ?.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                            ?.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                    }
                    alertDialog.show()
                }

                // Return true to indicate that the long click event is consumed
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = CanqueMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}