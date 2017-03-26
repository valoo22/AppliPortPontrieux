package com.example.valoo221.portpontrieux;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class MainActivity extends Activity
{
 private ListView LVEmplacement;

 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
     LVEmplacement = (ListView)findViewById(R.id.LVEmplacement);
     List<Emplacement> LesEmplacements = getEmplacements();
     EmplacementAdapter adapter = new EmplacementAdapter(MainActivity.this, LesEmplacements);
     LVEmplacement.setAdapter(adapter);
 }
 public List<Emplacement> getEmplacements()
 {
     ArrayList<Emplacement> lesEmplacements = new ArrayList<Emplacement>();
     AccesWebService accesWS = new AccesWebService();
     try
     {
         InputStream iS = accesWS.execute().get();
         String result = InputStreamOperations.InputStreamToString(iS);
         System.out.println(result);
         JSONArray array = new JSONArray(result);
         System.out.println(array.get(0).toString());
         for (int i = 0; i < array.length();i++)
         {
             JSONObject obj  = new JSONObject(array.getString(i));
             Emplacement unEmplacement = new Emplacement();
             unEmplacement.setRefEmplacement(obj.getInt("id"));
             unEmplacement.setDispo(obj.getBoolean("disponible"));
             Type UnType = new Type();
             UnType.setId(obj.getJSONObject("typeId").getInt("id"));
             UnType.setPrix(obj.getJSONObject("typeId").getDouble("prix"));
             UnType.setProfondeur(obj.getJSONObject("typeId").getInt("profondeur"));
             UnType.setSituation(obj.getJSONObject("typeId").getString("situation"));
             unEmplacement.setUnType(UnType);
             lesEmplacements.add(unEmplacement);
         }
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
     System.out.println(lesEmplacements.get(2).getUnType().getPrix());
     return lesEmplacements;
 }

 private class AccesWebService extends AsyncTask<Void, Void, InputStream>
 {

     @Override
     protected InputStream doInBackground(Void... params)
     {
         try
         {
             String MonUrl = "http://192.168.43.189:8080/WebService_PortPontrieux/webresources/emplacement";
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