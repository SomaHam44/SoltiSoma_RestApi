package hu.petrik.soltisoma_restapi;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.petrik.soltisoma_restapi.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {
   ActivityInsertBinding binding;
    //private String url = "http://10.0.2.2:8000/api/cities";
    private String url = "https://retoolapi.dev/jSLi3M/varosok";
    private int[] gifek = new int[]{R.drawable.gif1,
            R.drawable.gif2, R.drawable.gif3, R.drawable.gif4, R.drawable.gif5,
            R.drawable.gif6, R.drawable.gif7, R.drawable.gif8, R.drawable.gif9,
            R.drawable.gif10};
    private Random random;

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
        binding.editNev.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                    if (ListResultActivity.cities.contains(binding.editNev.getText().toString().trim())) {
                        binding.editNev.setTextColor(Color.RED);
                    } else {
                        binding.editNev.setTextColor(Color.GREEN);
                    }
                }
            else {
                binding.editNev.setTextColor(Color.BLACK);
            }

        });
        binding.editNev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                gombfeoldo();

            }
        });
        binding.editOrszag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                gombfeoldo();

            }
        });
        binding.editLakossag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                gombfeoldo();

            }
        });
    }

    private boolean validacio(String nev, String orszag, String lakossagString) {
        if (nev.isEmpty()) {
            Toast.makeText(this, "Név megadása kötelező", Toast.LENGTH_SHORT).show();
            randomGif();
            return false;
        }
        if (orszag.isEmpty()) {
            Toast.makeText(this, "Ország megadása kötelező", Toast.LENGTH_SHORT).show();
            randomGif();
            return false;
        }
        if (lakossagString.isEmpty()) {
            randomGif();
            Toast.makeText(this, "Lakosság megadása kötelező", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void gombfeoldo() {
        if (!binding.editNev.getText().equals("") &&
                !binding.editNev.getText().equals("") &&
                        !binding.editLakossag.getText().equals("")) {
            binding.btnFelvetel.setEnabled(true);
        }
    }
    private void alaphelyzet() {
        binding.editNev.setText("");
        binding.editOrszag.setText("");
        binding.editLakossag.setText("");
    }

    private void randomGif() {
        binding.animacio.setVisibility(View.VISIBLE);
        int index = random.nextInt(9) + 1;
        int gif = gifek[index];
        binding.animacio.setImageResource(gif);

    }

    private void gifLeallitasa() {
        binding.animacio.setVisibility(View.INVISIBLE);
    }

    private void varosHozzadasa() {
        String nev = binding.editNev.getText().toString().trim();
        String orszag = binding.editOrszag.getText().toString().trim();
        String lakossagString = binding.editLakossag.getText().toString().trim();
        if (nev.isEmpty() || orszag.isEmpty() || lakossagString.isEmpty()) {
            binding.btnFelvetel.setEnabled(false);
            randomGif();
        }
        if (!validacio(nev, orszag, lakossagString)) {
            Toast.makeText(InsertActivity.this, "Sikertelen felvétel", Toast.LENGTH_SHORT).show();
            randomGif();
            return;
        }
        int lakossag = Integer.parseInt(lakossagString);
        City city = new City(0, nev, orszag, lakossag);
        Toast.makeText(InsertActivity.this, "Sikeres felvétel", Toast.LENGTH_SHORT).show();
        gifLeallitasa();
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
                runOnUiThread(() -> Toast.makeText(InsertActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
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