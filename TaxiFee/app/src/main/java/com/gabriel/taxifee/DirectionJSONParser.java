package com.gabriel.taxifee;

import org.json.JSONArray;
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
            for (int i = 0; )
        }

    }
}
