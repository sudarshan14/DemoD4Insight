package sud.bhatt.d4insight.repository

import sud.bhatt.d4insight.constants.Constants
import sud.bhatt.d4insight.networking.RetrofitApi
import sud.bhatt.d4insight.view.weather.model.CityName

class DataSourceRepository(private val retrofitApi: RetrofitApi) {

    suspend fun getCityWeatherDetails(cityName: String?) =
        retrofitApi.getCityWeatherDetails(cityName, Constants.API_KEY)

    // if fetching city names from service
// suspend fun getCityName()= retrofitService.getCityNames()
// currently fetching locally hardcoded values
    fun getCityNames(): List<CityName> {
        return listOf(
            CityName("Delhi"),
            CityName("Mumbai"),
            CityName("Bengaluru"),
            CityName("Chennai")

        )

    }

}