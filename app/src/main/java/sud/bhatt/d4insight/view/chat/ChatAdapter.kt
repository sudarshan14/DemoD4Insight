package sud.bhatt.d4insight.view.chat;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sud.bhatt.d4insight.constants.Constants
import sud.bhatt.d4insight.databinding.FragmentChatItemContainerReceivedMessageBinding
import sud.bhatt.d4insight.databinding.FragmentChatItemContainerSentMessageBinding
import sud.bhatt.d4insight.view.chat.model.ChatMessage

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var chatMessage: List<ChatMessage> = listOf(
        ChatMessage("S", "R", "Hello from Sender", "10.30 pm"),
        ChatMessage("S", "R", "How are you receiver?", "10.30 pm"),
        ChatMessage("R", "S", "Hello from Receiver", "10.30 pm"),
        ChatMessage("R", "S", "I am fine.", "10.30 pm"),
        ChatMessage("R", "S", "How Are you??", "10.30 pm"),
        ChatMessage("S", "R", "I am fine too. ", "10.30 pm")
    )
    // we will get this data from repository but for demo purpose creating here



    class SentMessageViewHolder(private val binding: FragmentChatItemContainerSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(chatMessage: ChatMessage) {

            binding.sentMessage.text = chatMessage.message
            binding.sentMessageDateTime.text = chatMessage.messageTime
        }

    }

    class ReceivedMessageViewHolder(private val binding: FragmentChatItemContainerReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(chatMessage: ChatMessage) {
            binding.receivedMessage.text = chatMessage.message
            binding.receivedMessageDateTime.text = chatMessage.messageTime
        }

    }

    override fun getItemViewType(position: Int): Int {
// based on logic of sender and receiver view type will be changed.

        return when (chatMessage[position].senderId) {
            "S" -> Constants.VIEW_TYPE_SENT
            else -> Constants.VIEW_TYPE_RECEIVED
        }

//        return when (position % 2) {
//            0 -> Constants.VIEW_TYPE_SENT
//            else -> Constants.VIEW_TYPE_RECEIVED
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == Constants.VIEW_TYPE_SENT) {
            val binding =
                FragmentChatItemContainerSentMessageBinding.inflate(inflater, parent, false)
            SentMessageViewHolder(binding)
        } else {
            val binding =
                FragmentChatItemContainerReceivedMessageBinding.inflate(inflater, parent, false)
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return chatMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Constants.VIEW_TYPE_SENT) {
            (holder as SentMessageViewHolder).setData(chatMessage[position])

        } else {
            (holder as ReceivedMessageViewHolder).setData(chatMessage[position])
        }


    }

}
