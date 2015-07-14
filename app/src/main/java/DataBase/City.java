package DataBase;


public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityname){
        this.cityName=cityname;
    }
    public String getCityCode(){
        return cityCode;
    }
    public void setCityCode(String citycode){
        this.cityCode=citycode;
    }
    public int getProvinceId(){
        return provinceId;
    }
    public void setProvinceId(int provinceId){
        this.provinceId=provinceId;
    }
}
