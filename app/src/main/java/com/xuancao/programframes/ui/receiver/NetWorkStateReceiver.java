package com.xuancao.programframes.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.Rxbus.MsgEvent;
import com.xuancao.programframes.Rxbus.RxBus;
import com.xuancao.programframes.injectors.compontents.DaggerBroadcastComponent;
import com.xuancao.programframes.utils.ClickCommonUtil;
import com.xuancao.programframes.utils.LogUtil;

import javax.inject.Inject;

public class NetWorkStateReceiver extends BroadcastReceiver {

    @Inject
    RxBus mBus;

    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "移动数据网络";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DaggerBroadcastComponent.builder()
                .appComponent(((BaseApp)context.getApplicationContext()).getApplicationComponent())
                .build().inject(this);
        if (ClickCommonUtil.isFastDoubleClick()) {
            return;
        }


        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                LogUtil.e( "WIFI已连接,移动数据已连接");
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
                mBus.send(new MsgEvent(MsgEvent.NETWORK_STATE_EVENT,true));
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                LogUtil.e(getConnectionType(wifiNetworkInfo.getType()) + "连上");
//                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                mBus.send(new MsgEvent(MsgEvent.NETWORK_STATE_EVENT,true));
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                LogUtil.e(getConnectionType(dataNetworkInfo.getType()) + "连上");
//                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                mBus.send(new MsgEvent(MsgEvent.NETWORK_STATE_EVENT,true));
            } else {
                LogUtil.e( "WIFI已断开,移动数据已断开");
//                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                mBus.send(new MsgEvent(MsgEvent.NETWORK_STATE_EVENT,false));
            }
            //API大于23时使用下面的方式进行网络监听
        } else {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();

            //通过循环将网络信息逐个取出来
            boolean isConnected = false;
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

                if (networkInfo!=null) {
                    if (networkInfo.isConnected()) {
                        LogUtil.e(getConnectionType(networkInfo.getType()) + "连上");
                        isConnected = true;
                    } else {
                        LogUtil.e(getConnectionType(networkInfo.getType()) + "断开");
                        isConnected = false;
                    }
                }
            }
            mBus.send(new MsgEvent(MsgEvent.NETWORK_STATE_EVENT,isConnected));
        }
    }

}
