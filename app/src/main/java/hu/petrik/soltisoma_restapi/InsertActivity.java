package hu.petrik.soltisoma_restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import hu.petrik.soltisoma_restapi.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {
   ActivityInsertBinding binding;
    private String url = "http://10.0.2.2:8000/api/cities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnVissza.setOnClickListener(view -> {
            Intent vissza = new Intent(InsertActivity.this, MainActivity.class);
            startActivity(vissza);
            finish();
        });
        binding.btnFelvetel.setOnClickListener(view -> {
            varosHozzadasa();
        });
    }

    private boolean validacio(String nev, String orszag, String lakossagString) {
        if (nev.isEmpty()) {
            Toast.makeText(this, "Név megadása kötelező", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (orszag.isEmpty()) {
            Toast.makeText(this, "Ország megadása kötelező", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lakossagString.isEmpty()) {
            Toast.makeText(this, "Lakosság megadása kötelező", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nev.isEmpty() && orszag.isEmpty() && lakossagString.isEmpty()) {
            binding.btnFelvetel.setEnabled(false);
            return false;
        }
        binding.btnFelvetel.setEnabled(true);
        return true;
    }

    private void alaphelyzet() {
        binding.editNev.setText("");
        binding.editOrszag.setText("");
        binding.editLakossag.setText("");
    }

    private void varosHozzadasa() {
        String nev = binding.editNev.getText().toString().trim();
        String orszag = binding.editOrszag.getText().toString().trim();
        String lakossagString = binding.editLakossag.getText().toString().trim();
        if (!validacio(nev, orszag, lakossagString)) {
            return;
        }
        int lakossag = Integer.parseInt(lakossagString);
        City city = new City(0, nev, orszag, lakossag);
        Gson jsonConverter = new Gson();
        CityTask task = new CityTask(url, "POST", jsonConverter.toJson(city));
        task.execute();
    }

    private class CityTask extends AsyncTask<Void, Void, Response> {
        String requestUrl;
        String requestType;
        String requestParams;

        public CityTask(String requestUrl, String requestType, String requestParams) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
            this.requestParams = requestParams;
        }

        public CityTask(String requestUrl, String requestType) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                response = RequestHandler.post(requestUrl, requestParams);
            } catch (IOException e) {
                runOnUiThread(() -> Toast.makeText(InsertActivity.this, "Sikertelen felvétel", Toast.LENGTH_SHORT).show());
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson converter = new Gson();
            if (response.getResponseCode() >= 400){
                Toast.makeText(InsertActivity.this, "Hiba történt a kérés feldolgozása során", Toast.LENGTH_SHORT).show();
            }
            else {
                City city = converter.fromJson(response.getContent(), City.class);
                ListResultActivity.cities.add(0, city);
                alaphelyzet();
            }
        }
    }

}