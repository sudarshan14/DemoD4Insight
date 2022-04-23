package sud.bhatt.d4insight.view.weather.model

import com.google.gson.annotations.SerializedName

data class Wind(

    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int
)