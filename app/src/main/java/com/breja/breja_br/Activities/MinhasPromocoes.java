package com.breja.breja_br.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.breja.breja_br.Adapters.MinhasPromocoesAdapter;
import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MinhasPromocoes extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MinhasPromocoesAdapter adapter;
    final double latPoint = 0;
    final double lngPoint = 0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_promocoes);

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
        Query query= db.collection("Promotion")
                .whereEqualTo("email",mAuth.getCurrentUser().getEmail());
        FirestoreRecyclerOptions<Promocao> options = new FirestoreRecyclerOptions.Builder<Promocao>()
                .setQuery(query,Promocao.class)
                .build();

        adapter = new MinhasPromocoesAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

}

