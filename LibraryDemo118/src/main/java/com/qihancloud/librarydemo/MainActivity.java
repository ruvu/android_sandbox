package com.qihancloud.librarydemo;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends TopBaseActivity {

    @Bind(R.id.btn_white_light)
    Button whiteLightBtn;

    @Bind(R.id.btn_move_forward)
    Button moveForwardBtn;

    @Bind(R.id.btn_move_right)
    Button moveRightBtn;

    @Bind(R.id.btn_move_left)
    Button moveLeftBtn;

    MainService mainService;

    boolean isOpen = false;
    HardWareManager hardWareManager;
    WheelMotionManager wheelMotionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(128, 128);
        setBodyView(R.layout.activity_main);
        ButterKnife.bind(this);

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);

        /**
         * Turn on and turn off the robot white light
         */
        whiteLightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    whiteLightBtn.setText(R.string.close_light);
                    hardWareManager.switchWhiteLight(true);
                } else {
                    whiteLightBtn.setText(R.string.open_light);
                    hardWareManager.switchWhiteLight(false);
                }
                isOpen = !isOpen;
            }
        });

        /**
         * Move forward for some fixed time
         */
        moveForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistanceWheelMotion distanceWheelMotion =
                        new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, 1, 1);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);
            }
        });

        /**
         * Move right for some fixed time
         */
        moveRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistanceWheelMotion distanceWheelMotion =
                        new DistanceWheelMotion(DistanceWheelMotion.ACTION_RIGHT_FORWARD_RUN, 1, 1);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);
            }
        });

        /**
         * Move left for some fixed time
         */
        moveLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistanceWheelMotion distanceWheelMotion =
                        new DistanceWheelMotion(DistanceWheelMotion.ACTION_LEFT_FORWARD_RUN, 1, 1);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {
        Log.i("log", getString(R.string.mainservice_version) + " main service connected.");
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainService.MyBinder binder = (MainService.MyBinder) service;
            mainService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onDestroy() {
        if (mainService != null) {
            unbindService(connection);
        }
        super.onDestroy();
    }
}
