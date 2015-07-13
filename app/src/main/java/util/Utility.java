package util;

import android.text.TextUtils;

import DataBase.City;
import DataBase.County;
import DataBase.DataBasesimpleweather;
import DataBase.Province;

/**
 * Created by Singlecloud on 2015/7/13.
 * 解析和处理服务器返回的数据
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(                     //解析省份
            DataBasesimpleweather dataBasesimpleweather,String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length >0){
                for(String p:allProvinces){
                    String[] array = p.split("\\|");
                    Province province =new Province();
                    province.setProvincecode(array[0]);
                    province.setProvincename(array[1]);
                    dataBasesimpleweather.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCitiesResponse(                                     //解析城市
            DataBasesimpleweather dataBasesimpleweather,String response,int provinceid){
        if (!TextUtils.isEmpty(response)){
            String[] allcities =response.split(",");
            if (allcities != null && allcities.length>0){
                for (String c: allcities){
                    String[] array=c.split("\\|");
                    City city = new City();
                    city.setCitycode(array[0]);
                    city.setCityname(array[1]);
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
            if (allCounties != null && allCounties.length>0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county =new County();
                    county.setCountycode(array[0]);
                    county.setCountyname(array[1]);
                    county.setCityid(cityId);
                    dataBasesimpleweather.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
