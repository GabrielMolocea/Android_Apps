package com.gabriel.taxifee;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionJSONParser {

    // Receives a JSONObject and returns a list of lists containing latitude and longitude
    public List<List<HashMap<String, String>>> parse(JSONObject jsonObject){

        List<List<HashMap<String, String>>> routes = new ArrayList<>();
        JSONArray jRoutes, jLegs, jSteps;

        try {

            jRoutes = jsonObject.getJSONArray("routes");

            // Traversing all routes
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String,String>>();

                // Traversing all legs
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jRoutes.get(i)).getJSONArray("steps");

                    // Traversing all legs
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List list = decodePoly(polyline);

                        // Traversing all legs
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("lat", Double.toString(((LatLng)list.get(l)).latitude));
                            hashMap.put("lnl", Double.toString(((LatLng)list.get(l)).longitude));
                            path.add(hashMap);
                        }
                    }
                    routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return routes;
    }

    private List decodePoly(String encoder) {
    }
}
