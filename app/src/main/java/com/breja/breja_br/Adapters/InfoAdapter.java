package com.breja.breja_br.Adapters;

import android.content.Context;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.breja.breja_br.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class InfoAdapter implements GoogleMap.InfoWindowAdapter {
    private View view;
    private String foto;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public double lat;
    public double lon;

    @Override
    public View getInfoWindow(Marker marker) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_infowindow, null);
        renderInfo(marker);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderInfo(marker);
        return view;
    }


    public InfoAdapter(Context mContext) {
        context = mContext;

    }

    private void renderInfo(Marker marker) {
        lat = marker.getPosition().latitude;
        lon = marker.getPosition().longitude;
        foto=marker.getTitle();
        String [] text =foto.split("&#");
        TextView txt_places = view.findViewById(R.id.txt_places_maps);
        TextView txt_produto_maps = view.findViewById(R.id.txt_produto_maps);
        TextView txt_valor_maps = view.findViewById(R.id.txt_valor_maps);
        ImageView imageView_promocao =view.findViewById(R.id.img_fp);
        txt_places.setText(text[2]);
        txt_produto_maps.setText(text[0]);
        txt_valor_maps.setText(marker.getSnippet());
        Picasso.get().load(text[1]).into(imageView_promocao);

    }

}