package sud.bhatt.d4insight.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sud.bhatt.d4insight.view.weather.model.CityWeatherDetails

interface RetrofitApis {

    @GET("data/2.5/weather")
    suspend fun getCityWeatherDetails(
        @Query("q") city: String?,
        @Query("appid") appid: String
    ): Response<CityWeatherDetails>
}