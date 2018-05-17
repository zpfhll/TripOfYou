package ll.zhao.triptoyou.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.HLLButton;

public class MapActivity extends BaseActivity implements AMapLocationListener,Inputtips.InputtipsListener, View.OnClickListener{
    private AMap amap;
    private MapView mapView;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int LOCATION_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private Marker locMarker;

    private HLLButton home;
    private EditText searchLocation;
    private String city = null;
    private ListView tipView;
    private List<Tip> tips;
    private MapTipAdapter adapter;

    //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
    private InputtipsQuery inputquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        home = findViewById(R.id.home_button);

        tipView = findViewById(R.id.tip_list);
        tips = new ArrayList<>();
        adapter = new MapTipAdapter(tips,this);
        tipView.setAdapter(adapter);
        tipView.setVisibility(View.GONE);
        searchLocation = findViewById(R.id.search_location);
        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                if( s.toString() != null && s.toString().equals("")){
                    tipView.setVisibility(View.GONE);
                }else {
                    InputtipsQuery inputquery = new InputtipsQuery(s.toString(), city);
                    inputquery.setCityLimit(true);//限制在当前城市
                    Inputtips inputTips = new Inputtips(MapActivity.this, inputquery);
                    inputTips.setInputtipsListener(MapActivity.this);
                    inputTips.requestInputtipsAsyn();
                }
            }
        });
        home.setOnClickListener(this);

        initLocation();//初始化定位参数
        checkStoragePermission();//初始化请求权限，存储权限
        //添加地图
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (amap == null) {
            amap = mapView.getMap();
        }
        init();
    }

    private void init() {
        amap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        amap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //没有权限，向系统申请该权限。
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }else if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //没有权限，向系统申请该权限。
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        }else{
            startLocation();
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }else{
            checkLocationPermission();
        }
    }

    //定位
    private void initLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(false);
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null
                && aMapLocation.getErrorCode() == 0) {
            stopLocation();
            double longitude = aMapLocation.getLongitude();
            double latitude = aMapLocation.getLatitude();
            LatLng location = new LatLng(latitude, longitude);
            aMapLocation.getAoiName();
            changeLocation(location);
            city = aMapLocation.getCity();
            String errText = longitude + ": " + latitude + " : " + aMapLocation.getAoiName()+ " : " + aMapLocation.getPoiName();
            HLLog.showLog("定位", errText);
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            HLLog.showLog("AmapErr", errText);
        }
    }

    private void changeLocation(LatLng location) {
        if (locMarker == null){
            locMarker = amap.addMarker(new MarkerOptions().position(location));
        }else{
            locMarker.setPosition(location);
        }
        amap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
    }

    /**
     * 开始定位
     */
    private void startLocation(){
        // 启动定位
        mlocationClient.startLocation();
        HLLog.showLog("MY","startLocation");
    }
    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        mlocationClient.stopLocation();
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
            mlocationClient = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HLLog.showLog("MY","定位权限已获取");
                    startLocation();
                } else {
                    HLLog.showLog("MY","定位权限被拒绝");
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                        HLLog.showLog("MY","false 勾选了不再询问，并引导用户去设置中手动设置");
                        return;
                    }
                }
                return;
            }
            case STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HLLog.showLog("onRequestResult","存储权限已获取");
                } else {
                    HLLog.showLog("MY","存储权限被拒绝");
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        HLLog.showLog("MY","false 勾选了不再询问，并引导用户去设置中手动设置");
                    }
                    return;
                }
                checkLocationPermission();
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_button:
                finish();
                break;
        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        tips.clear();
        if(list.size() < 1){
            tipView.setVisibility(View.GONE);
        }else{
            tips.addAll(list);
            tipView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
        HLLog.showLog("MY",list.toString());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
