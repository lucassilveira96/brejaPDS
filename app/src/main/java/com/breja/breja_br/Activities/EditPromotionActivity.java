package com.breja.breja_br.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.breja.breja_br.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class EditPromotionActivity extends AppCompatActivity {
    ImageView imageView_foto_promocao;
    EditText edt_value;
    TextView txt_estabelecimento;
    TextView txt_produto;
    Button edit;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion);
        imageView_foto_promocao=findViewById(R.id.img_foto_promocao);
        edt_value = findViewById(R.id.editText_value_beer);
        txt_estabelecimento = findViewById(R.id.txt_etb);
        txt_produto = findViewById(R.id.txt_pdt);
        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        String id = i.getString("foto");
        Picasso.get().load(id).into(imageView_foto_promocao);
        edt_value.setText("R$ "+ i.get("valor"));
        txt_produto.setText(i.get("produto").toString());
        txt_estabelecimento.setText(i.get("estabelecimento").toString());
        edit = findViewById(R.id.btn_edit_promotion);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(edt_value.getText().toString());
                db.collection("Promotion")
                        .whereEqualTo("uriImg",i.get("foto"))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("Promotion")
                                                .document(document.getId())
                                                .update("value",value);
                                        Toast.makeText(getApplicationContext(),"valor da promoção editado",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),MinhasPromocoes.class));
                                    }

                                } else {

                                }



                            }
                        });
            }
        });
    }
}