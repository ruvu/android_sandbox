package com.qihancloud.librarydemo;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.WheelControlActivity
 * function: 轮子控制
 * <p/>
 * create at 2017/5/24 15:37
 *
 * @author gangpeng
 */

public class WheelControlActivity extends TopBaseActivity {


    @Bind(R.id.sv_wheel_no_angle_action)
    Spinner svWheelNoAngleAction;
    @Bind(R.id.et_wheel_no_angle_speed)
    EditText etWheelNoAngleSpeed;
    @Bind(R.id.et_wheel_no_angle_duration)
    EditText etWheelNoAngleDuration;
    @Bind(R.id.tv_wheel_no_angle_start)
    TextView tvWheelNoAngleStart;
    @Bind(R.id.tv_wheel_no_angle_end_turn)
    TextView tvWheelNoAngleEndTurn;
    @Bind(R.id.tv_wheel_no_angle_end_run)
    TextView tvWheelNoAngleEndRun;
    @Bind(R.id.sv_wheel_relative_action)
    Spinner svWheelRelativeAction;
    @Bind(R.id.et_wheel_relative_speed)
    EditText etWheelRelativeSpeed;
    @Bind(R.id.et_wheel_relative_angle)
    EditText etWheelRelativeAngle;
    @Bind(R.id.tv_wheel_relative_start)
    TextView tvWheelRelativeStart;
    @Bind(R.id.tv_wheel_relative_end)
    TextView tvWheelRelativeEnd;
    @Bind(R.id.et_wheel_distance_speed)
    EditText etWheelDistanceSpeed;
    @Bind(R.id.et_wheel_distance)
    EditText etWheelDistance;
    @Bind(R.id.tv_wheel_distance_start)
    TextView tvWheelDistanceStart;
    @Bind(R.id.tv_wheel_distance_end)
    TextView tvWheelDistanceEnd;

    private WheelMotionManager wheelMotionManager;

    /**
     * 无角度运动action
     */
    private byte[] noAngleAction = {NoAngleWheelMotion.ACTION_FORWARD_RUN, NoAngleWheelMotion.ACTION_LEFT_CIRCLE
            , NoAngleWheelMotion.ACTION_RIGHT_CIRCLE, NoAngleWheelMotion.ACTION_TURN_LEFT, NoAngleWheelMotion.ACTION_TURN_RIGHT};
    private byte curNoAngleAction;

    /**
     * 相对运动action
     */
    private byte[] relativeAction = {RelativeAngleWheelMotion.TURN_LEFT, RelativeAngleWheelMotion.TURN_RIGHT};
    private byte curRelativeAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_control);
        ButterKnife.bind(this);
        //初始化变量
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //无角度运动action
        svWheelNoAngleAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curNoAngleAction = noAngleAction[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curNoAngleAction = noAngleAction[0];
            }
        });
        //相对运动action
        svWheelRelativeAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curRelativeAction = relativeAction[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curRelativeAction = relativeAction[0];
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_wheel_no_angle_start, R.id.tv_wheel_no_angle_end_turn, R.id.tv_wheel_no_angle_end_run
            , R.id.tv_wheel_relative_start, R.id.tv_wheel_relative_end, R.id.tv_wheel_distance_start, R.id.tv_wheel_distance_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //无角度运动
            case R.id.tv_wheel_no_angle_start:
                int speed;
                int duration;
                try {
                    speed = Integer.parseInt(etWheelNoAngleSpeed.getText().toString());
                    duration = Integer.parseInt(etWheelNoAngleDuration.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                    duration = 10;
                }
                NoAngleWheelMotion noAngleWheelMotion = new NoAngleWheelMotion(curNoAngleAction, speed, duration);
                wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
                break;
            //无角度停止转圈
            case R.id.tv_wheel_no_angle_end_turn:
                noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_STOP_TURN, 1, 0);
                wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
                break;
            //无角度停止行走
            case R.id.tv_wheel_no_angle_end_run:
                noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_STOP_RUN, 1, 1);
                wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
                break;
            //相对角度运动
            case R.id.tv_wheel_relative_start:
                int angle;
                try {
                    speed = Integer.parseInt(etWheelRelativeSpeed.getText().toString());
                    angle = Integer.parseInt(etWheelRelativeAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                    angle = 90;
                }
                RelativeAngleWheelMotion relativeAngleWheelMotion = new RelativeAngleWheelMotion(curRelativeAction, speed, angle);
                wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);
                break;
            //停止相对角度运动
            case R.id.tv_wheel_relative_end:
                relativeAngleWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_STOP, 1, 1);
                wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);
                break;
            //距离运动
            case R.id.tv_wheel_distance_start:
                int distance;
                try {
                    speed = Integer.parseInt(etWheelDistanceSpeed.getText().toString());
                    distance = Integer.parseInt(etWheelDistance.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                    distance = 10;
                }
                DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, speed, distance);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);
                break;
            //结束距离运动
            case R.id.tv_wheel_distance_end:
                distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_STOP_RUN, 1, 1);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);
                break;
        }
    }
}
