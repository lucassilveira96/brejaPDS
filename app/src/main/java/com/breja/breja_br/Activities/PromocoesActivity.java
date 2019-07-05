package com.breja.breja_br.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.breja.breja_br.Models.Comentarios;
import com.breja.breja_br.Models.Curtidas;
import com.breja.breja_br.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PromocoesActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView txt_value;
    private MapView mapView;
    private GoogleMap gmap;
    FloatingActionButton btn_curtir;
    double lat;
    double lng;
    String idpromotion;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String activity;
    Boolean verifica;
    private int curtir;
    TextView txt_curtida;
    String produto;
    FloatingActionButton comentarios;
    final FirebaseAuth auth = com.google.firebase.auth.FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        String id = i.getString("image");
        lat = i.getDouble("lat");
        lng = i.getDouble("lng");
        produto=i.get("produto").toString();
        activity = i.getString("activity");
        btn_curtir = findViewById(R.id.btn_comentar);
        Bundle mapViewBundle = null;
        txt_curtida=findViewById(R.id.txt_curtida);
        txt_curtida.setText(i.get("curtir").toString());
        comentarios=findViewById(R.id.btn_comentario);
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        btn_curtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Curtidas curtidas = new Curtidas(auth.getCurrentUser().getEmail(), id);
                db.collection("Curtidas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        verifica=false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String email = document.getString("email");
                            String uriImg = document.getString("uriImg");
                            if (email.equals(curtidas.getEmail()) && uriImg.equals(curtidas.getUriImg())) {
                                verifica = true;
                                break;
                            }

                        }
                        if (verifica == false) {
                            Toast.makeText(getApplicationContext(),"promoção curtida",Toast.LENGTH_SHORT).show();
                            db.collection("Curtidas")
                                    .add(curtidas).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    db.collection("Promotion")
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(QueryDocumentSnapshot document : task.getResult()){
                                                if(document.get("uriImg").equals(curtidas.getUriImg())){
                                                    idpromotion=document.getId();
                                                    curtir=Integer.parseInt(document.get("curtir").toString())+1;
                                                    db.collection("Promotion")
                                                            .document(idpromotion)
                                                            .update("curtir",curtir).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            txt_curtida.setText(""+curtir);
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        if (verifica == true) {
                            Toast.makeText(getApplicationContext(),"Você acabou de descurtir está promoção.",Toast.LENGTH_SHORT).show();
                            db.collection("Curtidas")
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        if(curtidas.getEmail().equals(document.get("email"))&&curtidas.getUriImg().equals(document.get("uriImg"))){
                                            db.collection("Curtidas")
                                                    .document(document.getId())
                                                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    db.collection("Promotion")
                                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            for(QueryDocumentSnapshot document : task.getResult()){
                                                                if(document.get("uriImg").equals(curtidas.getUriImg())){
                                                                    idpromotion=document.getId();
                                                                    curtir=Integer.parseInt(document.get("curtir").toString())-1;
                                                                    db.collection("Promotion")
                                                                            .document(idpromotion)
                                                                            .update("curtir",curtir).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(curtir<=0){
                                                                                txt_curtida.setText("0");
                                                                            }
                                                                            else {
                                                                                txt_curtida.setText("" + (curtir));
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                }
                            });

                        }
                    }
                });

            }
        });
        comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ComentariosActivity.class);
                i.putExtra("uriImg",id);
                startActivity(i);
            }
        });


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
        gmap.addMarker(new MarkerOptions().position(ny).title(""+produto));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
    public void onBackPressed(){ //Botão BACK padrão do android
        switch (activity) {
            case "main":
                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                x.putExtra("activity","main");
                x.putExtra("lat", lat);
                x.putExtra("lng", lng);
                startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

        }
        return;
    }
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home://ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                switch (activity) {
                    case "main":
                    Intent x = new Intent(getApplicationContext(), MainActivity.class);
                    x.putExtra("activity","main");
                    x.putExtra("lat", lat);
                    x.putExtra("lng", lng);
                    startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                    finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                    break;
                }
                break;
        }
        return true;
    }


}
