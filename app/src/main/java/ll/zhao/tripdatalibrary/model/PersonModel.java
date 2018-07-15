//package ll.zhao.tripdatalibrary.model;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * 人员信息
// * Created by Administrator on 2018/3/21.
// */
//public class PersonModel extends BaseModel implements Parcelable {
//    private String tel = "";
//    private String name = "";
//    private Bitmap icon = null;
//    private String type = "0";
//
//    public PersonModel(){
//
//    }
//
//    protected PersonModel(Parcel in) {
//        tel = in.readString();
//        name = in.readString();
//        icon = in.readParcelable(Bitmap.class.getClassLoader());
//        type = in.readString();
//    }
//
//    public static final Creator<PersonModel> CREATOR = new Creator<PersonModel>() {
//        @Override
//        public PersonModel createFromParcel(Parcel in) {
//            return new PersonModel(in);
//        }
//
//        @Override
//        public PersonModel[] newArray(int size) {
//            return new PersonModel[size];
//        }
//    };
//
//    public String getKey(){
//        return tel;
//    }
//
//    public String getTel() {
//        return tel;
//    }
//
//    public void setTel(String tel) {
//        this.tel = tel;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Bitmap getIcon() {
//        return icon;
//    }
//
//    public void setIcon(byte[] bytes){
//        this.icon = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//    }
//
//    public void setIcon(Bitmap icon) {
//        this.icon = icon;
//    }
//
//    @Override
//    public String toString() {
//        String str = "Tel:" + tel + " Name:" + name + " Type:" + type + " Icon:" + icon;
//        return str;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(tel);
//        dest.writeString(name);
//        dest.writeParcelable(icon, flags);
//        dest.writeString(type);
//    }
//}
