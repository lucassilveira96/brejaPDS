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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Query query= db.collection("Promotion").orderBy("value",Query.Direction.ASCENDING);
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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.navigation_perfil:
                startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
                break;

            case navigation_add_promo:
                startActivity(new Intent(getApplicationContext(), CadastroPromocoesActivity.class));
                break;

            case R.id.navigation_map:
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                break;


        }
        return true;
    }

}
