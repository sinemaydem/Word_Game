package com.example.wordlegamemobilodev;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Objects;

public class KayitSayfasi extends AppCompatActivity {
    FirebaseAuth myAuth;
    DatabaseReference myref;
    FirebaseUser myuser;
    HashMap<String,Object> mydata;
    EditText emailText,nameText,passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_sayfasi);


        emailText = findViewById(R.id.EmailText);
        nameText = findViewById(R.id.nameText);
        passwordEditText = findViewById(R.id.password);

        ProgressBar loadingProgressBar = findViewById(R.id.loading);

        myAuth=FirebaseAuth.getInstance();
        myref= FirebaseDatabase.getInstance().getReference();

    }




    @SuppressLint("SetTextI18n")
    public void Kaydol(View view) {
        String txtemail=emailText.getText().toString();
        String txtad=nameText.getText().toString();
        String txtsifre=passwordEditText.getText().toString();

        if(txtemail.isEmpty()){
            emailText.setText("Email Boş OLamaz");
            emailText.requestFocus();
        }
        if(txtad.isEmpty()){
            nameText.setText("Ad Boş OLamaz");
            nameText.requestFocus();
        }
        if(txtsifre.isEmpty()){
            passwordEditText.setText("Şifre Boş OLamaz");
            passwordEditText.requestFocus();
        }
        else{
            myAuth.createUserWithEmailAndPassword(txtemail,txtsifre)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            myuser=myAuth.getCurrentUser();
                            mydata=new HashMap<>();
                            mydata.put("KullaniciMail",txtemail);
                            mydata.put("KullaniciAdi",txtad);
                            mydata.put("KullaniciSifre",txtsifre);
                            myref.child("Kullanicilar").child(myuser.getUid()).setValue(mydata)
                                    .addOnCompleteListener(KayitSayfasi.this,task1 -> {
                                        if (task1.isSuccessful()){
                                            Toast.makeText(KayitSayfasi.this, "Kullanıcı Kayıt edildi", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(KayitSayfasi.this,Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
        }
    }
}
