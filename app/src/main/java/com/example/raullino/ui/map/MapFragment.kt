package com.example.raullino.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.raullino.JsonParse
import com.example.raullino.R
import com.example.raullino.ui.buildingDetail.BuildingDetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import com.google.android.material.button.MaterialButtonToggleGroup
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var mapView: MapView
    private lateinit var Mbtg:MaterialButtonToggleGroup
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        val jsonParse = JsonParse(requireContext());

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
                    mapView.controller.animateTo(GeoPoint(initialLocation), 13.0, 500L)
                    return@OnTouchListener true
                }
                if (lat > maxLat || lat < minLat || lon > maxLon || lon < minLon) {
                    mapView.controller.animateTo(GeoPoint(initialLocation))
                    return@OnTouchListener true
                }
            }
            false
        }



        val toggleButton1: Button = view.findViewById(R.id.togglebutton2)
        toggleButton1.setOnClickListener {
            // Lógica a ser executada quando o botão for clicado
            // Por exemplo:
            toggleButton1.isSelected = !toggleButton1.isSelected
            if (toggleButton1.isSelected) {
                val roadManager = OSRMRoadManager(this.requireContext())
            val waypoints = ArrayList<GeoPoint>()

            // Todos os pontos 15, 6, 16, 14, 10, 11, 14, 7
            val itinerary1 = arrayListOf("15", "6", "16", "14", "11", "7")

            for (itinerary in itinerary1) {
                var coords = jsonParse.get_coordinates(itinerary);
                val coords_array = coords.split(",").toTypedArray();
                val lat = coords_array[0].toDouble();
                val long = coords_array[1].toDouble();
                waypoints.add(GeoPoint(lat, long))
            }

            lifecycleScope.launch {
                val road = withContext(Dispatchers.IO) {
                    roadManager.getRoad(waypoints)
                }
                val roadOverlay = RoadManager.buildRoadOverlay(road,Color.GREEN, 8F)
                mapView.overlays.add(roadOverlay)
                mapView.invalidate()
            }
            } else {
                mapView.overlays.removeLast()
                mapView.invalidate()
            }
        }

        val toggleButton2: Button = view.findViewById(R.id.togglebutton1)
        toggleButton2.setOnClickListener {

            // Lógica a ser executada quando o botão for clicado
            // Por exemplo:
            toggleButton2.isSelected = !toggleButton2.isSelected
            if (toggleButton2.isSelected) {
                val roadManager = OSRMRoadManager(this.requireContext())
                val waypoints = ArrayList<GeoPoint>()

                val itinerary2 = arrayListOf("7", "17")

                for (itinerary in itinerary2) {
                    var coords = jsonParse.get_coordinates(itinerary);
                    val coords_array = coords.split(",").toTypedArray();
                    val lat = coords_array[0].toDouble();
                    val long = coords_array[1].toDouble();
                    waypoints.add(GeoPoint(lat, long))
                }

                lifecycleScope.launch {
                    val road = withContext(Dispatchers.IO) {
                        roadManager.getRoad(waypoints)
                    }
                    val roadOverlay = RoadManager.buildRoadOverlay(road,Color.rgb(79, 79, 182), 8F)
                    mapView.overlays.add(roadOverlay)
                    mapView.invalidate()
                }
            } else {
                mapView.overlays.removeLast()
                mapView.invalidate()
            }
        }

        val num = jsonParse.get_number();

        for (i in 0..num - 1) {
            var id_edificio = jsonParse.get_id(i)
            var coords = jsonParse.get_coordinates(id_edificio);
            var title = jsonParse.get_title(id_edificio);
            val coords_array = coords.split(",").toTypedArray();
            val lat = coords_array[0].toDouble();
            val long = coords_array[1].toDouble();
            val point = GeoPoint(lat, long);
            addMarker(point, title, id_edificio);
        }

        mapView.setOnTouchListener(mapTouchListener)

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
    private fun addMarker(p1: GeoPoint, title: String, id_edificio: String) {
        var point = p1
        var marker = Marker(mapView)

        // Cria e coloca a custom InfoWindow para os markers
        val customInfoWindow = CustomInfoWindow(R.layout.info_window, mapView, title, id_edificio) { marker ->
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
        marker?.infoWindow = customInfoWindow
        mapView.overlays.add(marker)
    }

    //Personalização da InfoWindow
    inner class CustomInfoWindow(
        layoutResId: Int,
        mapView: MapView,
        private val title: String,
        private val id_edificio: String,
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

            // Botão + na infoWindow
            //val PlusButton: Button = mView.findViewById(R.id.plusButton)
            //PlusButton.setOnClickListener {
            //    openBuildingDetailFragment(id_edificio)
            //}

            // Clique do LinearLayout (InfoWindow)
            val linearLayout: LinearLayout = mView.findViewById(R.id.infowindow)
            linearLayout.setOnClickListener {
                openBuildingDetailFragment(id_edificio)
            }
        }

        //Abre e trasmite o ID do edificio (id_edificio) para o fragment BuildingDetailFragment
        private fun openBuildingDetailFragment(id_edificio: String) {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val buildingDetailFragment = BuildingDetailFragment.newInstance(id_edificio)
            fragmentTransaction.add(R.id.map, buildingDetailFragment)
            fragmentTransaction.addToBackStack("mapFragment")
            fragmentTransaction.commit()
        }
    }


}
