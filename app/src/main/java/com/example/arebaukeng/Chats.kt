package com.example.arebaukeng

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Chats: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var gestureDetector: GestureDetector
    lateinit var model:Model

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        model =viewModel.model

        recyclerView = findViewById(R.id.recyclerVie)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        chatAdapter = ChatAdapter(model.messageTable)
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        for(list in model.messageTable){
          if(list.username==model.currentUser){  list.isSent=true}
        }

        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                
                model.messageTable.add(Message(model.currentUser,messageText, dateFormat.format(calendar.time),true))
                model.myRefMessage.setValue(model.messageTable)
                messageInput.text.clear()
                chatAdapter.notifyItemInserted(model.messageTable.size - 1)
                recyclerView.scrollToPosition(model.messageTable.size - 1)

                // Simulate receiving a message (for demo purposes)
                receiveMessage(model.currentUser,"Echo: $messageText")
            }
        }
        gestureDetector = GestureDetector(this, SwipeGestureListener {
            // Handle the swipe up action
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        })

        val swipeView: View = findViewById(R.id.swipe_view)
        swipeView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    private fun receiveMessage(user:String,content: String) {
        model.messageTable.add(Message(user,content,  Date().time.toString(),false))
        chatAdapter.notifyItemInserted(model.messageTable.size - 1)
        recyclerView.scrollToPosition(model.messageTable.size - 1)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
class SwipeGestureListener(private val onSwipeUp: () -> Unit) : GestureDetector.OnGestureListener {
    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100




    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true

    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true

    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        val diffY = e2?.y?.minus(e1!!.y) ?: 0.0f
        val diffX = e2?.x?.minus(e1!!.x) ?: 0.0f
        if (Math.abs(diffY) > Math.abs(diffX)) {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY < 0) {
                    onSwipeUp()
                }
                return true
            }
        }
        return false
    }
}

