package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import DataBase.City;
import DataBase.County;
import DataBase.DataBasesimpleweather;
import DataBase.Province;

/**
 * Created by Singlecloud on 2015/7/13.
 * �����ʹ�����������ص�����
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(                     //����ʡ��
            DataBasesimpleweather dataBasesimpleweather,String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            Log.v("debug", "message............u1");
            if (allProvinces != null && allProvinces.length >0){
                for(String p:allProvinces){
                    String[] array = p.split("\\|");
                    Province province =new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    dataBasesimpleweather.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCitiesResponse(                                     //��������
            DataBasesimpleweather dataBasesimpleweather,String response,int provinceid){
        if (!TextUtils.isEmpty(response)){
            Log.v("debug", "message............u2");
            String[] allcities =response.split(",");
            if (allcities != null && allcities.length>0){
                for (String c: allcities){
                    String[] array=c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceid);
                    dataBasesimpleweather.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCountiesResponse(
            DataBasesimpleweather dataBasesimpleweather,String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            Log.v("debug", "message............u3");
            if (allCounties != null && allCounties.length>0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county =new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    dataBasesimpleweather.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
    public static void handleWeatherResponse(Context context,String response){
        try {Log.v("debug", "message............u4");
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherInfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String pubishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,pubishTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static void saveWeatherInfo(
            Context context,String cityName,String weather,String temp1,String temp2,
            String weatherDesp,String publishTime){Log.v("debug", "message............u5");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy��M��d��", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weather);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weatherDesp", weatherDesp);
        editor.putString("publishTime", publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}
