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

import com.breja.breja_br.R;
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
    TextView textView_cadastro_estabelecimento;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().hide();

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_perfil);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.getMenu();

        textView_minhas_promocoes = findViewById(R.id.textView_minhas_promocoes);
        textView_cadastro_cerveja = findViewById(R.id.textView_cadastrar_cerveja);
        textView_cadastro_tipo = findViewById(R.id.textView_cadastrar_tipo);
        textView_cadastro_estabelecimento = findViewById(R.id.textView_cadastrar_estabelecimento);
        imageView_foto_perfil = findViewById(R.id.imageView_foto_perfil);
        button_logout = findViewById(R.id.button_logout);
        textView_nome_usuario = findViewById(R.id.TextView_nome_usuario);
        Uri imageUri = mAuth.getCurrentUser().getPhotoUrl();
        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(imageView_foto_perfil);
        textView_nome_usuario.setText(mAuth.getCurrentUser().getDisplayName());

        textView_cadastro_cerveja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CadastroCervejasActivity.class));
                finish();
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
                startActivity(new Intent(getApplicationContext(),CadastroTipo.class));
            }
        });
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mAuth.signOut();
              finish();
              startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        textView_cadastro_estabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CadastroEstabelecimentosActivity.class));
            }
        });

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

            case R.id.navigation_add_promo:
                startActivity(new Intent(getApplicationContext(), CadastroPromocoesActivity.class));
                break;

            case R.id.navigation_map:
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                break;

        }
        return true;
    }
}
