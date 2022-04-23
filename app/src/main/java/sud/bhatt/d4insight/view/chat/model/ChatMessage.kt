package sud.bhatt.d4insight.view.chat.model

data class ChatMessage(
    val senderId: String,
    val receiverId: String,
    val message: String,
    val messageTime: String
) {
}