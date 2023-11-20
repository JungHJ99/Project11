import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedultalk.databinding.FragmentChatBinding
import com.example.schedultalk.ui.chats.ChatAdapter
import com.example.schedultalk.ui.chats.ChatMessage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatFragment : AppCompatActivity() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var btn_exit: ImageButton
    private lateinit var btn_submit: Button
    private lateinit var txt_title: TextView
    private lateinit var edt_message: EditText
    private lateinit var recycler_talks: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messages: MutableList<ChatMessage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeProperty()
        initializeView()
        initializeListener()
        setupChatRooms()
    }

    private fun initializeProperty() {
        messages = mutableListOf()
        chatAdapter = ChatAdapter(messages)
    }

    private fun initializeView() {
        edt_message = binding.edtMessage
        recycler_talks = binding.recyclerMessages
        btn_submit = binding.btnSubmit
        txt_title = binding.txtTitle
        txt_title.text = ""
        recycler_talks.layoutManager = LinearLayoutManager(this)
        recycler_talks.adapter = chatAdapter
    }

    private fun initializeListener() {
        btn_exit.setOnClickListener {
        }
        btn_submit.setOnClickListener {
            val messageText = edt_message.text.toString()
            putDummyMessage(messageText)
        }
    }

    private fun setupChatRooms() {
    }

    private fun putDummyMessage(messageText: String) {
        val dummyDateTime = "10시46분"
        val message = ChatMessage("1", dummyDateTime, messageText)
        messages.add(message)
        chatAdapter.notifyItemInserted(messages.size - 1)
        recycler_talks.smoothScrollToPosition(messages.size - 1)
        edt_message.text.clear()
    }
}
