package com.example.wordlegamemobilodev;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Baslangic extends AppCompatActivity {


    private String name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baslangic);

        TextView nameView = findViewById(R.id.nameView);
        Intent gelenAd= getIntent();
        name=gelenAd.getStringExtra("KullaniciAdi");
        nameView.setText(name);
        FirebaseAuth myAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = myAuth.getCurrentUser();
        assert currentUser != null;
    }
    public void clickStartBtn(View view) {
        startActivity(new Intent(getApplicationContext(),gameActivity.class).putExtra("KullaniciAdi",name));
    }
    public void clickLastGameBtn(View view) {
        startActivity(new Intent(getApplicationContext(),gameActivity.class));

    }
    public void clickHighScoreBtn(View view) {
        startActivity(new Intent(getApplicationContext(),scoreActivity.class).putExtra("KullaniciAdi",name));
    }
    public void clickExitBtn(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(Baslangic.this);
        builder.setTitle("❌ Exit");
        builder.setMessage("Oyundan çikmak ister misin ?");
        builder.setPositiveButton("Evet", (dialogInterface, i) -> {
            Toast.makeText(Baslangic.this,"Görüsürüz!",Toast.LENGTH_LONG).show();
            finish();
        });
        builder.setNegativeButton("Hayir", (dialogInterface, i) -> Toast.makeText(Baslangic.this,"Hadi Bir oyun oynayalim",Toast.LENGTH_LONG).show());
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}