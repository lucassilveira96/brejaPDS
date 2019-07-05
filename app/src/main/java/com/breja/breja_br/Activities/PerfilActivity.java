package com.breja.breja_br.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.breja.breja_br.Models.PromocoesFavoritas;
import com.breja.breja_br.R;
import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import static com.breja.breja_br.R.id.navigation_add_promo;

public class PerfilActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView navigationView;
    Button button_logout;
    TextView textView_minhas_promocoes;
    TextView textView_cadastro_cerveja;
    TextView textView_cadastro_tipo;
    ImageView imageView_foto_perfil;
    TextView textView_nome_usuario;
    double latPoint;
    double lngPoint;
    String activity;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        activity = i.getString("activity");
        latPoint = i.getDouble("lat");
        lngPoint = i.getDouble("lng");

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_perfil);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.getMenu();

        textView_minhas_promocoes = findViewById(R.id.textView_minhas_promocoes);
        textView_cadastro_cerveja = findViewById(R.id.textView_cadastrar_cerveja);
        textView_cadastro_tipo = findViewById(R.id.textView_cadastrar_tipo);
        imageView_foto_perfil = findViewById(R.id.img_foto_promocao);
        button_logout = findViewById(R.id.button_logout);
        textView_nome_usuario = findViewById(R.id.TextView_nome_usuario);
        Uri imageUri = mAuth.getCurrentUser().getPhotoUrl();
        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(imageView_foto_perfil);
        textView_nome_usuario.setText(mAuth.getCurrentUser().getDisplayName());

        textView_cadastro_cerveja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getApplicationContext(), CadastroCervejasActivity.class);
                z.putExtra("lat",latPoint);
                z.putExtra("lng",lngPoint);
                startActivity(z);
            }
        });
        textView_minhas_promocoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MinhasPromocoes.class));
            }
        });
        textView_cadastro_tipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(getApplicationContext(), CadastroTipo.class);
                z.putExtra("lat",latPoint);
                z.putExtra("lng",lngPoint);
                startActivity(z);
            }
        });
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("activity","perfil");
                i.putExtra("lat",latPoint);
                i.putExtra("lng",lngPoint);
                startActivity(i);
                break;
            case R.id.navigation_perfil:
                Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                x.putExtra("activity","perfil");
                x.putExtra("lat",latPoint);
                x.putExtra("lng",lngPoint);
                startActivity(x);
                break;

            case navigation_add_promo:
                Intent z = new Intent(getApplicationContext(), CadastroPromocoesActivity.class);
                z.putExtra("activity","perfil");
                z.putExtra("lat",latPoint);
                z.putExtra("lng",lngPoint);
                startActivity(z);
                break;

            case R.id.navigation_map:
                Intent m = new Intent(getApplicationContext(), MapsActivity.class);
                m.putExtra("activity","perfil");
                m.putExtra("lat",latPoint);
                m.putExtra("lng",lngPoint);
                startActivity(m);
                break;
            case R.id.navigation_favoritos:
                Intent t = new Intent(getApplicationContext(), PromocoesFavoritasActivity.class);
                t.putExtra("activity","perfil");
                t.putExtra("lat",latPoint);
                t.putExtra("lng",lngPoint);
                startActivity(t);
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home://ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                switch (activity) {
                    case "main":
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("activity","perfil");
                        i.putExtra("lat", latPoint);
                        i.putExtra("lng", lngPoint);
                        startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    case "maps":
                        Intent x = new Intent(getApplicationContext(), MapsActivity.class);
                        x.putExtra("activity","perfil");
                        x.putExtra("lat", latPoint);
                        x.putExtra("lng", lngPoint);
                        startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                        break;
                    case "favoritas":
                        Intent z = new Intent(getApplicationContext(), PerfilActivity.class);
                        z.putExtra("activity","perfil");
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
                i.putExtra("activity","perfil");
                i.putExtra("lat", latPoint);
                i.putExtra("lng", lngPoint);
                startActivity(i);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case "maps":
                Intent x = new Intent(getApplicationContext(), Maps.class);
                x.putExtra("activity","perfil");
                x.putExtra("lat", latPoint);
                x.putExtra("lng", lngPoint);
                startActivity(x);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            case "favoritas":
                Intent z = new Intent(getApplicationContext(), PromocoesFavoritasActivity.class);
                z.putExtra("activity","perfil");
                z.putExtra("lat", latPoint);
                z.putExtra("lng", lngPoint);
                startActivity(z);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

        }
        return;
    }
}
