package com.breja.breja_br.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.breja.breja_br.Adapters.PromocoesAdapter;
import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
import com.breja.breja_br.Utils.FirebaseUtils;
import com.breja.breja_br.Utils.localizacao;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.breja.breja_br.R.id.navigation_add_promo;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView navigationView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PromocoesAdapter adapter;
    double latPoint;
    double lngPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        latPoint = i.getDouble("lat");
        lngPoint = i.getDouble("lng");
        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_home);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.getMenu();
        setUpRecyclerView();

    }
    @Override
    protected void onStart(){
            super.onStart();
            adapter.startListening();
    }
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
    private void setUpRecyclerView(){
        Query query = db.collection("Promotion")
                .whereLessThan("denunciar",6);
        FirestoreRecyclerOptions<Promocao> options = new FirestoreRecyclerOptions.Builder<Promocao>()
                .setQuery(query,Promocao.class)
                .build();

            adapter = new PromocoesAdapter(options);
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("lat",latPoint);
                i.putExtra("lng",lngPoint);
                startActivity(i);
                break;
            case R.id.navigation_perfil:
                Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                x.putExtra("lat",latPoint);
                x.putExtra("lng",lngPoint);
                startActivity(x);
                break;

            case navigation_add_promo:
                Intent z = new Intent(getApplicationContext(), CadastroPromocoesActivity.class);
                z.putExtra("lat",latPoint);
                z.putExtra("lng",lngPoint);
                startActivity(z);
                break;

            case R.id.navigation_map:
                Intent m = new Intent(getApplicationContext(), MapsActivity.class);
                m.putExtra("lat",latPoint);
                m.putExtra("lng",lngPoint);
                startActivity(m);
                break;
            case R.id.navigation_favoritos:
                Intent t = new Intent(getApplicationContext(), PromocoesFavoritasActivity.class);
                t.putExtra("lat",latPoint);
                t.putExtra("lng",lngPoint);
                startActivity(t);
                break;


        }
        return true;
    }


}
