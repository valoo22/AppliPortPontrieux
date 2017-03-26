package com.example.valoo221.portpontrieux;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import metier.Emplacement;
import metier.Type;

/**
 * Created by Valoo221 on 24/03/2017.
 */

public class JSONEmplacement extends AsyncTask <Void, Emplacement, ArrayList<Emplacement>>
{
    @Override
    protected ArrayList<Emplacement> doInBackground(Void... params)
    {
        ArrayList<Emplacement> lesEmplacements = new ArrayList<Emplacement>();
        try
        {
            String MonUrl="http://localhost:8080/WebService_PortPontrieux/webresources/emplacement";
            URL url = new URL(MonUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            String  result = InputStreamOperations.InputStreamToString(inputStream);
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject.toString());
            JSONArray array = new JSONArray(jsonObject.getString("emplacement"));
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
        catch (MalformedURLException e)
        {
            System.out.println("Erreur Url :" + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("Erreur IOE :" + e.getMessage());
        }
        catch (JSONException e)
        {
            System.out.println("Erreur JSON :" + e.getMessage());
        }
        return lesEmplacements;
    }
}
