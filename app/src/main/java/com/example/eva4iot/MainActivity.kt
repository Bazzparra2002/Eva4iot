package com.example.eva4iot  // <-- usa tu package real

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, SensorEventListener {

    private lateinit var fusedClient: FusedLocationProviderClient
    private lateinit var sensorManager: SensorManager
    private lateinit var wifiManager: WifiManager
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var map: GoogleMap

    private val LOCATION_PERMISSION = 100

    // UI
    private lateinit var textLocation: TextView
    private lateinit var textAccelerometer: TextView
    private lateinit var textWifi: TextView
    private lateinit var textBluetooth: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textLocation = findViewById(R.id.text_location)
        textAccelerometer = findViewById(R.id.text_accelerometer)
        textWifi = findViewById(R.id.text_wifi)
        textBluetooth = findViewById(R.id.text_bluetooth)

        //Sensores
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        startAccelerometer()
        updateWifiStatus()
        updateBluetoothStatus()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        checkPermissions()
    }

    private fun checkPermissions() {
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION
            )
        } else startLocationUpdates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            Toast.makeText(this, "Permiso de ubicación requerido", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 2000
        ).build()

        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            mainLooper
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            for (location in result.locations) {
                updateLocationUI(location)
                updateMapLocation(location)
            }
        }
    }

    private fun updateLocationUI(location: Location) {
        textLocation.text = "Ubicación: Lat: ${location.latitude} | Lon: ${location.longitude}"
    }

    private fun updateMapLocation(location: Location) {
        val pos = LatLng(location.latitude, location.longitude)
        map.clear()
        map.addMarker(MarkerOptions().position(pos).title("Tu ubicación"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16f))
    }
    private fun startAccelerometer() {
        val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = "%.2f".format(event.values[0])
            val y = "%.2f".format(event.values[1])
            val z = "%.2f".format(event.values[2])

            textAccelerometer.text = "Acelerómetro: X: $x | Y: $y | Z: $z"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    private fun updateWifiStatus() {
        textWifi.text = if (wifiManager.isWifiEnabled) "Wi-Fi: Activado" else "Wi-Fi: Desactivado"
    }
    @SuppressLint("MissingPermission")
    private fun updateBluetoothStatus() {
        val adapter = bluetoothManager.adapter
        textBluetooth.text = when {
            adapter == null -> "BLUETOOTH: No soportado"
            !adapter.isEnabled -> "BLUETOOTH: Desactivado"
            adapter.bluetoothLeScanner == null -> "BLUETOOTH: Desactivado"
            else -> "BLUETOOTH: Activado"
        }
    }
}
