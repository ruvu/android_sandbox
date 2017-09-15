package com.qihancloud.librarydemo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.InfrareListener;

import butterknife.ButterKnife;

/**
 * className: com.qihancloud.librarydemo.IRSensorActivity
 * function: IR sensor
 * <p/>
 * create at 2017/5/23 11:16
 *
 * @author gangpeng
 */
public class IRSensorActivity extends TopBaseActivity {

    private HardWareManager hardWareManager;

    private TextView ir1_tv;
    private TextView ir3_tv;
    private TextView ir2_tv;
    private TextView ir4_tv;
    private TextView ir5_tv;
    private TextView ir6_tv;
    private TextView ir7_tv;
    private TextView ir8_tv;
    private TextView ir9_tv;
    private TextView ir10_tv;
    private TextView ir11_tv;
    private TextView ir12_tv;
    private TextView ir13_tv;
    private TextView ir14_tv;
    private TextView ir16_tv;
    private TextView ir17_tv;
    private TextView ir15_tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setBodyView(R.layout.activity_ir);
        ButterKnife.bind(this);

        //设置顶部状态栏颜色
        GradientDrawable topDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT
                , new int[]{Color.parseColor("#2B6DF8"), Color.parseColor("#00A2ED")});
        setHeadBackground(topDrawable);
        //初始化变量
        hardWareManager = (HardWareManager) this.getUnitManager(FuncConstant.HARDWARE_MANAGER);
        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ir1_tv = (TextView) findViewById(R.id.ir1_tv);
        ir2_tv = (TextView) findViewById(R.id.ir2_tv);
        ir3_tv = (TextView) findViewById(R.id.ir3_tv);
        ir4_tv = (TextView) findViewById(R.id.ir4_tv);
        ir5_tv = (TextView) findViewById(R.id.ir5_tv);
        ir6_tv = (TextView) findViewById(R.id.ir6_tv);
        ir7_tv = (TextView) findViewById(R.id.ir7_tv);
        ir8_tv = (TextView) findViewById(R.id.ir8_tv);
        ir9_tv = (TextView) findViewById(R.id.ir9_tv);
        ir10_tv = (TextView) findViewById(R.id.ir10_tv);
        ir11_tv = (TextView) findViewById(R.id.ir11_tv);
        ir12_tv = (TextView) findViewById(R.id.ir12_tv);
        ir13_tv = (TextView) findViewById(R.id.ir13_tv);
        ir14_tv = (TextView) findViewById(R.id.ir14_tv);
        ir15_tv = (TextView) findViewById(R.id.ir15_tv);
        ir16_tv = (TextView) findViewById(R.id.ir16_tv);
        ir17_tv = (TextView) findViewById(R.id.ir17_tv);

        hardWareManager.setOnHareWareListener(new InfrareListener() {
            @Override
            public void infrareDistance(int part, int distance) {
                switch (part) {
                    case 1:
                        ir1_tv.setText(String.valueOf(distance));
                        break;
                    case 2:
                        ir2_tv.setText(String.valueOf(distance));
                        break;
                    case 3:
                        ir3_tv.setText(String.valueOf(distance));
                        break;
                    case 4:
                        ir4_tv.setText(String.valueOf(distance));
                        break;
                    case 5:
                        ir5_tv.setText(String.valueOf(distance));
                        break;
                    case 6:
                        ir6_tv.setText(String.valueOf(distance));
                        break;
                    case 7:
                        ir7_tv.setText(String.valueOf(distance));
                        break;
                    case 8:
                        ir8_tv.setText(String.valueOf(distance));
                        break;
                    case 9:
                        ir9_tv.setText(String.valueOf(distance));
                        break;
                    case 10:
                        ir10_tv.setText(String.valueOf(distance));
                        break;
                    case 11:
                        ir11_tv.setText(String.valueOf(distance));
                        break;
                    case 12:
                        ir12_tv.setText(String.valueOf(distance));
                        break;
                    case 13:
                        ir13_tv.setText(String.valueOf(distance));
                        break;
                    case 14:
                        ir14_tv.setText(String.valueOf(distance));
                        break;
                    case 15:
                        ir15_tv.setText(String.valueOf(distance));
                        break;
                    case 16:
                        ir16_tv.setText(String.valueOf(distance));
                        break;
                    case 17:
                        ir17_tv.setText(String.valueOf(distance));
                        break;
                }
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }
}
