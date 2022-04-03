package hu.petrik.soltisoma_restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnListazas, btnUj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnListazas.setOnClickListener(view -> {
            Intent listazasra = new Intent(MainActivity.this, ListResultActivity.class);
            startActivity(listazasra);
            finish();

        });
        btnUj.setOnClickListener(view -> {
            Intent felvetelre = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(felvetelre);
            finish();
        });
    }

    private void init() {
        btnListazas = findViewById(R.id.btnListazas);
        btnUj = findViewById(R.id.btnUj);
    }
}