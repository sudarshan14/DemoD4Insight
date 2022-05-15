package sud.bhatt.d4insight.locolstorage

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WeatherInfoDAO {

    @Insert
    suspend fun insertWeatherInfo(weatherInfo: WeatherInfo)

    @Update
    suspend fun updateWeatherInfo(weatherInfo: WeatherInfo)

    @Delete
    suspend fun deleteWeatherInfo(weatherInfo: WeatherInfo)

    //in room when return type is LiveData, it automatically runs the function in background thread
    //So don't make the function suspend function
    @Query("SELECT * FROM weatherinfo")
    fun getWeatherInfo(): LiveData<List<WeatherInfo>>
}