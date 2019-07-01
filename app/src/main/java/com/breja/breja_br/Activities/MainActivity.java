package com.breja.breja_br.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.breja.breja_br.Adapters.PromocoesAdapter;
import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
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
    String activity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);
        Bundle i = intent.getExtras();
        activity = i.getString("activity");
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
    protected void onResume(){
        super.onResume();
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
    protected void onPause(){
        super.onPause();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                break;
            case R.id.navigation_perfil:
                Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                x.putExtra("activity","main");
                x.putExtra("lat",latPoint);
                x.putExtra("lng",lngPoint);
                startActivity(x);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case navigation_add_promo:
                Intent z = new Intent(getApplicationContext(), CadastroPromocoesActivity.class);
                z.putExtra("activity","main");
                z.putExtra("lat",latPoint);
                z.putExtra("lng",lngPoint);
                startActivity(z);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case R.id.navigation_map:
                Intent m = new Intent(getApplicationContext(), MapsActivity.class);
                m.putExtra("activity","main");
                m.putExtra("lat",latPoint);
                m.putExtra("lng",lngPoint);
                startActivity(m);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case R.id.navigation_favoritos:
                Intent t = new Intent(getApplicationContext(), PromocoesFavoritasActivity.class);
                t.putExtra("activity","main");
                t.putExtra("lat",latPoint);
                t.putExtra("lng",lngPoint);
                startActivity(t);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;


        }
        return true;
    }


}
