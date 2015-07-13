package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Singlecloud on 2015/7/13.
 * ��װ�������ݿ����
 */
public class DataBasesimpleweather {
    public static final String DB_NAME = "Simple_weather";  //���ݿ���
    public static final int VERSION = 1;                    //���ݿ�汾
    private static DataBasesimpleweather dataBasesimpleweather;
    private SQLiteDatabase db;

/**
 *�����췽��˽�л�
 */
    private DataBasesimpleweather(Context context){
        DataBasesimpleweatherHelper dbHelper = new DataBasesimpleweatherHelper(
                context,DB_NAME,null,VERSION);
        db= dbHelper.getWritableDatabase();
    }
/**
 *��ȡʵ��
 */
    public synchronized static DataBasesimpleweather getInstance(Context context){
        if (dataBasesimpleweather == null){
            dataBasesimpleweather = new DataBasesimpleweather(context);
        }
        return dataBasesimpleweather;
    }
/**
 * �洢Provinceʵ�������ݿ�
 */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvincename());
            values.put("province_code",province.getProvincecode());
            db.insert("Province",null,values);
        }
    }
/**
 *�����ݿ��ȡȫ������ʡ�ݵ���Ϣ
 */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvincename(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvincecode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }
    public void saveCity(City city){
        if(city != null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityname());
            values.put("city_code",city.getCitycode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    public List<City> loadCities(int provinceid){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id=?",
                new String[]{String.valueOf(provinceid)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city= new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityname(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCitycode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceid);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void saveCounty(County county){
        if(county != null){
            ContentValues values =new ContentValues();
            values.put("county_name",county.getCountyname());
            values.put("county_code",county.getCountycode());
            values.put("county_id",county.getCityid());
            db.insert("County",null,values);
        }
    }
    public List<County> loadCounties(int Cityid){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",
                new String[]{String.valueOf(Cityid)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county= new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyname(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountycode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityid(Cityid);
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}