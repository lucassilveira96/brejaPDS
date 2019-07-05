package com.breja.breja_br.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.breja.breja_br.Activities.EditPromotionActivity;
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

public class MinhasPromocoesAdapter extends FirestoreRecyclerAdapter<Promocao, MinhasPromocoesAdapter.PromocoesHolder> {
    int denunciar = 0;
    private AlertDialog alerta;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;
    public MinhasPromocoesAdapter(@NonNull FirestoreRecyclerOptions<Promocao> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull final PromocoesHolder holder, final int position, @NonNull final Promocao model) {
        denunciar =model.getDenunciar();
        if(model.getDenunciar()<6){
            holder.btn_alert.setVisibility(View.INVISIBLE);
        }
        if(model.getDenunciar()>=6){
            holder.btn_alert.setVisibility(View.VISIBLE);
        }
        Uri uri = Uri.parse(Uri.decode(model.getUriImg()));
        holder.Produto.setText(model.getBeer()+" "+model.getType_beer()+" "+model.getContent());
        holder.Valor.setText("R$ "+model.getValue());
        holder.Descricao.setText(model.getDescription());
        holder.Local.setText(model.getEstabelecimento());
        holder.Valido.setText("Valido até: "+model.getValidade());
        Picasso.get().load(model.getUriImg()).into(holder.FotoPromocao);
        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Você deseja excluir essa promoção?");
                //define um botão como positivo
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("Promotion").whereEqualTo("uriImg", model.getUriImg())
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    db.collection("Promotion")
                                            .document(document.getId())
                                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });

                                }
                            }
                        });
                    }

                });
                alerta = builder.create();
                //Exibe
                alerta.show();
                Button positive = alerta.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                positive.setTextColor(Color.parseColor("#FF0B8B42"));

            }
            });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditPromotionActivity.class);
                i.putExtra("foto",model.getUriImg());
                i.putExtra("produto",model.getBeer()+" "+model.getType_beer()+" "+model.getContent());
                i.putExtra("valor",model.getValue());
                i.putExtra("estabelecimento",model.getEstabelecimento());
                v.getContext().startActivity(i);
            }
        });
        holder.btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Sua promoção foi denunciada pelos seguintes motivos");
                //define um botão como positivo
                builder.setPositiveButton("Ok", null);
                builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(v.getContext(), EditPromotionActivity.class);
                        i.putExtra("foto",model.getUriImg());
                        i.putExtra("produto",model.getBeer()+" "+model.getType_beer()+" "+model.getContent());
                        i.putExtra("valor",model.getValue());
                        i.putExtra("estabelecimento",model.getEstabelecimento());
                        i.putExtra("denunciado",true);
                        v.getContext().startActivity(i);
                    }
                });
                alerta = builder.create();
                //Exibe
                alerta.show();
                Button positive = alerta.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                positive.setTextColor(Color.parseColor("#FF0B8B42"));
                Button negative = alerta.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE);
                negative.setTextColor(Color.parseColor("#FF0000"));
            }
        });

    }

    @NonNull
    @Override
    public PromocoesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.minhapromocao,viewGroup,false);
        return new PromocoesHolder(v);
    }

    class PromocoesHolder extends RecyclerView.ViewHolder{
        ImageView FotoPromocao;
        TextView Produto;
        TextView Descricao;
        TextView Valor;
        TextView Local;
        TextView Valido;
        Button excluir;
        Button btn_alert;
        public PromocoesHolder(@NonNull View itemView) {
            super(itemView);
            FotoPromocao = itemView.findViewById(R.id.imgPromocao);
            Produto = itemView.findViewById(R.id.txtNomeProdPromo);
            Descricao = itemView.findViewById(R.id.txtDescricaoProdPromo);
            Valor = itemView.findViewById(R.id.txtPrecoPromo);
            Local = itemView.findViewById(R.id.txtEstabPromo);
            Valido = itemView.findViewById(R.id.ValidadePromocao);
            excluir = itemView.findViewById(R.id.btn_excluir_coment);
            btn_alert = itemView.findViewById(R.id.btn_alert);

        }
    }
}