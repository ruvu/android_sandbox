package com.qihancloud.librarydemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.HomeActivity
 * function: 功能菜单
 * <p/>
 * create at 2017/5/22 10:31
 *
 * @author gangpeng
 */

public class HomeActivity extends TopBaseActivity {

    /**
     * 标题
     */
    @Bind(R.id.tv_title)
    TextView tvTitle;
    /**
     * 语音控制
     */
    @Bind(R.id.tv_speech_control)
    TextView tvSpeechControl;
    /**
     * 硬件控制
     */
    @Bind(R.id.tv_hardware_control)
    TextView tvHardwareControl;
    /**
     * 头部控制
     */
    @Bind(R.id.tv_head_control)
    TextView tvHeadControl;
    /**
     * 手部控制
     */
    @Bind(R.id.tv_hand_control)
    TextView tvHandControl;
    /**
     * 轮子控制
     */
    @Bind(R.id.tv_wheel_control)
    TextView tvWheelControl;
    /**
     * 系统控制
     */
    @Bind(R.id.tv_system_control)
    TextView tvSystemControl;
    /**
     * 投影仪控制
     */
    @Bind(R.id.tv_projector_control)
    TextView tvProjectorControl;
    /**
     * 多媒体控制
     */
    @Bind(R.id.tv_media_control)
    TextView tvMediaControl;
    /**
     * 模块化运动控制
     */
    @Bind(R.id.tv_modularity_control)
    TextView tvModularityControl;
    /**
     * 预处理指令
     */
    @Bind(R.id.tv_preprocessor_control)
    TextView tvPreprocessorControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setBodyView(R.layout.activity_home);
        ButterKnife.bind(this);

        //设置顶部状态栏颜色及标题
        GradientDrawable topDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT
                , new int[]{Color.parseColor("#2B6DF8"), Color.parseColor("#00A2ED")});
        setHeadBackground(topDrawable);
        tvTitle.setText(R.string.title);


    }

    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_speech_control, R.id.tv_hardware_control, R.id.tv_head_control, R.id.tv_hand_control, R.id.tv_wheel_control, R.id.tv_system_control, R.id.tv_projector_control, R.id.tv_media_control, R.id.tv_modularity_control, R.id.tv_preprocessor_control, R.id.tv_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_speech_control:
                Intent intent = new Intent(HomeActivity.this, SpeechControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_hardware_control:
                intent = new Intent(HomeActivity.this, HardwareControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_head_control:
                intent = new Intent(HomeActivity.this, HeadControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_hand_control:
                intent = new Intent(HomeActivity.this, HandControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_wheel_control:
                intent = new Intent(HomeActivity.this, WheelControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_system_control:
                intent = new Intent(HomeActivity.this, SystemControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_projector_control:
                intent = new Intent(HomeActivity.this, ProjectorControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_media_control:
                intent = new Intent(HomeActivity.this, MediaControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_modularity_control:
                intent = new Intent(HomeActivity.this, ModularityControlActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_main:
                intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_preprocessor_control:
                intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
