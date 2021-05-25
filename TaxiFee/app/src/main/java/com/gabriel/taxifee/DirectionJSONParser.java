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
                List<HashMap<String, String>> path = new ArrayList<HashMap<String,String>>();

                // Traversing all steps
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jRoutes.get(i)).getJSONArray("steps");

                    // Traversing all points
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline;
                        polyline = (String) ((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        // Traversing lat and lng
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("lat", Double.toString((list.get(l)).latitude));
                            hashMap.put("lng", Double.toString((list.get(l)).longitude));
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

    private List<LatLng> decodePoly(String encoder) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoder.length();
        int lat , lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoder.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            lat = ((result & 1) != 0 ? ~ (result >> 1) : (result >> 1));

            shift = 0;
            result = 0;

            do {
                b = encoder.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
