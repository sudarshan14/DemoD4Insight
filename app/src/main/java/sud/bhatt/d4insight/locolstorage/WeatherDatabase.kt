package sud.bhatt.d4insight.locolstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [WeatherInfo::class, CityInfo::class], version = 3)
@TypeConverters(DateTypeConvertor::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherInfo(): WeatherInfoDAO

    companion object {

        // migrating form version 1 to version 2
        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE weatherinfo ADD COLUMN info_new_date_2 INTEGER DEFAULT 0 NOT NULL")
            }
        }

        private val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE cityinfo  (cityid INTEGER NOT NULL, cityname TEXT, PRIMARY KEY(cityid))")

            }

        }


        // when value is updated in volatile variable , all the threads are
        // made aware of it and get updated values
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)//.also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weatherDB"
        )
            .addMigrations(migration_1_2, migration_2_3)
            .addTypeConverter(DateTypeConvertor())
            .build()
    }
}