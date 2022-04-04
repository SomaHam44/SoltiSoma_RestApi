package hu.petrik.soltisoma_restapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import hu.petrik.soltisoma_restapi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private Button btnListazas, btnUj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnListazas.setOnClickListener(view -> {
            Intent listazasra = new Intent(MainActivity.this, ListResultActivity.class);
            startActivity(listazasra);
            finish();
        });

        binding.btnUj.setOnClickListener(view -> {
            Intent felvetelre = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(felvetelre);
            finish();
        });
    }
}