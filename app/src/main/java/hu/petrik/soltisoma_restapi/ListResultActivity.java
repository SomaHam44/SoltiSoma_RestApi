package hu.petrik.soltisoma_restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListResultActivity extends AppCompatActivity {
    private TextView textViewAdatok;
    private Button btnVissza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        init();
        btnVissza.setOnClickListener(view -> {
            Intent vissza = new Intent(ListResultActivity.this, MainActivity.class);
            startActivity(vissza);
            finish();

        });
    }

    private void init() {
        textViewAdatok = findViewById(R.id.textviewOrszag);
        btnVissza = findViewById(R.id.btnVisszafele);
    }
}