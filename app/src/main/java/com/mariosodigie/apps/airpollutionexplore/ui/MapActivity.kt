package com.mariosodigie.apps.airpollutionexplore.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.mariosodigie.apps.airpollutionexplore.R
import com.mariosodigie.apps.airpollutionexplore.databinding.ActivityMapBinding
import com.mariosodigie.apps.airpollutionexplore.extentions.observe
import com.mariosodigie.apps.airpollutionexplore.network.NetworkError
import com.mariosodigie.apps.airpollutionexplore.network.OnNetworkErrorListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity: AppCompatActivity(), OnMapReadyCallback, OnNetworkErrorListener {

//    @Inject
//    lateinit var viewModel: PollutionViewModel

    private val viewModel: PollutionViewModel by viewModels()

    lateinit var mMap: GoogleMap
    override var alertDialog: AlertDialog? = null

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        addErrorSource(viewModel.networkError)

        viewModel.requestInProgress.observe(this) {
            binding.requestProgress.apply {
                visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        viewModel.pollutionResponse.observe(this){cityData ->
            startActivity(PollutionDetailsActivity.createIntent(this, cityData))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(46.935249, 7.467382), 5F
            )
        )
        mMap.setOnMapClickListener{ location->
            viewModel.requestPollutionDetails(location)
        }
    }
    override fun addErrorSource(source: LiveData<NetworkError>) {
        source.observe(this, ::showErrorDialog)
    }

    override fun showErrorDialog(error: NetworkError) {
        alertDialog = AlertDialog.Builder(this)
            .apply {
                setTitle(error.title)
            }
            .setMessage(error.message)
            .setPositiveButton(R.string.dialog_ok){ dialog, _->
                dialog.dismiss()
            }
            .setOnCancelListener { dialog->
                dialog.dismiss()
            }
            .setOnDismissListener{
                alertDialog = null
            }
            .apply {  error.icon?.let { this.setIcon(it) } }
            .show()

    }
}