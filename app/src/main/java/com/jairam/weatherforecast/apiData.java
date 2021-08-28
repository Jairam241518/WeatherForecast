package com.jairam.weatherforecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class apiData {

    private String temperature, maxTemperature, city, weatherType, minTemp, lat, longitude, weatherIcon;
    private int weatherConditionId;

    public static apiData fromJson(JSONObject jsonObject){

        apiData apiD = new apiData();
        try {
            apiD.city = jsonObject.getString("name");
            apiD.weatherConditionId = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            apiD.weatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue = (int) Math.rint(tempResult);
            apiD.temperature = Integer.toString(roundedValue);
            return apiD;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }




    public String getTemperature() {
        return temperature;
    }

    public String getMaxTemperature() {
        return maxTemperature + "°C";
    }

    public String getCity() {
        return city;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getLat() {
        return lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
