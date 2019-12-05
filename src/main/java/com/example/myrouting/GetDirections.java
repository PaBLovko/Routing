package com.example.myrouting;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

class GetDirections {

    private final MyAsyncTask asyncTask = new MyAsyncTask();

    protected void getLatLng(String address, boolean setDestination) {
        String uri = "http://maps.google.com/maps/api/geocode/json?address="
                + address + "&sensor=false";

        HttpGet httpGet = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int byteData;
            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (setDestination) {
            AddNewRouteActivity.finalAddressPos = new LatLng(lat, lng);
        } else {
            AddNewRouteActivity.addressPos = new LatLng(lat, lng);
        }

    }

    public AsyncTask<String, String, String> getAsyncTask() {
        return asyncTask;
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String startAddress = params[0];
            startAddress = startAddress.replaceAll(" ", "%20");
            getLatLng(startAddress, false);

            String endingAddress = params[1];
            endingAddress = endingAddress.replaceAll(" ", "%20");
            getLatLng(endingAddress, true);
            return null;
        }
    }
}
