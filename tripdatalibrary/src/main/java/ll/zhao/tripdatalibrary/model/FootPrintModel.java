package ll.zhao.tripdatalibrary.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 足迹信息
 */
public class FootPrintModel extends BaseModel{


    private String footPrintId;

    private String footPrintTime;

    private String footPrintLocation;

    private String tipId;

    private Bitmap icon = null;


    /**
     * start:開始
     * end：終了
     * do：実施中
     * normal:正常
     */
    private String footPrintType;

    public FootPrintModel(){}

    public FootPrintModel(String footPrintId, String footPrintTime, String footPrintLocation, String tipId) {
        this.footPrintId = footPrintId;
        this.footPrintTime = footPrintTime;
        this.footPrintLocation = footPrintLocation;
        this.tipId = tipId;
    }


    public String getFootPrintId() {
        return footPrintId;
    }

    public void setFootPrintId(String footPrintId) {
        this.footPrintId = footPrintId;
    }

    public String getFootPrintTime() {
        return footPrintTime;
    }

    public void setFootPrintTime(String footPrintTime) {
        this.footPrintTime = footPrintTime;
    }

    public String getFootPrintLocation() {
        return footPrintLocation;
    }

    public void setFootPrintLocation(String footPrintLocation) {
        this.footPrintLocation = footPrintLocation;
    }

    public String getTipId() {
        return tipId;
    }

    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    public String getFootPrintType() {
        return footPrintType;
    }

    public void setFootPrintType(String footPrintType) {
        this.footPrintType = footPrintType;
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
}
