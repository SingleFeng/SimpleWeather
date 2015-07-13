package DataBase;

/**
 * Created by Singlecloud on 2015/7/13.
 * µÿ –¿‡
 */
public class City {
    private int id;
    private String cityname;
    private String citycode;
    private int provinceId;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCityname(){
        return cityname;
    }
    public void setCityname(String cityname){
        this.cityname=cityname;
    }
    public String getCitycode(){
        return citycode;
    }
    public void setCitycode(String citycode){
        this.citycode=citycode;
    }
    public int getProvinceId(){
        return provinceId;
    }
    public void setProvinceId(int provinceId){
        this.provinceId=provinceId;
    }
}
