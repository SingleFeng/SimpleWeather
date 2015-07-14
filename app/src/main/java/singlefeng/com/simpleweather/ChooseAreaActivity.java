package singlefeng.com.simpleweather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import DataBase.City;
import DataBase.County;
import DataBase.DataBasesimpleweather;
import DataBase.Province;
import util.HttpCallbackListener;
import util.HttpUtil;
import util.Utility;


public class ChooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY= 1;
    public static final int LEVEL_COUNTY= 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DataBasesimpleweather dataBasesimpleweather;
    private List<String> dataList = new ArrayList<String>();
    private boolean isFromWeatherActivity;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("city_selected", false)) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        dataBasesimpleweather = DataBasesimpleweather.getInstance(this);
        Log.v("debug", "message............x");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>arg0, View view,int index,long arg3){
                Log.v("debug", "message............0");
                if (currentLevel == LEVEL_PROVINCE){
                    Log.v("debug", "message............1");
                    selectedProvince = provinceList.get(index);
                    queryCities();
                }else if (currentLevel == LEVEL_CITY){
                    Log.v("debug", "message............2");
                    selectedCity = cityList.get(index);
                    queryCounties();
                }else if (currentLevel == LEVEL_COUNTY) {
                    String countyCode = countyList.get(index).getCountycode();
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra("county_code", countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces();
    }
    /**
     * 查询全国所有的省，优先级 数据库>服务器
     */
    private void queryProvinces(){
        provinceList = dataBasesimpleweather.loadProvinces();
        Log.v("debug", "message............3");
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province:provinceList){
                dataList.add(province.getProvincename());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }else {
            queryFromServer(null, "provinces");
        }
    }
    /**
     * 查询已选中省的所有地市  优先级 数据库>服务器
     */
    private void queryCities(){
        cityList = dataBasesimpleweather.loadCities(selectedProvince.getId());
        Log.v("debug", "message............4");
        if (cityList.size()>0){
            dataList.clear();
            for (City city: cityList){
                dataList.add(city.getCityname());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvincename());
            currentLevel = LEVEL_CITY;
        }else {
            queryFromServer(selectedProvince.getProvincecode(),"city");
        }
    }
    /**
     * 查询已选中地市的所有县市 优先级 数据库>服务器
     */
    private void queryCounties(){
        countyList= dataBasesimpleweather.loadCounties(selectedCity.getId());
        Log.v("debug", "message............5");
        if (countyList.size()>0){
            dataList.clear();
            for (County county:countyList){
                dataList.add(county.getCountyname());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityname());
            currentLevel = LEVEL_COUNTY;
        }else {
            queryFromServer(selectedCity.getCitycode(), "county");
        }
    }
    /**
     * 根据传入的代号和类型从服务器上查询市县数据
     */
    private void queryFromServer(final String code,final String type){
        Log.v("debug", "message............6");
        String address;
        if (!TextUtils.isEmpty(code)){
            address="http://www.weather.com.cn/data/list3/city"+code+".xml";
        }else{
            address="http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinsh(String response) {
                boolean result = false;
                Log.v("debug", "message............7");
                if ("Province".equals(type)){
                    result = Utility.handleProvincesResponse(
                            dataBasesimpleweather,response);
                }else if("City".equals(type)){
                    result = Utility.handleCitiesResponse(
                            dataBasesimpleweather,response,selectedProvince.getId());
                }else if("County".equals(type)){
                    result = Utility.handleCountiesResponse(
                            dataBasesimpleweather,response,selectedCity.getId());
                }
                if (result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                Log.v("debug", "message............8");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * 进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭对话框
     */
    private void closeProgressDialog(){
        if(progressDialog !=null){
            progressDialog.dismiss();
        }
    }
    /**
     * 捕获BACK，判断应该返回到哪个级别
     */
    @Override
    public void onBackPressed(){
        if (currentLevel == LEVEL_COUNTY){
            queryCities();
        }else if (currentLevel == LEVEL_CITY){
            queryProvinces();
        }else{
            if (isFromWeatherActivity){
                Intent intent = new Intent(this,WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
