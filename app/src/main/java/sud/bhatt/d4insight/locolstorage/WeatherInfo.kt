package sud.bhatt.d4insight.locolstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "WeatherInfo")
data class WeatherInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val info_date: Date,
    val info_new_date: Date,
    val info_new_date_2: Date
) {

}