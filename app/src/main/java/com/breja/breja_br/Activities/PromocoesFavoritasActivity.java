package com.breja.breja_br.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.breja.breja_br.Adapters.PromocoesFavoritasAdapter;
import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import static com.breja.breja_br.R.id.navigation_add_promo;

public class PromocoesFavoritasActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView navigationView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PromocoesFavoritasAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    double latPoint;
    double lngPoint;
    private String activity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes_favoritas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        activity = i.getString("activity");
        latPoint = i.getDouble("lat");
        lngPoint = i.getDouble("lng");
        navigationView = findViewById(R.id.navigation_favorite);
        navigationView.setSelectedItemId(R.id.navigation_favoritos);
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
        Query query = db.collection("Favoritas")
                .whereEqualTo("usuario",mAuth.getCurrentUser().getEmail());
        FirestoreRecyclerOptions<Promocao> options = new FirestoreRecyclerOptions.Builder<Promocao>()
                .setQuery(query,Promocao.class)
                .build();

        adapter = new PromocoesFavoritasAdapter(options,latPoint,lngPoint);
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
                i.putExtra("activity","favoritas");
                i.putExtra("lat",latPoint);
                i.putExtra("lng",lngPoint);
                finishAffinity();
                startActivity(i);
                break;
            case R.id.navigation_perfil:
                Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                x.putExtra("activity","favoritas");
                x.putExtra("lat",latPoint);
                x.putExtra("lng",lngPoint);
                finishAffinity();
                startActivity(x);
                break;

            case navigation_add_promo:
                Intent z = new Intent(getApplicationContext(), CadastroPromocoesActivity.class);
                z.putExtra("activity","favoritas");
                z.putExtra("lat",latPoint);
                z.putExtra("lng",lngPoint);
                finishAffinity();
                startActivity(z);
                break;

            case R.id.navigation_map:
                Intent m = new Intent(getApplicationContext(), MapsActivity.class);
                m.putExtra("activity","favoritas");
                m.putExtra("lat",latPoint);
                m.putExtra("lng",lngPoint);
                finishAffinity();;
                startActivity(m);
                break;
            case R.id.navigation_favoritos:
                break;


        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home://ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                switch (activity) {
                    case "perfil":
                        Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                        x.putExtra("activity","favoritas");
                        x.putExtra("lat", latPoint);
                        x.putExtra("lng", lngPoint);
                        startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    case "main":
                        Intent z = new Intent(getApplicationContext(), MainActivity.class);
                        z.putExtra("activity","favoritas");
                        z.putExtra("lat", latPoint);
                        z.putExtra("lng", lngPoint);
                        startActivity(z);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    case "maps":
                        Intent j = new Intent(getApplicationContext(), MapsActivity.class);
                        j.putExtra("activity","favoritas");
                        j.putExtra("lat", latPoint);
                        j.putExtra("lng", lngPoint);
                        startActivity(j);  //O efeito ao ser pressionado do botão (no caso abre a activity)
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
            case "perfil":
                Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                x.putExtra("activity","favoritas");
                x.putExtra("lat", latPoint);
                x.putExtra("lng", lngPoint);
                startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case "main":
                Intent z = new Intent(getApplicationContext(), MainActivity.class);
                z.putExtra("activity","favoritas");
                z.putExtra("lat", latPoint);
                z.putExtra("lng", lngPoint);
                startActivity(z);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case "maps":
                Intent j = new Intent(getApplicationContext(), MapsActivity.class);
                j.putExtra("activity","favoritas");
                j.putExtra("lat", latPoint);
                j.putExtra("lng", lngPoint);
                startActivity(j);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

        }
        return;
    }

}
