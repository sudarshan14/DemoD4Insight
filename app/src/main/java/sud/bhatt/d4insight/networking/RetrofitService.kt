package sud.bhatt.d4insight.networking

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import sud.bhatt.d4insight.constants.Constants
import sud.bhatt.d4insight.logger.UNI_TAG
import sud.bhatt.d4insight.logger.debugLogger
import sud.bhatt.d4insight.logger.exceptionLogger
import sud.bhatt.d4insight.view.weather.model.CityName
import sud.bhatt.d4insight.view.weather.model.CityWeatherDetails
import java.util.concurrent.TimeUnit

private const val TIME_OUT: Long = 120

private val okHttpClient = OkHttpClient.Builder()
    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    .addInterceptor { chain ->
        val resp = chain.proceed(chain.request())
        // Deal with the response code
        if (resp.code == 200) {
            try {
                val myJson =
                    resp.peekBody(2048).string() // peekBody() will not close the response
                debugLogger(UNI_TAG, myJson)
            } catch (e: Exception) {
                exceptionLogger(UNI_TAG, " exception parsing data", e)
            }
        } else {
            println(resp)
        }
        resp
    }
    .build()


private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()


interface RetrofitApi {

    @GET("data/2.5/weather")
    suspend fun getCityWeatherDetails(
        @Query("q") city: String?,
        @Query("appid") appid: String
    ): Response<CityWeatherDetails>

    suspend fun getCityNames(): Response<CityName>

}

object RetrofitService {
    val retrofitService: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}