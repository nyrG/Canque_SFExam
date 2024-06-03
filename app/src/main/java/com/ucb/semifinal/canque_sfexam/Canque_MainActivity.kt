package com.ucb.semifinal.canque_sfexam

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ucb.semifinal.canque_sfexam.adapter.MessageAdapter
import com.ucb.semifinal.canque_sfexam.databinding.ActivityCanqueMainBinding
import com.ucb.semifinal.canque_sfexam.models.Message
import java.util.UUID

class Canque_MainActivity : AppCompatActivity() {
    private lateinit var messageList: MutableList<Message>
    private lateinit var binding: ActivityCanqueMainBinding
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCanqueMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        messageList = mutableListOf()
        messageAdapter = MessageAdapter(this, messageList)

        messageList.add(Message("1", 1, "Hello", System.currentTimeMillis(), 1))
        messageList.add(Message("2", 2, "Hi!", System.currentTimeMillis(), 1))
        messageList.add(Message("3", 1, "Nice to meet you!", System.currentTimeMillis(), 1))

        binding.button1.setOnClickListener {
            showCustomDialog(1, messageList)
        }

        binding.button2.setOnClickListener {
            showCustomDialog(2, messageList)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = messageAdapter
    }

    private fun showCustomDialog(sender: Int, messages: MutableList<Message>) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.canque_custom_dialog, null)

        val messageET = view.findViewById<EditText>(R.id.messageEditText)

        val alertDialogBuilder = AlertDialog.Builder(this, R.style.RoundedCornersAlertDialog)
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // Retrieve the text inputted by the user
            val messageText = messageET.text.toString().trim()
            val messageId = UUID.randomUUID().toString()
            //tasks = sharedPref.loadTaskList().toMutableList()
            if(!(messageText.isNullOrEmpty())) {
                val message = Message( messageId, sender, messageText, System.currentTimeMillis(), 1)
                messages.add(message)
                messageAdapter.notifyItemInserted(messageList.size - 1)
                binding.recyclerView.scrollToPosition(messageList.size - 1)
                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "You didn't input a message!", Toast.LENGTH_SHORT).show()
            }
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setOnShowListener {
            alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                ?.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                ?.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        }
        alertDialog.show()
    }
}