package com.breja.breja_br.Adapters;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.breja.breja_br.Activities.PromocoesActivity;
import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.Models.PromocoesDenunciadas;
import com.breja.breja_br.Models.PromocoesFavoritas;
import com.breja.breja_br.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PromocoesAdapter extends FirestoreRecyclerAdapter<Promocao, PromocoesAdapter.PromocoesHolder> {
    int denunciar = 0;
    private boolean verifica=false;
    private AlertDialog alerta;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseAuth auth = FirebaseAuth.getInstance();
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
        holder.Local.setText(model.getEstabelecimento());
        holder.Valido.setText("Valido até: "+model.getValidade());
        Picasso.get().load(model.getUriImg()).into(holder.FotoPromocao);

        holder.btn_denunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifica=false;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                //define o titulo
                builder.setTitle(auth.getCurrentUser().getDisplayName());
                //define a mensagem
                builder.setMessage("Você deseja denunciar está promoção?");
                builder.setNegativeButton("não",null);
                builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("Denunciadas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    String email = document.getString("email");
                                    String uriImg =document.getString("uriImg");
                                    if(email.equals(auth.getCurrentUser().getEmail())&& uriImg.equals(model.getUriImg())){
                                        verifica=true;
                                        break;
                                    }

                                }
                                if(verifica == false){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    //define o titulo
                                    builder.setTitle(auth.getCurrentUser().getDisplayName());
                                    //define a mensagem
                                    builder.setMessage("Por qual motivo você deseja denunciar está promoção?");
                                    final EditText input = new EditText(v.getContext());
                                    builder.setView(input);
                                    //define um botão como positivo
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            PromocoesDenunciadas Denunciadas = new PromocoesDenunciadas(auth.getCurrentUser().getEmail(),model.getUriImg(),input.getText().toString());
                                            db.collection("Denunciadas")
                                                    .add(Denunciadas).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
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
                                                                                    .update("denunciar", model.getDenunciar()+1);;
                                                                            Toast.makeText(v.getContext(), "Está promoção foi denunciada com sucesso.", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }
                                            });
                                        }
                                    });
                                    alerta = builder.create();
                                    //Exibe
                                    alerta.show();

                                    Button positive = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                                    positive.setTextColor(Color.parseColor("#FF0B8B42"));

                                }
                                if(verifica==true){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    //define o titulo
                                    builder.setTitle(auth.getCurrentUser().getDisplayName());
                                    //define a mensagem
                                    builder.setMessage("Você já denunciou essa promoção.");
                                    //define um botão como positivo
                                    builder.setPositiveButton("Ok", null);
                                    alerta = builder.create();
                                    //Exibe
                                    alerta.show();
                                    Button positive = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                                    positive.setTextColor(Color.parseColor("#FF0B8B42"));
                                }
                            }
                        });
                    }
                });
                alerta = builder.create();
                //Exibe
                alerta.show();
                Button positive = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setTextColor(Color.parseColor("#FF0B8B42"));
                Button negative = alerta.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setTextColor(Color.parseColor("#FF0000"));

            }

        });
        holder.btn_favoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifica = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                //define o titulo
                builder.setTitle(auth.getCurrentUser().getDisplayName());
                //define a mensagem
                builder.setMessage("Você deseja favoritar essa promoção?");
                builder.setNegativeButton("não",null);
                //define um botão como positivo
                builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PromocoesFavoritas Favoritas =  new PromocoesFavoritas(model.getBeer(),model.getType_beer(),model.getContent(),model.getDescription(),model.getEstabelecimento(),model.getLat(),model.getLng(),model.getValue(),model.getUriImg(),model.getEmail(),auth.getCurrentUser().getEmail(),model.getValidade(),model.getDenunciado());
                        db.collection("Favoritas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    String email = document.getString("email");
                                    String uriImg =document.getString("uriImg");
                                    if(email.equals(Favoritas.getEmail())&& uriImg.equals(Favoritas.getUriImg())){
                                        verifica=true;
                                        break;
                                    }

                                }
                                if(verifica == false){
                                    db.collection("Favoritas")
                                            .add(Favoritas).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                            //define o titulo
                                            builder.setTitle(auth.getCurrentUser().getDisplayName());
                                            //define a mensagem
                                            builder.setMessage("Promoção favoritada com sucesso.");
                                            //define um botão como positivo
                                            builder.setPositiveButton("Ok",null);
                                            alerta = builder.create();
                                            //Exibe
                                            alerta.show();

                                            Button positive = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                                            positive.setTextColor(Color.parseColor("#FF0B8B42"));
                                        }
                                    });
                                }
                                if(verifica==true){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    //define o titulo
                                    builder.setTitle(auth.getCurrentUser().getDisplayName());
                                    //define a mensagem
                                    builder.setMessage("Você já favoritou essa promoção.");
                                    //define um botão como positivo
                                    builder.setPositiveButton("Ok", null);
                                    alerta = builder.create();
                                    //Exibe
                                    alerta.show();
                                    Button positive = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                                    positive.setTextColor(Color.parseColor("#FF0B8B42"));
                                }
                            }
                        });

                    }
                });
                alerta = builder.create();
                //Exibe
                alerta.show();
                Button positive = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setTextColor(Color.parseColor("#FF0B8B42"));
                Button negative = alerta.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setTextColor(Color.parseColor("#FF0000"));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PromocoesActivity.class);
                i.putExtra("produto",model.getBeer()+" "+model.getType_beer()+" "+model.getContent());
                i.putExtra("estabelecimento",model.getEstabelecimento());
                i.putExtra("image",model.getUriImg());
                i.putExtra("lat",model.getLat());
                i.putExtra("lng",model.getLng());
                i.putExtra("valor",model.getValue());
                i.putExtra("curtir",model.getCurtir());
                Toast.makeText(v.getContext(),""+model.getCurtir(),Toast.LENGTH_SHORT).show();
                i.putExtra("activity","main");
                v.getContext().startActivity(i);
            }
        });

    }

    @NonNull
    @Override
    public PromocoesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.promocao,viewGroup,false);
        return new PromocoesHolder(v);
    }
    class PromocoesHolder extends RecyclerView.ViewHolder {
        ImageView FotoPromocao;
        TextView Produto;
        TextView Descricao;
        TextView Valor;
        TextView Local;
        TextView Valido;
        Button btn_denunciar;
        Button btn_favoritar;

        public PromocoesHolder(@NonNull View itemView) {
            super(itemView);
            FotoPromocao = itemView.findViewById(R.id.imgPromocao);
            Produto = itemView.findViewById(R.id.txtNomeProdPromo);
            Descricao = itemView.findViewById(R.id.txtDescricaoProdPromo);
            Valor = itemView.findViewById(R.id.txtPrecoPromo);
            Local = itemView.findViewById(R.id.txtEstabPromo);
            Valido = itemView.findViewById(R.id.ValidadePromocao);
            btn_denunciar = itemView.findViewById(R.id.btn_denunciar);
            btn_favoritar = itemView.findViewById(R.id.btn_excluir_coment);
        }
    }
}