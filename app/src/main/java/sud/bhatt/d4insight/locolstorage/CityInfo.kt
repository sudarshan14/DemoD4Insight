package sud.bhatt.d4insight.locolstorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class CityInfo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cityid") val cityId: Int,
    @ColumnInfo(name = "cityname") val cityName: String?
)