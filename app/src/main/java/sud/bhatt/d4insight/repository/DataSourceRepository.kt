package sud.bhatt.d4insight.repository

import retrofit2.Response
import sud.bhatt.d4insight.constants.Constants
import sud.bhatt.d4insight.locolstorage.WeatherDatabase
import sud.bhatt.d4insight.locolstorage.WeatherInfo
import sud.bhatt.d4insight.networking.RetrofitApi
import sud.bhatt.d4insight.utils.UNI_TAG
import sud.bhatt.d4insight.utils.debugLogger
import sud.bhatt.d4insight.view.weather.model.CityName
import sud.bhatt.d4insight.view.weather.model.CityWeatherDetails
import java.util.*

class DataSourceRepository(
    private val retrofitApi: RetrofitApi,
    private val database: WeatherDatabase
) {

    suspend fun getCityWeatherDetails(cityName: String?): Response<CityWeatherDetails> {
        val info = retrofitApi.getCityWeatherDetails(cityName, Constants.API_KEY)

        try {
            if (info.isSuccessful)
                insertIntoLocalDatabase(cityName, info)
        } catch (e: Exception) {
            debugLogger(UNI_TAG, e.toString())
        }
        return info

    }


    private suspend fun insertIntoLocalDatabase(
        cityName: String?,
        info: Response<CityWeatherDetails>
    ) {

        info.body()!!.main.apply {
            val weather =
                WeatherInfo(
                    0, cityName!!, temp, feels_like, temp_min, temp_max, pressure, humidity,
                    Date(),
                    Date(),
                    Date()
                )
            database.weatherInfo().insertWeatherInfo(weather)
        }
    }


// if fetching city names from service
// suspend fun getCityName()= retrofitService.getCityNames()
// currently fetching locally hardcoded values
fun getCityNames(): List<CityName> {
    return listOf(
        CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"),
        CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"),
        CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"), CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"), CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"),
        CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"),
        CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"),
        CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"), CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai"), CityName("Delhi"),
        CityName("Mumbai"),
        CityName("Bengaluru"),
        CityName("Chennai")

    )

}

}