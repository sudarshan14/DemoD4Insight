package sud.bhatt.d4insight.view.blur

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import sud.bhatt.d4insight.R
import sud.bhatt.d4insight.constants.Constants.Companion.KEY_IMAGE_URI
import sud.bhatt.d4insight.databinding.BlurFragmentBinding

class BlurFragment : Fragment() {

    companion object {
        fun newInstance() = BlurFragment()
    }

    private lateinit var binding: BlurFragmentBinding

    //private val binding get() = _binding
    private lateinit var viewModel: BlurViewModel
    private lateinit var workManager: WorkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BlurFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val instance = requireContext().applicationContext
        workManager = WorkManager.getInstance(instance)

        viewModel =
            ViewModelProvider(
                this,
                BlurViewModelFactory(workManager, requireContext().applicationContext)
            )[BlurViewModel::class.java]


        binding.goButton.setOnClickListener {
            viewModel.applyBlur(blurLevel)
        }

        binding.cancelButton.setOnClickListener {
            viewModel.cancelWork()
        }

        binding.seeFileButton.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->

                binding.imageView.setImageURI(currentUri)
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                actionView.resolveActivity(requireActivity().packageManager)?.run {
                    startActivity(actionView)
                }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty())
                return@observe

            val workInfo = it[it.size - 1]
            // val state = it[it.size - 1].state
            if (workInfo.state.isFinished) {
                showWorkFinished()

                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                // If there is an output file show "See File" button
                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.seeFileButton.visibility = View.VISIBLE
                }

            } else
                showWorkInProgress()

        }

        return view
    }


    /**
     * Shows and hides views for when the Fragment is processing an image
     */
    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
            goButton.visibility = View.GONE
            seeFileButton.visibility = View.GONE
        }
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = View.GONE
            cancelButton.visibility = View.GONE
            goButton.visibility = View.VISIBLE
        }
    }

    private val blurLevel: Int
        get() =
            when (binding.radioBlurGroup.checkedRadioButtonId) {
                R.id.radio_blur_lv_1 -> 1
                R.id.radio_blur_lv_2 -> 2
                R.id.radio_blur_lv_3 -> 3
                else -> 1
            }

}