package de.mt.shop.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.mt.shop.databinding.FragmentMapBinding;

public class MapFragment extends Fragment implements OnMapReadyCallback, OnCompleteListener { // 1

    private FragmentMapBinding binding; // 0
    private MapView mapView; // 0
    private GoogleMap googleMap; // 0

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { // 1

        binding = FragmentMapBinding.inflate(inflater, container, false); // 2
        View root = binding.getRoot(); // 2
        mapView = binding.mapView; // 2
        Bundle mapViewBundle = null; // 1
        if (savedInstanceState != null) { // 2
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey"); // 2
        }
        mapView.onCreate(mapViewBundle); // 1
        mapView.getMapAsync(this); // 1

        return root; // 1
    }

    @Override // 1
    public void onSaveInstanceState(Bundle outState) { // 1
        super.onSaveInstanceState(outState); // 1

        Bundle mapViewBundle = outState.getBundle("MapViewBundleKey"); // 2
        if (mapViewBundle == null) { // 2
            mapViewBundle = new Bundle(); // 2
            outState.putBundle("MapViewBundleKey", mapViewBundle); // 1
        }

        mapView.onSaveInstanceState(mapViewBundle); // 1
    }

    @Override // 1
    public void onResume() { // 1
        super.onResume(); // 1
        mapView.onResume(); // 1
    }

    @Override // 1
    public void onStart() { // 1
        super.onStart(); // 1
        mapView.onStart(); // 1
    }

    @Override // 1
    public void onStop() { // 1
        super.onStop(); // 1
        mapView.onStop(); // 1
    }

    @Override // 1
    public void onPause() { // 1
        mapView.onPause(); // 1
        super.onPause(); // 1
    }

    @Override // 1
    public void onDestroy() { // 1
        mapView.onDestroy(); // 1
        super.onDestroy(); // 1
    }

    @Override // 1
    public void onLowMemory() { // 1
        super.onLowMemory(); // 1
        mapView.onLowMemory(); // 1
    }

    @Override // 1
    public void onCreate(@Nullable Bundle savedInstanceState) { // 1
        super.onCreate(savedInstanceState); // 1

        if (ActivityCompat.checkSelfPermission(this.getContext(), // 3
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && // 5
                ActivityCompat.checkSelfPermission(this.getContext(), // 2
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // 4
            ActivityCompat.requestPermissions(this.getActivity(), // 2
                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1); // 3
            ActivityCompat.requestPermissions(this.getActivity(), // 2
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1); // 3
        }

        FusedLocationProviderClient location = LocationServices. // 1
                getFusedLocationProviderClient(this.getContext()); // 2
        location.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null) // 2
                .addOnCompleteListener(this); // 1
    }

    @Override // 1
    public void onDestroyView() { // 1
        super.onDestroyView(); // 1
        binding = null; // 1
    }

    @SuppressLint("MissingPermission") // 1
    @Override // 1
    public void onMapReady(@NonNull GoogleMap googleMap) { // 1
        this.googleMap = googleMap; // 2
        googleMap.setMyLocationEnabled(true); // 1
        googleMap.setMinZoomPreference(16); // 1
    }

    @Override // 1
    public void onComplete(@NonNull Task task) { // 1
        Location loc = (Location) task.getResult(); // 3
        double lng = loc.getLongitude(); // 2
        double lat = loc.getLatitude(); // 2

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng))); // 3

        MarkerOptions marker = new MarkerOptions(); // 2
        marker.position(new LatLng(lat + 0.002, lng - 0.003)); // 4
        googleMap.addMarker(marker); // 1
    }
}

// 112