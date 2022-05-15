package sud.bhatt.d4insight.locolstorage

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
class DateTypeConvertor {

    @TypeConverter
    fun fromDateToLong(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun fromLongToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}