package com.qihancloud.librarydemo;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.EmotionsType;
import com.qihancloud.opensdk.function.unit.SystemManager;
import com.qihancloud.opensdk.function.unit.interfaces.IDarlingListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.SystemControlActivity
 * function: 系统控制
 * <p/>
 * create at 2017/5/23 10:37
 *
 * @author gangpeng
 */

public class SystemControlActivity extends TopBaseActivity {


    @Bind(R.id.tv_robot_id)
    TextView tvRobotId;
    @Bind(R.id.tv_main_server_version)
    TextView tvMainServerVersion;
    @Bind(R.id.tv_main_server_version_result)
    TextView tvMainServerVersionResult;
    @Bind(R.id.tv_battery_level)
    TextView tvBatteryLevel;
    @Bind(R.id.tv_battery_level_result)
    TextView tvBatteryLevelResult;
    @Bind(R.id.tv_battery_state)
    TextView tvBatteryState;
    @Bind(R.id.tv_battery_state_result)
    TextView tvBatteryStateResult;
    @Bind(R.id.tv_emotion_control)
    TextView tvEmotionControl;
    @Bind(R.id.tv_back_screen_saver)
    TextView tvBackScreenSaver;
    @Bind(R.id.tv_safe_home_state)
    TextView tvSafeHomeState;
    @Bind(R.id.tv_show_floating)
    TextView tvShowFloating;
    @Bind(R.id.tv_hide_floating)
    TextView tvHideFloating;

    private SystemManager systemManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_control);
        ButterKnife.bind(this);
        //初始化变量
        systemManager = (SystemManager) getUnitManager(FuncConstant.SYSTEM_MANAGER);

        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        tvRobotId.setText(systemManager.getDeviceId());
        systemManager.setOnIDarlingListener(new IDarlingListener() {
            @Override
            public void onAlarm(int i) {
                if (i == 1) {
                    tvSafeHomeState.setText(R.string.shadow_alarm);
                } else if (i == 2) {
                    tvSafeHomeState.setText(R.string.invasion_alarm);
                } else if (i == 3) {
                    tvSafeHomeState.setText(R.string.boundary_violation_alarm);
                }
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_battery_level, R.id.tv_battery_state, R.id.tv_emotion_control, R.id.tv_back_screen_saver
            , R.id.tv_main_server_version, R.id.tv_show_floating, R.id.tv_hide_floating})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //电量
            case R.id.tv_battery_level:
                tvBatteryLevelResult.setText(systemManager.getBatteryValue() + "%");
                break;
            //充电状态
            case R.id.tv_battery_state:
                if (systemManager.getBatteryStatus() == SystemManager.STATUS_NORMAL) {
                    tvBatteryStateResult.setText(R.string.no_charging);
                } else if (systemManager.getBatteryStatus() == SystemManager.STATUS_CHARGE_LINE) {
                    tvBatteryStateResult.setText(R.string.charging_by_wire);
                } else if (systemManager.getBatteryStatus() == SystemManager.STATUS_CHARGE_PILE) {
                    tvBatteryStateResult.setText(R.string.fail_no_charge_pile);
                }
                break;
            //表情控制，设置表情为大笑
            case R.id.tv_emotion_control:
                systemManager.showEmotion(EmotionsType.LAUGHTER);
                break;
            //返回屏幕保护动画
            case R.id.tv_back_screen_saver:
                systemManager.doHomeAction();
                break;
            //返回主控版本号
            case R.id.tv_main_server_version:
                tvMainServerVersionResult.setText(systemManager.getMainServiceVersion());
                break;
            //显示悬浮框
            case R.id.tv_show_floating:
                systemManager.switchFloatBar(true, SystemControlActivity.class.getName());
                break;
            //隐藏悬浮框
            case R.id.tv_hide_floating:
                systemManager.switchFloatBar(false, SystemControlActivity.class.getName());
                break;
        }
    }
}
