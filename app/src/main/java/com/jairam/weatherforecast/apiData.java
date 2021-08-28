package com.jairam.weatherforecast;


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
            double maxTemp = jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int roundedValueMaxTemp = (int) Math.rint(maxTemp);
            apiD.maxTemperature = Integer.toString(roundedValueMaxTemp);
            double minTemp = jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;
            int roundedValueMinTemp = (int) Math.rint(maxTemp);
            apiD.minTemp = Integer.toString(roundedValueMinTemp);
            double lat = jsonObject.getJSONObject("coord").getDouble("lat")-273.15;
            int roundedValueLat = (int) lat;
            apiD.lat = Integer.toString(roundedValueLat);
            double lon = jsonObject.getJSONObject("coord").getDouble("lon")-273.15;
            int roundedValueLon = (int) lat;
            apiD.longitude = Integer.toString(roundedValueLon);
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
        return temperature + "°C";
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
