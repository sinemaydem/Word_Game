package com.example.wordlegamemobilodev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;


public class LoginSayfasi extends AppCompatActivity {

    EditText email,sifre;
    FirebaseAuth myAuth;
    Button btngiris,btnkaydol;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sayfasi);

        email=findViewById(R.id.EmailText);
        sifre=findViewById(R.id.sifre);
        btnkaydol=findViewById(R.id.Kayitsayfasi);
        btngiris=findViewById(R.id.login);

        btnkaydol.setOnClickListener(view -> startActivity(new Intent(LoginSayfasi.this,KayitSayfasi.class)));
            myAuth=FirebaseAuth.getInstance();
            myref= FirebaseDatabase.getInstance().getReference();
    }



    public void Giris(View view) {
        String txtEmail=email.getText().toString();
        String txtpass=sifre.getText().toString();
        myAuth.signInWithEmailAndPassword(txtEmail, txtpass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginSayfasi.this, "Giris Başarılı", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = myAuth.getCurrentUser();
                        assert user != null;
                        myref=FirebaseDatabase.getInstance().getReference("Kullanicilar").child(user.getUid());
                        myref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String name= (snapshot.child("KullaniciAdi").getValue()).toString();
                                startActivity(new Intent(getApplicationContext(),Baslangic.class).putExtra("KullaniciAdi",name));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(LoginSayfasi.this, "Adi çekilmedi", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(LoginSayfasi.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                    }
                });


        }



}