package com.wangshiqi.pineappleb.ui.activity.mine;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.wangshiqi.pineappleb.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dllo on 16/10/26.
 */
public class GPSPlayActivity extends Activity implements LocationSource, View.OnClickListener, AMapLocationListener {
    private MapView mMapView;
    private AMap aMap;
    private Button nightBtn;
    private Button locatingBtn;
    private Button satelliteBtn;
    private Button conditionBtn;

    private boolean nightState = false;
    private boolean locatingState = false;
    private boolean satelliteState = false;
    private boolean conditionState = false;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mLocationClient = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_layout);
        nightBtn = (Button) findViewById(R.id.map_night_btn);
        locatingBtn = (Button) findViewById(R.id.map_locating_btn);
        satelliteBtn = (Button) findViewById(R.id.map_satellite_btn);
        conditionBtn = (Button) findViewById(R.id.map_condition_btn);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        //初始化地图变量
        aMap = mMapView.getMap();

        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mLocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位

        nightBtn.setOnClickListener(this);
        locatingBtn.setOnClickListener(this);
        satelliteBtn.setOnClickListener(this);
        conditionBtn.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 夜间模式
            case R.id.map_night_btn:
                if (nightState == false) {
                    aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                    nightBtn.setText("日间模式");
                    nightState = true;
                } else {
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    nightBtn.setText("夜间模式");
                    nightState = false;
                }
                break;
            // 定位
            case R.id.map_locating_btn:
                if (locatingState == false) {
                    mLocationClient.startLocation();
                    locatingBtn.setText("取消定位");
                    locatingState = true;
                } else {
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁

                    locatingBtn.setText("定位");
                    locatingState = false;
                }
                break;
            // 卫星地图
            case R.id.map_satellite_btn:
                if (satelliteState == false) {
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                    satelliteBtn.setText("标准模式");
                    satelliteState = true;
                } else {
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    satelliteBtn.setText("卫星模式");
                    satelliteState = false;
                }
                break;
            // 路况显示
            case R.id.map_condition_btn:
                if (conditionState == false) {
                    aMap.setTrafficEnabled(true);
                    conditionBtn.setText("隐藏路况");
                    conditionState = true;
                } else {
                    aMap.setTrafficEnabled(false);
                    conditionBtn.setText("显示路况");
                    conditionState = false;
                }
                break;

        }
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息

                String s = amapLocation.getAddress() + amapLocation.getCountry() + amapLocation.getProvince() + amapLocation.getCity() + amapLocation.getDistrict() + amapLocation.getStreet() + amapLocation.getStreetNum() + amapLocation.getCityCode() + amapLocation.getAdCode() + amapLocation.getAoiName();
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

                String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
                IntentFilter fliter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                fliter.addAction(GEOFENCE_BROADCAST_ACTION);
                //mGeoFenceReceiver为自定义的广播接收器
                registerReceiver(mGeoFenceReceiver, fliter);
                //创建PendingIntent对象
                Intent intent = new Intent(GEOFENCE_BROADCAST_ACTION);
                PendingIntent mPendingIntent = null;
                mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                //添加地理围栏，设置地理围栏中心点，半径等参数
                mLocationClient.addGeoFenceAlert("556", latitude, longitude, 1000, 1000 * 60 * 30, mPendingIntent);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }

    }

    //自定义广播接收器
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播内容，处理进出的具体操作。
        }
    };
}
