package com.example.app23.Builder;

import org.json.JSONException;
import org.json.JSONObject;

public class GetParamsBuilder {

    public static JSONObject getParams(String city, int page, int contentperpages){

        JSONObject GetParams = new JSONObject();
        try {
            GetParams.put("city", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            GetParams.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            GetParams.put("content_per_pages", contentperpages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return GetParams;
    }
}
