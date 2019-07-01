package com.breja.breja_br.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.breja.breja_br.Models.TipoCervejas;
import com.breja.breja_br.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroTipo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tipo);
        final EditText editText_type_beer = findViewById(R.id.editText_value_beer);
        FloatingActionButton btn_cadastrar = findViewById(R.id.btn_cadastrar);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        double lat = i.getDouble("lat");
        double lng = i.getDouble("lng");
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tipobeer = editText_type_beer.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                TipoCervejas tipo = new TipoCervejas(editText_type_beer.getText().toString().toUpperCase());

                if (tipobeer != "") {
                    db.collection("TypeBeers")
                            .add(tipo)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "adicionado com sucesso", Toast.LENGTH_SHORT).show();
                                    Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                                    x.putExtra("lat",lat);
                                    x.putExtra("lng",lng);
                                    startActivity(x);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "erro ao adicionar", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    editText_type_beer.setError("favor inserir o nome do tipo");
                }
            }
        });

    }
}
