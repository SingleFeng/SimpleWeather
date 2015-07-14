package DataBase;


public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;
    public int getId(){
        return id;
    }
    public void setId(int id){this.id=id;}
    public String getCountyName(){
        return countyName;
    }
    public void setCountyName(String countyname){
        this.countyName=countyname;
    }
    public String getCountyCode(){
        return countyCode;
    }
    public void setCountyCode(String countycode){
        this.countyCode=countycode;
    }
    public int getCityId(){
        return cityId;
    }
    public void setCityId(int cityid){
        this.cityId=cityid;
    }
}
