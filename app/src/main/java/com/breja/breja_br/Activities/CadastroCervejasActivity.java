package com.breja.breja_br.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.breja.breja_br.Models.Cervejas;
import com.breja.breja_br.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroCervejasActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cervejas);
        getSupportActionBar().hide();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button btn = findViewById(R.id.btn_add_beer);
        final EditText edt_name_beer = findViewById(R.id.edt_name_beer);
        final EditText edt_alcohol_content = findViewById(R.id.edt_alcohol_content);
        final Spinner spinner_type = findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_beers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_beer = edt_name_beer.getText().toString();
                String alcohol_content = edt_alcohol_content.getText().toString();
                String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String type_beer = spinner_type.getSelectedItem().toString();
                if (name_beer.length() == 0) {
                    edt_name_beer.setError("Digite o nome da cerveja!");
                } else if (alcohol_content.length() == 0) {
                    edt_alcohol_content.setError("Digite o teor alcoólico cerveja!");
                } else {
                    Cervejas beer = new Cervejas(name_beer.toUpperCase(), alcohol_content, email, Uid, type_beer);
                    db.collection("Beers")
                            .add(beer)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "adicionado com sucesso", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "erro ao adicionar", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }



}


