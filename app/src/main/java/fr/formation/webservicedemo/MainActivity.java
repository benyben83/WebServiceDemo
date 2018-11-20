package fr.formation.webservicedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvPays;
    HttpClient client;
    String adressall;
    String adressone;
    String code;
    EditText etCodePays;
    TextView tvPays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void searchOne(View view) {

        lvPays = findViewById(R.id.lvPays);
        tvPays = findViewById(R.id.tvPays);
        etCodePays = findViewById(R.id.etCodePays);
        code = etCodePays.getText().toString();


        adressone = "http://demo@services.groupkt.com/country/get/iso2code/\n" +
                code;
        client = new HttpClient();
        client.setAdr(adressone);
        client.start();
        try {
            client.join();
            String reponse = client.getResponse();
            JSONObject j = new JSONObject(reponse);
            JSONObject j2 = j.getJSONObject("RestResponse");
            JSONObject j3 = j2.getJSONObject("result");
            String resultat = j3.getString("name");
            tvPays.setText(resultat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchAll(View view) {
        adressall = "http://demo@services.groupkt.com/country/get/all";
        lvPays = findViewById(R.id.lvPays);
        List liste = new ArrayList();
        client = new HttpClient();
        client.setAdr(adressall);
        client.start();
        try {
            client.join();
            String reponse = client.getResponse();
            JSONObject j = new JSONObject(reponse);
            JSONObject j2 = j.getJSONObject("RestResponse");
            JSONArray result = j2.getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                JSONObject pays = result.getJSONObject(i);
                String affichage = pays.getString("name");
                liste.add(affichage);
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liste);

            lvPays.setAdapter(arrayAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
