package sud.bhatt.d4insight.view.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import sud.bhatt.d4insight.logger.UNI_TAG
import sud.bhatt.d4insight.logger.debugLogger
import sud.bhatt.d4insight.logger.exceptionLogger
import sud.bhatt.d4insight.repository.DataSourceRepository
import sud.bhatt.d4insight.view.weather.model.CityName
import sud.bhatt.d4insight.view.weather.model.CityWeatherDetails

class WeatherViewModel(private val repository: DataSourceRepository) : ViewModel() {

    //    private val TAG = WeatherViewModel::class.qualifiedName
    val errorMessage = MutableLiveData<String>()
    val weatherDetails = MutableLiveData<CityWeatherDetails>()
    val cityName = MutableLiveData<List<CityName>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.message}")
    }
    val loading = MutableLiveData<Boolean>()


    fun getCityNames() {

        //we can get city names from db or network call but for now referencing hardcoaded array
        try {
            job = CoroutineScope(Dispatchers.IO).launch {
                loading.postValue(true)
                val op = repository.getCityNames()
                withContext(Dispatchers.Main) {
                    if (op.isNotEmpty()) {
                        cityName.postValue(op)
                        loading.postValue(false)
                    }
                }

            }
        } catch (e: Exception) {

        }
    }

    fun getWeatherDetails(cityName:String?) {
        try {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                loading.postValue(true)
                val response = repository.getCityWeatherDetails(cityName)
//                debugLogger(UNI_TAG, "response" + response.body());
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        debugLogger(UNI_TAG, "response is successful")
                        weatherDetails.postValue(response.body())
                        loading.postValue(false)
                    } else {
                        debugLogger(UNI_TAG, "response not successful")
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        } catch (e: Exception) {
            exceptionLogger(UNI_TAG, "error while fetching data from api", e)
        }
    }


    private fun onError(message: String) {
        debugLogger(UNI_TAG, "hello here $message")
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}