package sud.bhatt.d4insight.view.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import sud.bhatt.d4insight.databinding.FragmentWeatherBinding
import sud.bhatt.d4insight.locolstorage.WeatherDatabase
import sud.bhatt.d4insight.networking.RetrofitService
import sud.bhatt.d4insight.repository.DataSourceRepository
import sud.bhatt.d4insight.utils.UNI_TAG
import sud.bhatt.d4insight.utils.debugLogger

class WeatherFragment : Fragment(), CellClickListener {

    private lateinit var _binding: FragmentWeatherBinding
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel
    private val adapter = WeatherAdapter(this)
    lateinit var database: WeatherDatabase
    lateinit var database2: WeatherDatabase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugLogger(UNI_TAG, "onViewCreated")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        debugLogger(UNI_TAG, "onCreateView")
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recyclerview.adapter = adapter
        database = WeatherDatabase.getDatabase(requireContext())
        database2 = WeatherDatabase.getDatabase(requireContext())
        val retrofitService = RetrofitService.retrofitService
        val retrofitService2 = RetrofitService.retrofitService

        val mainRepository = DataSourceRepository(retrofitService, database)

        viewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(mainRepository)
        )[WeatherViewModel::class.java]

        observerViewModel()

        return view
    }

    private fun observerViewModel() {
        viewModel.getCityNames()

        viewModel.cityName.observe(viewLifecycleOwner) {
            adapter.updateCities(it)
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
        }

//        viewModel.getWeatherDetails()

        viewModel.weatherDetails.observe(viewLifecycleOwner) {
            adapter.showCityTemperatureDetails(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            binding.shimmerFrameLayout.visibility = View.GONE
//            binding.recyclerview.visibility = View.VISIBLE
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

    }

    override fun onCellClickListener(data: String?) {
        viewModel.getWeatherDetails(data)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        debugLogger(UNI_TAG, "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        debugLogger(UNI_TAG, "onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        debugLogger(UNI_TAG, "onDestroyView")
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
        debugLogger(UNI_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerFrameLayout.stopShimmerAnimation()
        debugLogger(UNI_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        debugLogger(UNI_TAG, "onStop")
    }

    override fun onStart() {
        super.onStart()
        debugLogger(UNI_TAG, "onStart")
    }

}