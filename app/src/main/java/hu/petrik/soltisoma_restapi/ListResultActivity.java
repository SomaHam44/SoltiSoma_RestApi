package hu.petrik.soltisoma_restapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.petrik.soltisoma_restapi.databinding.ActivityListResultBinding;

public class ListResultActivity extends AppCompatActivity {
   ActivityListResultBinding binding;
   List<City> cities = new ArrayList<>();
   private String URL = "http://127.0.0.1:8000/api/cities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        binding = ActivityListResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CityTask cityTask = new CityTask();
        cityTask.execute();
        binding.btnVisszafele.setOnClickListener(view -> {
            Intent vissza = new Intent(ListResultActivity.this, MainActivity.class);
            startActivity(vissza);
            finish();
        });
    }

    private class CityTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                response = RequestHandler.get(URL);

            } catch (IOException e) {
                e.getStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson converter = new Gson();
            if (response == null || response.getResponseCode() >= 400) {
                Toast.makeText(ListResultActivity.this, "Hiba történt a kérésnek feldolgozása során!", Toast.LENGTH_SHORT).show();

            }
            else {
                City[] places =  converter.fromJson(response.getContent(), City[].class);
                cities.clear();
                cities.addAll(Arrays.asList(places));
                ArrayAdapter<City> cityArrayAdapter = new ArrayAdapter<>(ListResultActivity.this,  R.layout.list_item, R.id.textviewItems, cities);
                binding.listviewOrszagVaros.setAdapter(cityArrayAdapter);
            }
        }
    }


}