package com.qihancloud.librarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.LED;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.GyroscopeListener;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.PIRListener;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.TouchSensorListener;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.VoiceLocateListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.HardwareControlActivity
 * function: 硬件控制
 * <p/>
 * create at 2017/5/22 16:37
 *
 * @author gangpeng
 */

public class HardwareControlActivity extends TopBaseActivity {


    @Bind(R.id.rb_one)
    RadioButton rbOne;
    @Bind(R.id.rb_two)
    RadioButton rbTwo;
    @Bind(R.id.rb_three)
    RadioButton rbThree;
    @Bind(R.id.rg_level)
    RadioGroup rgLevel;
    @Bind(R.id.tv_open_white_light)
    TextView tvOpenWhiteLight;
    @Bind(R.id.tv_close_white_light)
    TextView tvCloseWhiteLight;
    @Bind(R.id.tv_open_led_light)
    TextView tvOpenLedLight;
    @Bind(R.id.tv_close_led_light)
    TextView tvCloseLedLight;
    @Bind(R.id.tv_open_black_filter)
    TextView tvOpenBlackFilter;
    @Bind(R.id.tv_close_black_filter)
    TextView tvCloseBlackFilter;
    @Bind(R.id.tv_touch_sensor)
    TextView tvTouchSensor;
    @Bind(R.id.tv_voice_locate_angle)
    TextView tvVoiceLocateAngle;
    @Bind(R.id.tv_pir_sensor)
    TextView tvPirSensor;
    @Bind(R.id.tv_ri_sensor)
    TextView tvRiSensor;
    @Bind(R.id.tv_gyroscope_result)
    TextView tvGyroscopeResult;
    @Bind(R.id.sv_led_part)
    Spinner svLedPart;
    @Bind(R.id.sv_led_mode)
    Spinner svLedMode;
    @Bind(R.id.et_led_duration)
    EditText etLedDuration;
    @Bind(R.id.et_led_random)
    EditText etLedRandom;

    private HardWareManager hardWareManager;

    /**
     * led灯
     */
    private byte[] ledPart = {LED.PART_ALL, LED.PART_WHEEL, LED.PART_LEFT_HAND, LED.PART_RIGHT_HAND, LED.PART_LEFT_HEAD, LED.PART_RIGHT_HEAD};
    private byte curLedPart;

    /**
     * led模式
     */
    private byte[] ledMode = {LED.MODE_WHITE, LED.MODE_RED, LED.MODE_GREEN, LED.MODE_PINK, LED.MODE_PURPLE, LED.MODE_BLUE
            , LED.MODE_YELLOW, LED.MODE_FLICKER_WHITE, LED.MODE_FLICKER_RED, LED.MODE_FLICKER_GREEN, LED.MODE_FLICKER_PINK
            , LED.MODE_FLICKER_PURPLE, LED.MODE_FLICKER_BLUE, LED.MODE_FLICKER_YELLOW, LED.MODE_FLICKER_RANDOM};
    private byte curLedMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actviity_hardware_control);
        ButterKnife.bind(this);
        //初始化变量
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //白光灯亮度控制，只能在亮的时候控制
        rgLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_one) {
                    hardWareManager.setWhiteLightLevel(1);
                } else if (checkedId == R.id.rb_two) {
                    hardWareManager.setWhiteLightLevel(2);
                } else if (checkedId == R.id.rb_three) {
                    hardWareManager.setWhiteLightLevel(3);
                }
            }
        });
        //触摸事件回调
        hardWareManager.setOnHareWareListener(
                new TouchSensorListener() {
                    @Override
                    public void onTouch(int part) {
                        switch (part) {
                            case 1:
                                tvTouchSensor.setText(R.string.touch_jaw_right);
                                break;
                            case 2:
                                tvTouchSensor.setText(R.string.touch_jaw_left);
                                break;
                            case 3:
                                tvTouchSensor.setText(R.string.touch_chest_left);
                                break;
                            case 4:
                                tvTouchSensor.setText(R.string.touch_chest_right);
                                break;
                            case 5:
                                tvTouchSensor.setText(R.string.touch_back_head_left);
                                break;
                            case 6:
                                tvTouchSensor.setText(R.string.touch_back_head_right);
                                break;
                            case 7:
                                tvTouchSensor.setText(R.string.touch_back_left);
                                break;
                            case 8:
                                tvTouchSensor.setText(R.string.touch_back_right);
                                break;
                            case 9:
                                tvTouchSensor.setText(R.string.touch_hand_left);
                                break;
                            case 10:
                                tvTouchSensor.setText(R.string.touch_hand_right);
                                break;
                            case 11:
                                tvTouchSensor.setText(R.string.touch_head_middle);
                                break;
                            case 12:
                                tvTouchSensor.setText(R.string.touch_head_left);
                                break;
                            case 13:
                                tvTouchSensor.setText(R.string.touch_head_right);
                                break;
                        }
                    }
                }
        );

        //声源定位回调
        hardWareManager.setOnHareWareListener(new VoiceLocateListener() {
            @Override
            public void voiceLocateResult(int angle) {
                tvVoiceLocateAngle.setText(getString(R.string.voice_locate_angle) + angle);
            }
        });

        //pir回调
        hardWareManager.setOnHareWareListener(new PIRListener() {
            @Override
            public void onPIRCheckResult(boolean isChecked, int part) {
                if (isChecked) {
                    tvPirSensor.setText(part == 1 ? R.string.trigger_front_pir : R.string.trigger_back_pir);
                }
            }
        });
        //陀螺仪回调
        hardWareManager.setOnHareWareListener(new GyroscopeListener() {
            @Override
            public void gyroscopeData(float v, float v1, float v2) {
                Log.d("pgq", "first: " + v + ", second: " + v1 + ", third: " + v2);
                tvGyroscopeResult.setText(String.format(getResources().getString(R.string.gyroscope_result), v, v1, v2));
            }
        });
        //设置led模式
        svLedMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curLedMode = ledMode[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curLedMode = ledMode[0];
            }
        });
        //设置led位置
        svLedPart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curLedPart = ledPart[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curLedPart = ledPart[0];
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_open_white_light, R.id.tv_close_white_light, R.id.tv_open_led_light, R.id.tv_close_led_light
            , R.id.tv_open_black_filter, R.id.tv_close_black_filter, R.id.tv_ri_sensor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //打开白光灯
            case R.id.tv_open_white_light:
                hardWareManager.switchWhiteLight(true);
                break;
            //关闭白光灯
            case R.id.tv_close_white_light:
                hardWareManager.switchWhiteLight(false);
                break;
            //打开led灯
            case R.id.tv_open_led_light:
                byte duration;
                byte random;
                try {
                    duration = Byte.parseByte(etLedDuration.getText().toString());
                    random = Byte.parseByte(etLedRandom.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    duration = 1;
                    random = 1;
                }
                hardWareManager.setLED(new LED(curLedPart, curLedMode, duration, random));
                break;
            //关闭led灯
            case R.id.tv_close_led_light:
                hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_CLOSE, (byte) 1, (byte) 1));
                break;
            //打开黑线过滤
            case R.id.tv_open_black_filter:
                hardWareManager.switchBlackLineFilter(true);
                break;
            //关闭黑线过滤
            case R.id.tv_close_black_filter:
                hardWareManager.switchBlackLineFilter(false);
                break;
            //关闭黑线过滤
            case R.id.tv_ri_sensor:
                Intent intent = new Intent(HardwareControlActivity.this, IRSensorActivity.class);
                startActivity(intent);
                break;
        }
    }
}
