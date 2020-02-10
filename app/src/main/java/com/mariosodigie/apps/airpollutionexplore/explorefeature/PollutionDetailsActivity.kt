package com.mariosodigie.apps.airpollutionexplore.explorefeature

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mariosodigie.apps.airpollutionexplore.R
import com.mariosodigie.apps.airpollutionexplore.explorefeature.model.PollutionResponse
import com.mariosodigie.apps.airpollutionexplore.extentions.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_pollution_details.*

private const val EXTRA_CITY_DATA = "extra_city_data"

class PollutionDetailsActivity : AppCompatActivity() {

    private val viewModel: PollutionDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pollution_details)

        viewModel.pollutionObject.observe(this){data->
            fillActivity(data)
        }

        viewModel.getDataObject(intent.getStringExtra(EXTRA_CITY_DATA))
    }

    private fun fillActivity(data: PollutionResponse){
        setSupportActionBar(my_toolbar)
        supportActionBar?.apply {
            title = data.city
            subtitle = "Air Pollution Data"
            setDisplayHomeAsUpEnabled(true)
        }

        val cityText = getString(R.string.city_name, data.city)
        city_details.text = cityText

        val stateText = getString(R.string.state_name, data.state)
        state_details.text = stateText

        val countryText = getString(R.string.country_name, data.country)
        country_details.text = countryText

        val aqiLevel = viewModel.getPollutionViewStyle(data.pollution.qualityIndex)
        pollution_cardview.setCardBackgroundColor(resources.getColor(aqiLevel.colour))
        pollution_title.text = resources.getText(aqiLevel.title)

        val aqiValue = getString(R.string.city_us_aqi, data.pollution.qualityIndex)
        pollution_aqi.text = aqiValue

        val cityTemperature = getString(R.string.city_temperature, data.weather.temperature)
        temperature.text = cityTemperature

        val cityHumidity = getString(R.string.city_humidity, data.weather.humidity)
        humidity.text = cityHumidity

        val cityWind = getString(R.string.city_wind, data.weather.wind)
        wind.text = cityWind

        weather_icon.setImageDrawable(viewModel.getWeatherIcon(data.weather.icon))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        fun createIntent(context: Context, vin: String?): Intent {
            return Intent(context, PollutionDetailsActivity::class.java).putExtra(EXTRA_CITY_DATA, vin)
        }
    }
}
