package DataBase;

/**
 * Created by Singlecloud on 2015/7/13.
 * œÿ –¿‡
 */
public class County {
    private int id;
    private String countyname;
    private String countycode;
    private int cityid;
    public int getId(){
        return id;
    }
    public void setId(int id){this.id=id;}
    public String getCountyname(){
        return countyname;
    }
    public void setCountyname(String countyname){
        this.countyname=countyname;
    }
    public String getCountycode(){
        return countycode;
    }
    public void setCountycode(String countycode){
        this.countycode=countycode;
    }
    public int getCityid(){
        return cityid;
    }
    public void setCityid(int cityid){
        this.cityid=cityid;
    }
}
