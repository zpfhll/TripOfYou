package ll.zhao.triptoyou.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ll.zhao.triptoyou.Utils;

/**
 * 人员信息
 * Created by Administrator on 2018/3/21.
 */
@Entity(nameInDb = "Person",indexes = {
        @Index(value = "tel DESC",unique = true)
})
public class Person{
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String tel = "";

    @NotNull
    private String name = "";

    private byte[] icon = null;

    @NotNull
    private String type = "0";

    public Person(){}

    @Generated(hash = 2096362876)
    public Person(Long id, @NotNull String tel, @NotNull String name, byte[] icon,
            @NotNull String type) {
        this.id = id;
        this.tel = tel;
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

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

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] bytes){
        this.icon = bytes;
    }


    @Override
    public String toString() {
        String str = "Tel:" + tel + " Name:" + name + " Type:" + type + " Icon:" + new String(icon);
        return str;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBitmapIcon(Bitmap icon){
        this.icon = Utils.bitmabToBytes(icon);
    }

    public Bitmap getBitmapIcon(){
       return  BitmapFactory.decodeByteArray(icon, 0, icon.length);
    }

}
