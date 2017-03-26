package com.example.valoo221.portpontrieux;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import metier.Emplacement;
import metier.Type;

/**
 * Created by Valoo22 on 26/03/2017.
 */

public class EmplacementActivity extends Activity {
    private Button BtnRetour;
    private TextView TVRefEmplacement, TVSituation, TVPrix, TVProfondeur, TVDispo;
    private Integer NumEmplacement;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplacment);
        Intent intent = getIntent();
        NumEmplacement = intent.getIntExtra("position", 1) + 1;
        System.out.println("N°Emplacement :" + NumEmplacement);
        BtnRetour=(Button)findViewById(R.id.btnRetour);
        BtnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EmplacementActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        Emplacement UnEmplacement = getEmplacement();
        TVRefEmplacement = (TextView) findViewById(R.id.TVRefEmplacement);
        TVSituation = (TextView) findViewById(R.id.TVSituation);
        TVDispo = (TextView) findViewById(R.id.TVDispo);
        TVProfondeur = (TextView) findViewById(R.id.TVProfondeur);
        TVPrix = (TextView) findViewById(R.id.TVPrix);
        TVRefEmplacement.setText(String.valueOf(UnEmplacement.getRefEmplacement()));
        TVSituation.setText(UnEmplacement.getUnType().getSituation());
        if(UnEmplacement.getDispo())
        {
            TVDispo.setText("OUI");
        }
        else
        {
            TVDispo.setText("NON");
        }
        TVProfondeur.setText(String.valueOf(UnEmplacement.getUnType().getProfondeur()));
        TVPrix.setText(String.valueOf(UnEmplacement.getUnType().getPrix()));
    }

    public Emplacement getEmplacement()
    {
        Emplacement UnEmplacement = new Emplacement();
        AccesWebService accesWS = new AccesWebService();
        try
        {
            InputStream iS = accesWS.execute().get();
            String result = InputStreamOperations.InputStreamToString(iS);
            JSONObject obj =  new JSONObject(result);
            UnEmplacement.setRefEmplacement(obj.getInt("id"));
            UnEmplacement.setDispo(obj.getBoolean("disponible"));
            Type leType = new Type();
            leType.setId(obj.getJSONObject("typeId").getInt("id"));
            leType.setPrix(obj.getJSONObject("typeId").getDouble("prix"));
            leType.setProfondeur(obj.getJSONObject("typeId").getInt("profondeur"));
            leType.setSituation(obj.getJSONObject("typeId").getString("situation"));
            UnEmplacement.setUnType(leType);
        }
        catch (InterruptedException e)
        {
            System.out.println("Erreur Interpt:" + e.getMessage());
        }
        catch (ExecutionException e)
        {
            System.out.println("Erreur Execution:" + e.getMessage());
        }
        catch (JSONException e)
        {
            System.out.println("Erreur JSON:" + e.getMessage());
        }
        return UnEmplacement;
    }
    private class AccesWebService extends AsyncTask<Void, Void, InputStream>
    {
        @Override
        protected InputStream doInBackground(Void... params)
        {
            try
            {
                String MonUrl = "http://192.168.1.35:8080/WebService_PortPontrieux/webresources/emplacement/find/"+NumEmplacement;
                URL url = new URL(MonUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
            catch (MalformedURLException e)
            {
                System.out.println("Erreur MalUrl:" + e.getMessage());
                return  null;
            }
            catch (IOException e)
            {
                System.out.println("Erreur IOEE:" + e.getMessage());
                return  null;
            }

        }
    }
}
