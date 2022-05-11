package com.mariosodigie.apps.airpollutionexplore.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.mariosodigie.apps.airpollutionexplore.R
import com.mariosodigie.apps.airpollutionexplore.databinding.ActivityPollutionDetailsBinding
import com.mariosodigie.apps.airpollutionexplore.domain.model.PollutionInfo
import dagger.hilt.android.AndroidEntryPoint

private const val EXTRA_CITY_DATA = "extra_city_data"

@AndroidEntryPoint
class PollutionDetailsActivity : AppCompatActivity() {

    private val viewModel: PollutionViewModel by viewModels()
    private lateinit var binding: ActivityPollutionDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPollutionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillActivity(intent.getParcelableExtra(EXTRA_CITY_DATA)!!)
    }

    private fun fillActivity(data: PollutionInfo){
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.apply {
            title = data.city
            subtitle = "Air Pollution Data"
            setDisplayHomeAsUpEnabled(true)
        }

        with(binding){
            val cityText = getString(R.string.city_name, data.city)
            cityDetails.text = cityText

            val stateText = getString(R.string.state_name, data.state)
            stateDetails.text = stateText

            val countryText = getString(R.string.country_name, data.country)
            countryDetails.text = countryText

            val aqiLevel = getPollutionViewStyle(data.qualityIndex ?: -1)
            pollutionCardview.setCardBackgroundColor(resources.getColor(aqiLevel.colour))
            pollutionTitle.text = resources.getText(aqiLevel.title)

            val aqiValue = getString(R.string.city_us_aqi, data.qualityIndex)
            pollutionAqi.text = aqiValue

            val cityTemperature = getString(R.string.city_temperature, data.temperature)
            temperature.text = cityTemperature

            val cityHumidity = getString(R.string.city_humidity, data.humidity)
            humidity.text = cityHumidity

            val cityWind = getString(R.string.city_wind, data.wind)
            wind.text = cityWind

            data.icon?.let { weatherIcon.setImageDrawable(getWeatherIcon(it)) }
        }
    }

    fun getPollutionViewStyle(aqi: Int): AqiLevel {
        return when(aqi){
            in 0..50 -> AqiLevel.Good
            in 51..100 -> AqiLevel.Moderate
            in 101..150 -> AqiLevel.UnhealthySensitive
            in 151..200 -> AqiLevel.Unhealthy
            in 201..300 -> AqiLevel.VeryUnhealthy
            in 301..500 -> AqiLevel.Hazardous
            else -> AqiLevel.Undefined
        }
    }

    fun getWeatherIcon(name: String): Drawable {
        val sanitizedName = "ic_$name"
        val resource = resources.getIdentifier(sanitizedName, "drawable", packageName)
        return resources.getDrawable(resource, null)
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
        fun createIntent(context: Context, pollutionInfo: PollutionInfo?): Intent {
            return Intent(context, PollutionDetailsActivity::class.java).putExtra(EXTRA_CITY_DATA, pollutionInfo)
        }
    }
}
