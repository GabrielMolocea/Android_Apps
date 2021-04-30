package com.gabriel.taxifee;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    // Declaring variables
    GoogleMap mMap;
    String url;
    LatLng startLatLng, endLatLng;

    HttpsURLConnection httpsURLConnection = null;
    String data = "" ;
    InputStream inputStream = null;
    Context context;

    public GetDirectionsData(Context context) {
        this.context = context;
    }



    @Override
    protected String doInBackground(Object... objects) {
        // Initializing variables
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        startLatLng = (LatLng) objects[2];
        endLatLng = (LatLng) objects[3];

        try {
            // Connecting to url
            URL myURL = new URL(url);
            httpsURLConnection = (HttpsURLConnection) myURL.openConnection();
            httpsURLConnection.connect();

            inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder =  new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0)
                    .getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

            int count = jsonArray.length();
            String[] polyline_array = new String[count];

            JSONObject jsonObject1;

            for (int i = 0; i < count; i++) {
                jsonObject1 =  jsonArray.getJSONObject(i);

                String polygon = jsonObject1.getJSONObject("polyline").getString("points");
                polyline_array[i] = polygon;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
