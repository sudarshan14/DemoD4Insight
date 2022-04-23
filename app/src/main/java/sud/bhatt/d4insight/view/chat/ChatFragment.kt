package sud.bhatt.d4insight.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sud.bhatt.d4insight.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val adapter = ChatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.chatRecyclerView.adapter = adapter
        binding.name.text = "Dummy Name"
        binding.sendChat.setOnClickListener {
            // update data source using  live data
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.chatRecyclerView.visibility = View.VISIBLE
        }

        return view
    }


    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

}