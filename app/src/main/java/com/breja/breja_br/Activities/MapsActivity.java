package com.breja.breja_br.Activities;


import android.annotation.SuppressLint;;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.breja.breja_br.Adapters.InfoAdapter;
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


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

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
        final String[] imagem = new String[1];
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(new InfoAdapter(getApplicationContext()));
        db.collection("Promotion")
                .whereLessThan("denunciar", 6)
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
                                String tittle = document.get("beer").toString()+"&#"+img+"&#"+document.get("estabelecimento");
                                LatLng promocao = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(promocao).title(tittle).snippet("R$ " + document.get("value").toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                mMap.addPolyline(new PolylineOptions().add(promocao).color(R.color.colorBackground));
                                LatLng latLng = new LatLng(latPoint, lngPoint);
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(90).tilt(60).build();
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                mMap.setMyLocationEnabled(true);
                                mMap.setTrafficEnabled(true);


                            }
                        }

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
                        i.putExtra("activity","maps");
                        i.putExtra("lat", latPoint);
                        i.putExtra("lng", lngPoint);
                        startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    case "perfil":
                        Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                        x.putExtra("activity","maps");
                        x.putExtra("lat", latPoint);
                        x.putExtra("lng", lngPoint);
                        startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    case "favoritas":
                        Intent z = new Intent(getApplicationContext(), PerfilActivity.class);
                        z.putExtra("activity","maps");
                        z.putExtra("lat", latPoint);
                        z.putExtra("lng", lngPoint);
                        startActivity(z);  //O efeito ao ser pressionado do botão (no caso abre a activity)
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
                i.putExtra("activity","maps");
                i.putExtra("lat", latPoint);
                i.putExtra("lng", lngPoint);
                startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case "perfil":
                Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                x.putExtra("activity","maps");
                x.putExtra("lat", latPoint);
                x.putExtra("lng", lngPoint);
                startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case "favoritas":
                Intent z = new Intent(getApplicationContext(), PerfilActivity.class);
                z.putExtra("activity","maps");
                z.putExtra("lat", latPoint);
                z.putExtra("lng", lngPoint);
                startActivity(z);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

    }
        return;
        }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}

