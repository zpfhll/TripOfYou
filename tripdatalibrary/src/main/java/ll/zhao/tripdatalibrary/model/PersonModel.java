package ll.zhao.tripdatalibrary.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 人员信息
 * Created by Administrator on 2018/3/21.
 */
public class PersonModel extends BaseModel {
    private String tel = "";
    private String name = "";
    private Bitmap icon = null;
    private String type = "0";

    public String getKey(){
        return tel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(byte[] bytes){
        this.icon = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        String str = "Tel:" + tel + " Name:" + name + " Type:" + type + " Icon:" + icon;
        return str;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
