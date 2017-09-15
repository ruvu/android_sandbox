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
import com.qihancloud.opensdk.function.beans.handmotion.AbsoluteAngleHandMotion;
import com.qihancloud.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.qihancloud.opensdk.function.beans.handmotion.RelativeAngleHandMotion;
import com.qihancloud.opensdk.function.unit.HandMotionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.HandControlActivity
 * function: 手部控制
 * <p/>
 * create at 2017/5/24 14:37
 *
 * @author gangpeng
 */

public class HandControlActivity extends TopBaseActivity {

    @Bind(R.id.sv_hand_no_angle_action)
    Spinner svHandNoAngleAction;
    @Bind(R.id.sv_hand_no_angle_part)
    Spinner svHandNoAnglePart;
    @Bind(R.id.et_hand_no_angle_speed)
    EditText etHandNoAngleSpeed;
    @Bind(R.id.tv_hand_no_angle_start)
    TextView tvHandNoAngleStart;
    @Bind(R.id.sv_hand_relative_action)
    Spinner svHandRelativeAction;
    @Bind(R.id.sv_hand_relative_part)
    Spinner svHandRelativePart;
    @Bind(R.id.et_hand_relative_speed)
    EditText etHandRelativeSpeed;
    @Bind(R.id.et_hand_relative_angle)
    EditText etHandRelativeAngle;
    @Bind(R.id.tv_hand_relative_start)
    TextView tvHandRelativeStart;
    @Bind(R.id.sv_hand_absolute_part)
    Spinner svHandAbsolutePart;
    @Bind(R.id.et_hand_absolute_speed)
    EditText etHandAbsoluteSpeed;
    @Bind(R.id.et_hand_absolute_angle)
    EditText etHandAbsoluteAngle;
    @Bind(R.id.tv_hand_absolute_start)
    TextView tvHandAbsoluteStart;
    @Bind(R.id.tv_hand_no_angle_end)
    TextView tvHandNoAngleEnd;

    private HandMotionManager handMotionManager;

    /**
     * 无角度action
     */
    private byte[] noAngleAction = {NoAngleHandMotion.ACTION_UP, NoAngleHandMotion.ACTION_DOWN, NoAngleHandMotion.ACTION_RESET};
    private byte curNoAngleAction;

    /**
     * 无角度part
     */
    private byte[] noAnglePart = {NoAngleHandMotion.PART_LEFT, NoAngleHandMotion.PART_RIGHT, NoAngleHandMotion.PART_BOTH};
    private byte curNoAnglePart;

    /**
     * 相对运动action
     */
    private byte[] relativeAction = {RelativeAngleHandMotion.ACTION_UP, RelativeAngleHandMotion.ACTION_DOWN};
    private byte curRelativeAction;

    /**
     * 相对运动part
     */
    private byte[] relativePart = {RelativeAngleHandMotion.PART_LEFT, RelativeAngleHandMotion.PART_RIGHT, RelativeAngleHandMotion.PART_BOTH};
    private byte curRelativePart;

    /**
     * 绝对运动part
     */
    private byte[] absolutePart = {AbsoluteAngleHandMotion.PART_LEFT, AbsoluteAngleHandMotion.PART_RIGHT, AbsoluteAngleHandMotion.PART_BOTH};
    private byte curAbsolutePart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_control);
        ButterKnife.bind(this);
        //初始化变量
        handMotionManager = (HandMotionManager) getUnitManager(FuncConstant.HANDMOTION_MANAGER);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //无角度action
        svHandNoAngleAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curNoAngleAction = noAngleAction[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curNoAngleAction = noAngleAction[0];
            }
        });
        //无角度part
        svHandNoAnglePart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curNoAnglePart = noAnglePart[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curNoAnglePart = noAnglePart[0];
            }
        });
        //相对角度action
        svHandRelativeAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curRelativeAction = relativeAction[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curRelativeAction = relativeAction[0];
            }
        });
        //相对角度part
        svHandRelativePart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curRelativePart = relativePart[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curRelativePart = relativePart[0];
            }
        });
        //绝对角度part
        svHandAbsolutePart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curAbsolutePart = absolutePart[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curAbsolutePart = absolutePart[0];
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_hand_no_angle_start, R.id.tv_hand_relative_start, R.id.tv_hand_absolute_start, R.id.tv_hand_no_angle_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //无角度运动
            case R.id.tv_hand_no_angle_start:
                int speed;
                try {
                    speed = Integer.parseInt(etHandNoAngleSpeed.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                }
                NoAngleHandMotion noAngleHandMotion = new NoAngleHandMotion(curNoAnglePart, speed, curNoAngleAction);
                handMotionManager.doNoAngleMotion(noAngleHandMotion);
                break;
            //停止无角度运动
            case R.id.tv_hand_no_angle_end:
                try {
                    speed = Integer.parseInt(etHandNoAngleSpeed.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                }
                noAngleHandMotion = new NoAngleHandMotion(curNoAnglePart, speed, NoAngleHandMotion.ACTION_STOP);
                handMotionManager.doNoAngleMotion(noAngleHandMotion);
                break;
            //相对运动
            case R.id.tv_hand_relative_start:
                int angle;
                try {
                    speed = Integer.parseInt(etHandRelativeSpeed.getText().toString());
                    angle = Integer.parseInt(etHandRelativeAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                    angle = 0;
                }
                RelativeAngleHandMotion relativeAngleHandMotion = new RelativeAngleHandMotion(curRelativePart, speed, curRelativeAction, angle);
                handMotionManager.doRelativeAngleMotion(relativeAngleHandMotion);
                break;
            //绝对运动
            case R.id.tv_hand_absolute_start:
                try {
                    speed = Integer.parseInt(etHandAbsoluteSpeed.getText().toString());
                    angle = Integer.parseInt(etHandAbsoluteAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    speed = 5;
                    angle = 180;
                }
                AbsoluteAngleHandMotion absoluteAngleHandMotion = new AbsoluteAngleHandMotion(curAbsolutePart, speed, angle);
                handMotionManager.doAbsoluteAngleMotion(absoluteAngleHandMotion);
                break;
        }
    }
}
