package hu.petrik.soltisoma_restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {
    private EditText editNev;
    private EditText editOrszag;
    private EditText editLakossag;
    private Button btnFelvesz;
    private Button btnVissza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
        btnVissza.setOnClickListener(view -> {
            Intent vissza = new Intent(InsertActivity.this, MainActivity.class);
            startActivity(vissza);
            finish();
        });
    }

    private void init() {
        editNev = findViewById(R.id.editNev);
        editOrszag = findViewById(R.id.editOrszag);
        editLakossag = findViewById(R.id.editLakossag);
        btnFelvesz = findViewById(R.id.btnFelvetel);
        btnVissza = findViewById(R.id.btnVissza);
    }
}