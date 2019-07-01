package com.breja.breja_br.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.breja.breja_br.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();
    double latPoint;
    double lngPoint;
    String activity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setTitle("Breja");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        activity = i.getString("activity");
        latPoint = i.getDouble("lat");
        lngPoint = i.getDouble("lng");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        db.collection("Promotion")
                .whereLessThan("denunciar",6)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // list.add(document.getString("content"));
                                double lat = document.getDouble("lat");
                                double lng = document.getDouble("lng");
                                String img = document.getString("uriImg");
                                String tittle = document.get("beer").toString()+" "+document.get("estabelecimento").toString();
                                LatLng promocao = new LatLng(lat,lng);
                                mMap.addMarker(new MarkerOptions().position(promocao).title(tittle).snippet("Valor: R$ "+document.get("value").toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                mMap.addPolyline(new PolylineOptions().add(promocao).color(R.color.colorBackground));
                                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                    // Use default InfoWindow frame
                                    @Override
                                    public View getInfoWindow(Marker arg0) {
                                        return null;
                                    }

                                    // Defines the contents of the InfoWindow
                                    @Override
                                    public View getInfoContents(Marker arg0) {
                                        View v = null;
                                        try {

                                            // Getting view from the layout file info_window_layout
                                            v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);

                                            // Getting reference to the TextView to set latitude
                                            ImageView foto = findViewById(R.id.clientPic);
                                            TextView produtoTxt = (TextView) v.findViewById(R.id.txt_produto_maps);
                                            Picasso.get().load(img).into(foto);
                                            produtoTxt.setText(arg0.getTitle());

                                        } catch (Exception ev) {
                                            System.out.print(ev.getMessage());
                                        }

                                        return v;
                                    }
                                });
                            }

                        } else {
                        }
                        LatLng latLng = new LatLng(latPoint,lngPoint);;
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(90).tilt(60).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mMap.setMyLocationEnabled(true);
                        mMap.setTrafficEnabled(true);
                    }
                });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home://ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                switch (activity) {
                    case "main":
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("lat", latPoint);
                        i.putExtra("lng", lngPoint);
                        startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                }
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        switch (activity) {
            case "main":
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("lat", latPoint);
                i.putExtra("lng", lngPoint);
                startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
    }
        return;
        }
    }

