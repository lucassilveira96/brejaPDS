package com.breja.breja_br.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.breja.breja_br.Models.Promocao;
import com.breja.breja_br.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import static com.breja.breja_br.R.id.navigation_add_promo;


public class CadastroPromocoesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;
    ImageView imageView ;
    Spinner spinner_beers;
    Spinner spinner_type_beer;
    Spinner spinner_content;
    Spinner spinner_places;
    Button btn_add_promocao;
    EditText editText_descricao;
    EditText editText_valor;
    EditText editText_validade;
    Uri imagem;
    Bitmap img;
    double lat=0;
    double lng=0;
    double latPoint;
    double lngPoint;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_promocoes);

        getSupportActionBar().hide();
        getBeers();
        getTypeBeers();
        getContent();
        getPlaces();

        Intent intent = getIntent();
        Bundle i = intent.getExtras();
        latPoint = i.getDouble("lat");
        lngPoint = i.getDouble("lng");

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_add_promo);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.getMenu();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }
        btn_add_promocao = findViewById(R.id.btn_cadastrar_promocao);
        spinner_content = findViewById(R.id.spinner_content);
        spinner_type_beer = findViewById(R.id.spinner_type_beer);
        spinner_beers = findViewById(R.id.spinner_beer);
        spinner_places = findViewById(R.id.spinner_estabelecimento);
        editText_descricao = findViewById(R.id.editText_description);
        editText_valor = findViewById(R.id.editText_valor);
        imageView = findViewById(R.id.imageView);
        editText_validade = findViewById(R.id.editText_validade);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editText_validade,smf);
        editText_validade.addTextChangedListener(mtw);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarfoto();
            }
        });


        btn_add_promocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri downloadUri;
                mStorageRef = FirebaseStorage.getInstance().getReference();
                getPlaces(spinner_places.getSelectedItem().toString());
                String urlImg = "images/" + UUID.randomUUID() + ".jpg";
                final StorageReference imagemReferencia = mStorageRef.child(urlImg);
                Bitmap bitmap = img;
                final double valor=Double.parseDouble(editText_valor.getText().toString());

                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                byte [] data = byteArray.toByteArray();

                UploadTask uploadTask = imagemReferencia.putBytes(data);

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        else{
                            return imagemReferencia.getDownloadUrl();
                        }

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Promocao novapromocao = new Promocao(spinner_beers.getSelectedItem().toString(),spinner_type_beer.getSelectedItem().toString(),spinner_content.getSelectedItem().toString(),editText_descricao.getText().toString(),spinner_places.getSelectedItem().toString(),lat,lng,valor,downloadUri.toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(),editText_validade.getText().toString(),0);
                            db.collection("Promotion")
                                    .add(novapromocao)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(getApplicationContext(), "adicionado com sucesso", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            i.putExtra("lat", latPoint);
                                            i.putExtra("lng", lngPoint);
                                            startActivity(i);
                                            getPlaces(spinner_places.getSelectedItem().toString());
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
        });

    }
    public void tirarfoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && requestCode == RESULT_FIRST_USER){
            String image = null;
            Bundle extras = data.getExtras();
            img= (Bitmap) extras.get("data");
            imagem=Uri.parse(image=img.toString());
            imageView.setImageBitmap(img);
        }
        else startActivity(new Intent(getApplicationContext(),CadastroPromocoesActivity.class));

    }
    public void getBeers(){
        final ArrayList<String>list=new ArrayList<>();
        db.collection("Beers")
                .whereEqualTo("released",true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getString("name_beer")+" "+document.getString("type_beer"));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"deu pau",Toast.LENGTH_SHORT).show();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinner_beers.setAdapter(adapter);


                    }
                });

    }
    public void getTypeBeers(){
        final ArrayList<String>list=new ArrayList<>();
        db.collection("TypeBeers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getString("tipo"));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"deu pau",Toast.LENGTH_SHORT).show();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinner_type_beer.setAdapter(adapter);


                    }
                });

    }
    public void getContent(){
        final ArrayList<String>list=new ArrayList<>();
        db.collection("Content")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getString("qtd"));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"deu pau",Toast.LENGTH_SHORT).show();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinner_content.setAdapter(adapter);


                    }
                });

    }
    public void getPlaces(){
        final ArrayList<String>list=new ArrayList<>();
        db.collection("Places")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getString("estabelecimento"));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"erro ao realizar a busca no banco de dados",Toast.LENGTH_SHORT).show();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinner_places.setAdapter(adapter);


                    }
                });
    }
    public void getPlaces(String estabelecimento){
        final ArrayList<String>list=new ArrayList<>();
        db.collection("Places")
                .whereEqualTo("estabelecimento",estabelecimento)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lat=document.getDouble("lat");
                                lng=document.getDouble("lng");
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"erro ao realizar a busca no banco de dados",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("lat", latPoint);
                    i.putExtra("lng", lngPoint);
                    startActivity(i);
                    break;
                case R.id.navigation_perfil:
                    Intent x = new Intent(getApplicationContext(), PerfilActivity.class);
                    x.putExtra("lat", latPoint);
                    x.putExtra("lng", lngPoint);
                    startActivity(x);
                    break;

                case navigation_add_promo:
                    Intent z = new Intent(getApplicationContext(), CadastroPromocoesActivity.class);
                    z.putExtra("lat", latPoint);
                    z.putExtra("lng", lngPoint);
                    startActivity(z);
                    break;

                case R.id.navigation_map:
                    Intent m = new Intent(getApplicationContext(), MapsActivity.class);
                    m.putExtra("lat", latPoint);
                    m.putExtra("lng", lngPoint);
                    startActivity(m);
                    break;
                case R.id.navigation_favoritos:
                    Intent t = new Intent(getApplicationContext(), PromocoesFavoritasActivity.class);
                    t.putExtra("lat", latPoint);
                    t.putExtra("lng", lngPoint);
                    startActivity(t);
                    break;

            }
        return true;
    }
}



