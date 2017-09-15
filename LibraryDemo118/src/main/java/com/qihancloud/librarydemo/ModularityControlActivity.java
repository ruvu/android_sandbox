package com.qihancloud.librarydemo;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.beans.OperationResult;
import com.qihancloud.opensdk.function.unit.ModularMotionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.ModularityControlActivity
 * function: 模块化控制
 * <p/>
 * create at 2017/5/25 14:25
 *
 * @author gangpeng
 */

public class ModularityControlActivity extends TopBaseActivity {


    @Bind(R.id.tv_open_wander)
    TextView tvOpenWander;
    @Bind(R.id.tv_close_wander)
    TextView tvCloseWander;
    @Bind(R.id.tv_open_auto_charging)
    TextView tvOpenAutoCharging;
    @Bind(R.id.tv_close_auto_charging)
    TextView tvCloseAutoCharging;
    @Bind(R.id.tv_wander_state)
    TextView tvWanderState;
    @Bind(R.id.tv_auto_wander_result)
    TextView tvAutoWanderResult;
    @Bind(R.id.tv_auto_charging_state)
    TextView tvAutoChargingState;
    @Bind(R.id.tv_auto_charging_state_result)
    TextView tvAutoChargingStateResult;

    ModularMotionManager modularMotionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modularity_control);
        ButterKnife.bind(this);
        //初始化变量
        modularMotionManager = (ModularMotionManager) getUnitManager(FuncConstant.MODULARMOTION_MANAGER);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
    }


    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_open_wander, R.id.tv_close_wander, R.id.tv_open_auto_charging, R.id.tv_close_auto_charging, R.id.tv_wander_state, R.id.tv_auto_charging_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //开启自由行走
            case R.id.tv_open_wander:
                OperationResult operationResult = modularMotionManager.switchWander(true);
                Toast.makeText(ModularityControlActivity.this, operationResult.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            //关闭自由行走
            case R.id.tv_close_wander:
                operationResult = modularMotionManager.switchWander(false);
                Toast.makeText(ModularityControlActivity.this, operationResult.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            //开启自动充电
            case R.id.tv_open_auto_charging:
                operationResult = modularMotionManager.switchCharge(true);
                Toast.makeText(ModularityControlActivity.this, operationResult.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            //关闭自动充电
            case R.id.tv_close_auto_charging:
                operationResult = modularMotionManager.switchCharge(false);
                Toast.makeText(ModularityControlActivity.this, operationResult.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            //自由行走状态
            case R.id.tv_wander_state:
                operationResult = modularMotionManager.getWanderStatus();
                if (operationResult.getResult().equals("1")) {
                    tvAutoWanderResult.setText(R.string.open);
                } else {
                    tvAutoWanderResult.setText(R.string.close);
                }
                break;
            //自动充电状态
            case R.id.tv_auto_charging_state:
                operationResult = modularMotionManager.getAutoChargeStatus();
                if (operationResult.getResult().equals("1")) {
                    tvAutoChargingStateResult.setText(R.string.open);
                } else {
                    tvAutoChargingStateResult.setText(R.string.close);
                }
                break;
        }
    }
}
