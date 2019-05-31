package com.breja.breja_br.Adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PromocoesAdapter extends FirestoreRecyclerAdapter<Promocao, PromocoesAdapter.PromocoesHolder> {
    int denunciar = 0;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public PromocoesAdapter(@NonNull FirestoreRecyclerOptions<Promocao> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull final PromocoesHolder holder, final int position, @NonNull final Promocao model) {
        denunciar =model.getDenunciar();
        Uri uri = Uri.parse(Uri.decode(model.getUriImg()));
        holder.Produto.setText(model.getBeer()+" "+model.getType_beer()+" "+model.getContent());
        holder.Valor.setText("R$ "+model.getValue());
        holder.Descricao.setText(model.getDescription());
        holder.Local.setText("Mercado Teste");
        holder.Valido.setText("Valido até:"+"25/06/2019");
        Picasso.get().load(model.getUriImg()).into(holder.FotoPromocao);

        holder.btn_denunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denunciar++;
                db.collection("Promotion")
                    .whereEqualTo("uriImg",model.getUriImg())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("Promotion")
                                                .document(document.getId())
                                                .update("denunciado",denunciar);
                                        Promocao promocao= new Promocao();
                                        promocao.setDenunciar(denunciar);
                                    }
                                } else {

                                }



                            }
                        });
            }
        });

    }

    @NonNull
    @Override
    public PromocoesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.promocao,viewGroup,false);
        return new PromocoesHolder(v);
    }

    class PromocoesHolder extends RecyclerView.ViewHolder{
        ImageView FotoPromocao;
        TextView Produto;
        TextView Descricao;
        TextView Valor;
        TextView Local;
        TextView Valido;
        Button btn_denunciar;
        public PromocoesHolder(@NonNull View itemView) {
            super(itemView);
            FotoPromocao = itemView.findViewById(R.id.imgPromocao);
            Produto = itemView.findViewById(R.id.txtNomeProdPromo);
            Descricao = itemView.findViewById(R.id.txtDescricaoProdPromo);
            Valor = itemView.findViewById(R.id.txtPrecoPromo);
            Local = itemView.findViewById(R.id.txtEstabPromo);
            Valido = itemView.findViewById(R.id.ValidadePromocao);
            btn_denunciar=itemView.findViewById(R.id.btn_denunciar);

        }
    }
}