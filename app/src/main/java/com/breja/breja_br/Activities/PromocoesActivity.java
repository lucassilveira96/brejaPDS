package com.breja.breja_br.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.breja.breja_br.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class PromocoesActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imageView_foto_promocao;
    TextView txt_value;
    private MapView mapView;
    private GoogleMap gmap;
    private TextView txt_estabelecimento;
    private TextView txt_produto;
    double lat;
    double lng;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes);
        imageView_foto_promocao = findViewById(R.id.img_foto_promocao);
        txt_value = findViewById(R.id.txt_value_beer);
        txt_estabelecimento = findViewById(R.id.txt_etb);
        txt_produto= findViewById(R.id.txt_pdt);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        String id = i.getString("image");
        lat = i.getDouble("lat");
        lng = i.getDouble("lng");
        Picasso.get().load(id).into(imageView_foto_promocao);
        txt_value.setText("R$ "+ i.get("valor"));
        txt_produto.setText(i.get("produto").toString());
        txt_estabelecimento.setText(i.get("estabelecimento").toString());
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(lat, lng);
        gmap.addMarker(new MarkerOptions().position(ny).title(txt_produto.getText() + " "+txt_value.getText()+" "+txt_estabelecimento.getText()));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
