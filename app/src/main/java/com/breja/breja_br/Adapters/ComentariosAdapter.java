package com.breja.breja_br.Adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.breja.breja_br.Models.Comentarios;
import com.breja.breja_br.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class ComentariosAdapter extends FirestoreRecyclerAdapter<Comentarios, ComentariosAdapter.ComentariosHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ComentariosAdapter(@NonNull FirestoreRecyclerOptions<Comentarios> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ComentariosAdapter.ComentariosHolder holder, final int position, @NonNull final Comentarios model) {
        if (model.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            holder.deletar.setVisibility(View.VISIBLE);
        }
        holder.useremail.setText(model.getEmail());
    holder.comentario.setText(model.getComentario());
        Picasso.get().load(model.getUserFoto()).into(holder.fotousuario);
        holder.deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Comentarios")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()){
                            if(model.getEmail().equals(document.get("email"))&&model.getComentario().equals(document.get("comentario"))&&model.getUserFoto().equals(document.get("userFoto"))){
                                document.getId();
                                db.collection("Comentarios")
                                        .document(document.getId())
                                        .delete();
                            }
                        }
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public ComentariosAdapter.ComentariosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coment,viewGroup,false);
        return new ComentariosAdapter.ComentariosHolder(v);
    }

    class ComentariosHolder extends RecyclerView.ViewHolder{
        ImageView fotousuario;
        TextView useremail;
        TextView comentario;
        Button deletar;
        public ComentariosHolder(@NonNull View itemView) {
            super(itemView);
            fotousuario=itemView.findViewById(R.id.txt_foto_user);
            useremail=itemView.findViewById(R.id.txt_user);
            comentario=itemView.findViewById(R.id.txt_comentario);
            deletar=itemView.findViewById(R.id.btn_excluir_coment);
            deletar.setVisibility(View.INVISIBLE);
        }
    }
}


