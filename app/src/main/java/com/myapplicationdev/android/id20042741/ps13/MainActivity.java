package com.myapplicationdev.android.id20042741.ps13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText addressQuery;
    ListView lv_vaccine_location;
    ArrayList<AreasForVaccine> al_vac_location;
    ArrayAdapter<AreasForVaccine> aa_vac;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_vaccine_location = findViewById(R.id.lvVaccine);
        addressQuery = findViewById(R.id.queryLocation);

        client = new AsyncHttpClient();
        populateListView("");

        addressQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                populateListView("q=" + String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void populateListView(String query) {
        Log.i("debug", query);
        al_vac_location = new ArrayList<>();
        String url = String.format("https://data.gov.sg/api/action/datastore_search?resource_id=f179cb8a-20ad-4c91-b990-e315ed383018&%s", query);
        client.get(url, new JsonHttpResponseHandler() {
            String location;
            String address;
            String vaccine_type;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // the order for this would be jsonArray, firstObj, then jsonArrForecast
                    // this is because in the website, the structure is
                    // json object named item first
                    // followed by the json object named 0
                    // then inside the json object 0, it contains the forecast name which is an array

                    JSONObject jsonVaccineData = response.getJSONObject("result");
                    JSONArray jsonArrRecords = jsonVaccineData.getJSONArray("records");

                    for (int i = 0; i < jsonArrRecords.length(); i++) {
                        JSONObject jsonObjRecords = jsonArrRecords.getJSONObject(i);
                        vaccine_type = jsonObjRecords.getString("vaccine_type");
                        address = jsonObjRecords.getString("address");
                        location = jsonObjRecords.getString("name");

                        AreasForVaccine vaccine_loc = new AreasForVaccine(address, location, vaccine_type);
                        al_vac_location.add(vaccine_loc);

                    }

                } catch (JSONException e) {
                    Log.i("debug", String.valueOf(e));
                }

                aa_vac = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, al_vac_location);
                lv_vaccine_location.setAdapter(aa_vac);
                aa_vac.notifyDataSetChanged();

            } // closes onsuccess

        });
    }

}