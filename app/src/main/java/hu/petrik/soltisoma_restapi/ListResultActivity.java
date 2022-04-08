package hu.petrik.soltisoma_restapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.petrik.soltisoma_restapi.databinding.ActivityListResultBinding;

public class ListResultActivity extends AppCompatActivity {
   ActivityListResultBinding binding;
   public static List<City> cities = new ArrayList<>();
   //private String url = "http://10.0.2.2:8000/api/cities";
   private String url = "https://retoolapi.dev/jSLi3M/varosok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        binding = ActivityListResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnVisszafele.setOnClickListener(view -> {
            Intent vissza = new Intent(ListResultActivity.this, MainActivity.class);
            startActivity(vissza);
            finish();
        });
        CityTask cityTask = new CityTask(url, "GET");
        cityTask.execute();
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
                response = RequestHandler.get(requestUrl);

            } catch (IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(ListResultActivity.this, e.toString(), Toast.LENGTH_SHORT).show());
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson converter = new Gson();
            if (response == null | response.getResponseCode() >= 400) {
                Toast.makeText(ListResultActivity.this, "Hiba történt a kérésnek feldolgozása során!", Toast.LENGTH_SHORT).show();

            }
            else {
                City[] places =  converter.fromJson(response.getContent(), City[].class);
                cities.clear();
                cities.addAll(Arrays.asList(places));
                ArrayAdapter<City> cityArrayAdapter = new ArrayAdapter<>(ListResultActivity.this, R.layout.list_item, R.id.textviewItems, cities);
                binding.listviewOrszagVaros.setAdapter(cityArrayAdapter);
            }
        }


    }


}