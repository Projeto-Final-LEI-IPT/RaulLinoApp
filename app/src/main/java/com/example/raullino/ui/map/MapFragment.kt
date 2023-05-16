package com.example.raullino.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.raullino.JsonParse
import com.example.raullino.R
import com.example.raullino.ui.home.HomeFragment
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var mapView: MapView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Inicialize the map
        mapView = view.findViewById(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setBuiltInZoomControls(false)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(18.0)
        mapView.controller.setCenter(GeoPoint(39.461563, -8.197074))

        val myLocationoverlay = MyLocationNewOverlay(mapView)
        myLocationoverlay.enableMyLocation()
        mapView.overlays.add(myLocationoverlay)

        // Check for location permission
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        requestPermissionsIfNecessary(permissions)

        // Add touch listener to limit map view area
        val initialLocation = GeoPoint(39.461563, -8.197074)
        val maxLat = initialLocation.latitude + 0.08
        val minLat = initialLocation.latitude - 0.08
        val maxLon = initialLocation.longitude + 0.08
        val minLon = initialLocation.longitude - 0.08
        val mapTouchListener = View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val center = mapView.mapCenter
                val lat = center.latitude
                val lon = center.longitude
                if (mapView.zoomLevel < 13.0) {
                    mapView.controller.animateTo(initialLocation)
                    mapView.controller.setZoom(18.0)
                    return@OnTouchListener true
                }
                if (lat > maxLat || lat < minLat || lon > maxLon || lon < minLon) {
                    mapView.controller.animateTo(GeoPoint(39.461563, -8.197074))
                    return@OnTouchListener true
                }
            }
            false
        }

        mapView.setOnTouchListener(mapTouchListener)

        val fabContainer: RelativeLayout = view.findViewById(R.id.fab_container)
        fabContainer.setOnClickListener {
            // Lógica a ser executada quando o botão for clicado
            Toast.makeText(context, "Botão clicado", Toast.LENGTH_SHORT).show()
        }

        val jsonParse = JsonParse(requireContext());
        val num = jsonParse.get_number();

        for (i in 0..num - 1) {
            var id_edificio = jsonParse.get_id(i)
            var coords = jsonParse.get_coordinates(id_edificio);
            var title = jsonParse.get_title(id_edificio);
            val coords_array = coords.split(",").toTypedArray();
            val lat = coords_array[0].toDouble();
            val long = coords_array[1].toDouble();
            val point = GeoPoint(lat, long);
            addMarker(point, title);
        }

        return view
    }

    private fun requestPermissionsIfNecessary(permissions: Array<out String>) {
        val permissionsToRequest = ArrayList<String>()

        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    requireContext(), permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissions(
                permissionsToRequest.toTypedArray(), REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, do nothing
                } else {
                    // Permission denied, show a message or dialog
                    Toast.makeText(
                        context,
                        "É necessário conceder a permissão para usar esta funcionalidade.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private var currentInfoWindow: InfoWindow? = null
    private fun addMarker(p1: GeoPoint, title: String) {
        var point = p1
        var marker = Marker(mapView)

        // Cria e coloca a custom InfoWindow para os markers
        val customInfoWindow = CustomInfoWindow(R.layout.info_window, mapView, title) { marker ->
            // Click event na InfoWindow

        }
        marker.setOnMarkerClickListener { _, _ ->
            // Fecha a InfoWindow atualmente aberta
            currentInfoWindow?.close()

            // Abre a nova InfoWindow
            currentInfoWindow = customInfoWindow
            customInfoWindow.open(marker, point, 0, 0)

            // Indica que o clique foi tratado e não executa o comportamento padrão do marker
            true
        }

        mapView.setOnTouchListener { _, _ ->
            // Fecha a InfoWindow atualmente aberta quando houver um clique no mapa
            currentInfoWindow?.close()
            false
        }

        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        marker.setIcon(getResources().getDrawable(R.drawable.pontointeresse));
        //marker.setImage(getResources().getDrawable(R.drawable.add_button));
        //marker.setTitle(title)
        marker?.infoWindow = customInfoWindow
        mapView.overlays.add(marker)

    }

    inner class CustomInfoWindow(
        layoutResId: Int,
        mapView: MapView,
        private val title: String,
        param: (Any) -> Unit) : InfoWindow(layoutResId, mapView) {

        private lateinit var plusButton: Button

        override fun onClose() {
            // This method is called when the InfoWindow is closed
        }

        private lateinit var textInfoWindow: TextView
        override fun onOpen(item: Any?) {
            // Obter uma referência ao textInfoWindow no layout da InfoWindow
            textInfoWindow = mView.findViewById(R.id.textInfoWindow)

            // Definir o texto para o textInfoWindow através do ID do Json
            textInfoWindow.text = title

            val PlusButton: Button = mView.findViewById(R.id.plusButton)
            PlusButton.setOnClickListener {
                Log.d("MapFragment", "Clicado na InfoWindow!")
            }
        }

    }


}
