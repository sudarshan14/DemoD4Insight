package sud.bhatt.d4insight.view.weather


import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import sud.bhatt.d4insight.databinding.FragmentWeatherItemBinding
import sud.bhatt.d4insight.view.weather.model.CityName
import sud.bhatt.d4insight.view.weather.model.CityWeatherDetails
import kotlin.math.roundToInt

class WeatherAdapter(private val listener: CellClickListener) :
    RecyclerView.Adapter<WeatherAdapter.MainViewHolder>() {

    private var weatherData: CityWeatherDetails? = null
    private var cityNames: List<CityName>? = null
    private var selectedItemId = -1

    fun showCityTemperatureDetails(data: CityWeatherDetails) {
        this.weatherData = data
        notifyDataSetChanged()
    }

    fun updateCities(data: List<CityName>) {
        this.cityNames = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentWeatherItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

//        val data = weatherData
        val name = cityNames?.get(position)?.name
        holder.binding.city.text = name
        holder.binding.apply {
            if (position == selectedItemId && weatherData != null) {

                weatherData!!.main.apply {
                    tvMinTemp.text = "Min Temperature is : $temp_min \u00B0 kelvin"
                    tvMaxTemp.text = "Max Temperature is : $temp_max ° kelvin"
                    tvHumidity.text = "Humidity is : $humidity %"
                    tvTemp.text = "${(temp - 273.15).roundToInt()}℃"
                }
                cardGroup.visibility = View.VISIBLE
                show.setImageResource(R.drawable.arrow_up_float)
            } else {
                cardGroup.visibility = View.GONE
                show.setImageResource(R.drawable.arrow_down_float)
            }

        }

        holder.binding.apply {

            show.setOnClickListener {
                selectedItemId = holder.adapterPosition
                if (cardGroup.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardView,
                        AutoTransition()
                    )
                    cardGroup.visibility = View.GONE
                    show.setImageResource(R.drawable.arrow_down_float)
                } else {
                    listener.onCellClickListener(name)
                    TransitionManager.beginDelayedTransition(
                        baseCardView,
                        AutoTransition()
                    )
                    cardGroup.visibility = View.VISIBLE
                    show.setImageResource(R.drawable.arrow_up_float)
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return cityNames?.size ?: 0
//        return if (weatherData == null) 0 else 1
    }


    class MainViewHolder(val binding: FragmentWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root)
//class MainViewHolder(val binding: WeatherInfoItemBinding) : RecyclerView.ViewHolder(binding.root)
}

interface CellClickListener {
    fun onCellClickListener(data: String?)
}