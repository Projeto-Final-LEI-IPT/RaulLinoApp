package com.example.raullino.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
import kotlinx.coroutines.CoroutineScope
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var mapView: MapView
    private lateinit var Mbtg:MaterialButtonToggleGroup

    private var waypoints: ArrayList<GeoPoint>? = null
    private lateinit var roadManager: RoadManager
    private var instructionsMarkers: ArrayList<Marker> = ArrayList()
    private lateinit  var myLocationoverlay:MyLocationNewOverlay
    private var road_Overlay: Polyline? = null

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

        myLocationoverlay = MyLocationNewOverlay(mapView)
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

        // Rota Verde
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

        //Rota Azul
        val toggleButton2: Button = view.findViewById(R.id.togglebutton1)
        toggleButton2.setOnClickListener {

            // Lógica a ser executada quando o botão for clicado
            // Por exemplo:
            toggleButton2.isSelected = !toggleButton2.isSelected
            if (toggleButton2.isSelected) {
                val roadManager = OSRMRoadManager(this.requireContext())
                val waypoints = ArrayList<GeoPoint>()

                //Rota 1 - Draw Polyline
                val polyline = Polyline()
                // Set polyline attributes (color, thickness, etc.)
                // Set the color to #4F4FB6 -> Purple
                polyline.color = 0xFF4F4FB6.toInt()
                polyline.width = 8f

                polyline.addPoint(GeoPoint(39.461228, -8.198713))
                polyline.addPoint(GeoPoint(39.461843, -8.198083))
                polyline.addPoint(GeoPoint(39.461454, -8.197265))
                polyline.addPoint(GeoPoint(39.462100, -8.196200))
                polyline.addPoint(GeoPoint(39.462570, -8.196334))
                polyline.addPoint(GeoPoint(39.463444, -8.196728))
                polyline.addPoint(GeoPoint(39.464757, -8.197524))

                // Add the polyline to the map
                mapView.overlayManager.add(polyline)
                mapView.invalidate()
            } else {
                mapView.overlays.removeLast()
                mapView.invalidate()
            }
        }

        // Get screen orientation
        val orientation = resources.configuration.orientation
        // If landscape, hide the header
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val header = view.findViewById<ConstraintLayout>(R.id.include)
            header.visibility = View.GONE
            val decorView = requireActivity().window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }else{
            val header = view.findViewById<ConstraintLayout>(R.id.include)
            header.visibility = View.VISIBLE
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

        // Create a roadManager instance using the OSRMRoadManager
        roadManager = OSRMRoadManager(activity)

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
        val customInfoWindow = CustomInfoWindow(R.layout.info_window, mapView, title, id_edificio, p1) { marker ->
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
        private val point: GeoPoint,
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
            val PlusButton: ImageView = mView.findViewById(R.id.btn_caminho)
            PlusButton.setOnClickListener {
               // mostra rota
                roadRoute(myLocationoverlay.myLocation,point)
            }

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

    private fun roadRoute(myLocation: GeoPoint, location: GeoPoint) {
        var getRoute = true
        //Verify if the user is getting far way from the route
      /*  if (road_Overlay != null) {
            getRoute = !(road_Overlay!!.isCloseTo(GeoPoint(myLocation), 50.0, mapView))
        }*/

        //If the user is too far away or if the roadOverlay is null get the route
        if (getRoute) {
            // Create a list of waypoints for the route
            waypoints = ArrayList()
            waypoints!!.add(
                GeoPoint(myLocation)
            )
            waypoints!!.add(
                location
            )
            // Create a coroutine scope
            val coroutineScope = CoroutineScope(Dispatchers.IO)

            // Launch a coroutine within the scope
            coroutineScope.launch {
                // Perform network request on a background thread
                val road = roadManager.getRoad(waypoints)

                // Use withContext to switch to the main thread for UI updates
                withContext(Dispatchers.Main) {
                    //Check road status
                    if (road.mStatus != Road.STATUS_OK) {
                        var text = activity?.getString(R.string.error_route)
                        if (road.mStatus == Road.STATUS_INVALID) {
                            text += " " + activity?.getString(R.string.error_route_invalid)
                        } else if (road.mStatus == Road.STATUS_TECHNICAL_ISSUE) {
                            text += " " + activity?.getString(R.string.error_route_technical_issues)
                        }
                        Toast.makeText(
                            activity, text, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        road.mBoundingBox
                        road.mLength

                    }
                    //Remove the overlays
                    for (i in 0 until instructionsMarkers.size) {
                        mapView.overlays.remove(instructionsMarkers[i])
                    }
                    //Remove the instructionsMarkers from the array by replacing it with a new one
                    instructionsMarkers = ArrayList()
                    val nodeIcon = ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_baseline_circle_24, null
                    )
                    //Get instructions for the route
                    for (i in 0 until road.mNodes.size) {
                        val node = road.mNodes[i]
                        val instructMarker = Marker(mapView)
                        instructMarker.position = node.mLocation
                        instructMarker.icon = nodeIcon
                        instructMarker.title = "$i"
                        instructMarker.subDescription = Road.getLengthDurationText(
                            activity, node.mLength, node.mDuration
                        )
                        instructMarker.snippet = node.mInstructions
                        instructionsMarkers.add(instructMarker)

                        val icon = getManeuverIcon(node.mManeuverType)
                        instructMarker.image = icon
                        mapView.overlays.add(instructMarker)
                    }
//                    locationRoute = location
                    // Remove old route overlay
                    if (road_Overlay != null) {
                        mapView.overlays.remove(road_Overlay)
                    }

                    //mapView.overlays.removeLast()
                    // Display the new route on the map
                    road_Overlay = RoadManager.buildRoadOverlay(road)
                        mapView.overlays.add(road_Overlay)
                    mapView.invalidate()
                }
            }
        }
    }

    /**
     * Get the maneuver icon
     */
    private fun getManeuverIcon(value: Int): Drawable? {
        return when (value) {
            1 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_continue) // Continue
            6 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_slight_right
            ) // Slight right
            7 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_turn_right) // Right
            8 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_sharp_right
            ) // Sharp right
            12 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_u_turn) // U-turn
            5 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_sharp_left
            ) // Sharp left
            4 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_turn_left) // Left
            3 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_slight_left
            ) // Slight left
            24 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_arrived
            ) // Arrived (at waypoint)
            27 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_roundabout
            ) // Round-about, 1st exit
            28 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_roundabout
            ) // 2nd exit, etc ...
            29 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_roundabout)
            30 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_roundabout)
            31 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_roundabout)
            32 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_roundabout)
            33 -> ContextCompat.getDrawable(requireActivity(), R.drawable.ic_roundabout)
            34 -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_roundabout
            )
            else -> ContextCompat.getDrawable(
                requireActivity(), R.drawable.ic_empty
            )
        }
    }
}
