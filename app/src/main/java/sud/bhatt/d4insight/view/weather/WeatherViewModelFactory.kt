package sud.bhatt.d4insight.view.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sud.bhatt.d4insight.repository.DataSourceRepository

class WeatherViewModelFactory constructor(private val repository: DataSourceRepository) :
    ViewModelProvider.Factory {

    // we can use hilt Multibinding viewmodel if required

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            WeatherViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

//    fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
//        val viewModel = this
//        return object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
//        }
//    }