package com.mariosodigie.apps.airpollutionexplore.explorefeature

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.mariosodigie.apps.airpollutionexplore.BaseActivity
import com.mariosodigie.apps.airpollutionexplore.R
import com.mariosodigie.apps.airpollutionexplore.extentions.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_explorer.requestProgress

class ExplorerActivity : BaseActivity(), OnMapReadyCallback {

    private val viewModel: ExplorerViewModel by viewModel()
    lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorer)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        addErrorSource(viewModel.serviceError)

        viewModel.requestInProgress.observe(this) {
            requestProgress.apply {
                visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        viewModel.response.observe(this){cityData ->
            startActivity(PollutionDetailsActivity.createIntent(this, cityData))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener{ location->
            viewModel.requestPollutionDetails(location)
        }
    }
}
