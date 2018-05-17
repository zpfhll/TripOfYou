package ll.zhao.tripdatalibrary.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ll.zhao.tripdatalibrary.TripEnum;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TripModel extends BaseModel {
    private String id;
    private String trip_name;
    private String memo;
    private String location;
    private double count;
    private String start_time;
    private String end_time;
    @TripEnum.TRIP_STATE
    private int state;
    private String administrator;
    private String loction_id;
    private Bitmap image1;
    private Bitmap image2;

    @Override
    public String toString() {
        String str = "id:" + id + " trip_name:" + trip_name + " memo:" + memo + " location:" + location+ " count:" + count
                + " start_time:" + start_time+ " end_time:" + end_time+ " state:" + state+ " administrator:" + administrator+ " loction_id:" + loction_id;
        return str;
    }

    @Override
    public String getKey() {
        super.getKey();
        return id;
    }

    public Bitmap getImage1() {
        return image1;
    }

    public void setImage1(Bitmap image1) {
        this.image1 = image1;
    }

    public void setImage1(byte[] bytes){
        this.image1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public Bitmap getImage2() {
        return image2;
    }

    public void setImage2(Bitmap image2) {
        this.image2 = image2;
    }

    public void setImage2(byte[] bytes){
        this.image2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getLoction_id() {
        return loction_id;
    }

    public void setLoction_id(String loction_id) {
        this.loction_id = loction_id;
    }




}
