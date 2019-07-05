package com.breja.breja_br.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.breja.breja_br.Adapters.ComentariosAdapter;
import com.breja.breja_br.Adapters.PromocoesAdapter;
import com.breja.breja_br.Models.Comentarios;
import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ComentariosActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ComentariosAdapter adapter;
    EditText comentario;
    FloatingActionButton enviar;
    String fotopromocao;
    String fotousuario;
    private StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        fotopromocao=i.get("uriImg").toString();
        comentario = findViewById(R.id.editText_comentario);
        enviar=findViewById(R.id.btn_comentar);
        setUpRecyclerView();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comentario.getText().length() == 0) {
                    comentario.setError("favor inserir um comentario");
                } else {
                    Comentarios comentarios = new Comentarios(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),fotopromocao,comentario.getText().toString());
                    db.collection("Comentarios")
                            .add(comentarios).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                        }
                    });
                }
            }
        });
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
        Query query = db.collection("Comentarios")
                .whereEqualTo("fotopromocao",fotopromocao);
        FirestoreRecyclerOptions<Comentarios> options = new FirestoreRecyclerOptions.Builder<Comentarios>()
                .setQuery(query,Comentarios.class)
                .build();

        adapter = new ComentariosAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.rec_coment);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}
