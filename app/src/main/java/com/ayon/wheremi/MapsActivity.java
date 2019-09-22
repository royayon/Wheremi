package com.ayon.wheremi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity  /*extends AppCompatActivity*/ /*implements OnMapReadyCallback*/ {

    private GoogleMap mMap;
    private EditText srcAddr,destAddr;
    private Button getDir;


    private WebView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        /*// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        srcAddr = findViewById(R.id.srcAddr);
        destAddr = findViewById(R.id.destAddr);
        getDir = findViewById(R.id.getDir);

        getDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String src = srcAddr.getText().toString();
                String dest = destAddr.getText().toString();

                showMap(src,dest,"driving");

            }
        });
        showMap("MIST","MIST","driving");

    }

    public void showMap(String src, String dest, String mode) {
        mapView = findViewById(R.id.mapView);
        WebSettings webSettings = mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        //mapView.loadUrl("http://maps.google.com/maps?saddr=osmany hall&daddr=mist");
        mapView.loadUrl("https://directionsdebug.firebaseapp.com/embed.html?origin=" + src + "&destination=" + dest + "&mode=" + mode);
        mapView.setWebViewClient(new WebViewClient(){
            /*public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mapView.loadUrl("https://directionsdebug.firebaseapp.com/embed.html?origin=osmany%20hall&destination=chashara&mode=driving&region=BD");
            }*/
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        mapView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                /*super.onPermissionRequest(request);*/
                request.grant(request.getResources());
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(mapView.canGoBack()){
            mapView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Integer.parseInt(LOCATION_SERVICE));
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in MIST and move the camera
        LatLng MIST = new LatLng(23.837780, 90.358045);
        mMap.addMarker(new MarkerOptions().position(MIST).title("MIST"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MIST,15F));

        LatLng SRC = new LatLng(23.836631, 90.363179);

        mMap.addPolyline(new PolylineOptions().add(SRC,MIST).width(10).color(Color.parseColor("#598BE2")));
    }*/
}
