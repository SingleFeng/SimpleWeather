package DataBase;

/**
 * Created by Singlecloud on 2015/7/13.
 * 省份类
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id =id;
    }
    public String getProvinceName(){
        return provinceName;
    }
    public void setProvinceName(String provincename){
        this.provinceName=provincename;
    }
    public String getProvinceCode(){
        return provinceCode;
    }
    public void setProvinceCode(String provincecode){
        this.provinceCode=provincecode;
    }
}
