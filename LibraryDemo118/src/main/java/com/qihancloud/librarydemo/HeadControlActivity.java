package com.qihancloud.librarydemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.headmotion.AbsoluteAngleHeadMotion;
import com.qihancloud.opensdk.function.beans.headmotion.LocateAbsoluteAngleHeadMotion;
import com.qihancloud.opensdk.function.beans.headmotion.LocateRelativeAngleHeadMotion;
import com.qihancloud.opensdk.function.beans.headmotion.RelativeAngleHeadMotion;
import com.qihancloud.opensdk.function.unit.HeadMotionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.HeadControlActivity
 * function: 头部控制
 * <p/>
 * create at 2017/5/23 17:37
 *
 * @author gangpeng
 */

public class HeadControlActivity extends TopBaseActivity {


    @Bind(R.id.sv_head_relative_action)
    Spinner svHeadRelativeAction;
    @Bind(R.id.et_head_relative_angle)
    EditText etHeadRelativeAngle;
    @Bind(R.id.tv_head_relative_start)
    TextView tvHeadRelativeStart;
    @Bind(R.id.tv_head_relative_end)
    TextView tvHeadRelativeEnd;
    @Bind(R.id.sv_head_absolute_action)
    Spinner svHeadAbsoluteAction;
    @Bind(R.id.et_head_absolute_angle)
    EditText etHeadAbsoluteAngle;
    @Bind(R.id.tv_head_absolute_start)
    TextView tvHeadAbsoluteStart;
    @Bind(R.id.tv_head_horizontal_lock)
    TextView tvHeadHorizontalLock;
    @Bind(R.id.sv_relative_lock_action)
    Spinner svRelativeLockAction;
    @Bind(R.id.sv_relative_horizontal_direction)
    Spinner svRelativeHorizontalDirection;
    @Bind(R.id.et_head_horizontal_angle)
    EditText etHeadHorizontalAngle;
    @Bind(R.id.sv_relative_vertical_direction)
    Spinner svRelativeVerticalDirection;
    @Bind(R.id.et_head_vertical_angle)
    EditText etHeadVerticalAngle;
    @Bind(R.id.tv_head_relative_lock_start)
    TextView tvHeadRelativeLockStart;
    @Bind(R.id.sv_absolute_lock_action)
    Spinner svAbsoluteLockAction;
    @Bind(R.id.et_head_absolute_horizontal_angle)
    EditText etHeadAbsoluteHorizontalAngle;
    @Bind(R.id.et_head_absolute_vertical_angle)
    EditText etHeadAbsoluteVerticalAngle;
    @Bind(R.id.tv_head_absolute_lock_start)
    TextView tvHeadAbsoluteLockStart;

    /**
     * 相对运动方向
     */
    private byte[] relativeAction = {RelativeAngleHeadMotion.ACTION_UP, RelativeAngleHeadMotion.ACTION_DOWN
            , RelativeAngleHeadMotion.ACTION_LEFT, RelativeAngleHeadMotion.ACTION_RIGHT, RelativeAngleHeadMotion.ACTION_LEFTUP
            , RelativeAngleHeadMotion.ACTION_RIGHTUP, RelativeAngleHeadMotion.ACTION_LEFTDOWN, RelativeAngleHeadMotion.ACTION_RIGHTDOWN,};
    /**
     * 绝对运动方向
     */
    private byte[] absoluteAction = {AbsoluteAngleHeadMotion.ACTION_HORIZONTAL, AbsoluteAngleHeadMotion.ACTION_VERTICAL};
    /**
     * 相对运动锁定
     */
    private byte[] relativeLockAction = {LocateRelativeAngleHeadMotion.ACTION_NO_LOCK, LocateRelativeAngleHeadMotion.ACTION_HORIZONTAL_LOCK
            , LocateRelativeAngleHeadMotion.ACTION_VERTICAL_LOCK, LocateRelativeAngleHeadMotion.ACTION_BOTH_LOCK};
    /**
     * 绝对运动锁定
     */
    private byte[] absoluteLockAction = {LocateAbsoluteAngleHeadMotion.ACTION_NO_LOCK, LocateAbsoluteAngleHeadMotion.ACTION_HORIZONTAL_LOCK
            , LocateAbsoluteAngleHeadMotion.ACTION_VERTICAL_LOCK, LocateAbsoluteAngleHeadMotion.ACTION_BOTH_LOCK};
    /**
     * 相对运动水平方向
     */
    private byte[] relativeLockHorizontalAction = {LocateRelativeAngleHeadMotion.DIRECTION_LEFT, LocateRelativeAngleHeadMotion.DIRECTION_RIGHT};
    /**
     * 相对运动垂直方向
     */
    private byte[] relativeLockVerticalAction = {LocateRelativeAngleHeadMotion.DIRECTION_UP, LocateRelativeAngleHeadMotion.DIRECTION_DOWN};

    private HeadMotionManager headMotionManager;
    /**
     * 当前相对运动方向
     */
    private byte curRelativeAction;
    /**
     * 当前绝对运动方向
     */
    private byte curAbsoluteAction;
    /**
     * 当前相对锁定action
     */
    private byte curRelativeLockAction;
    /**
     * 当前绝对锁定action
     */
    private byte curAbsoluteLockAction;
    /**
     * 当前锁定水平方向
     */
    private byte curHorizontalLockAction;
    /**
     * 当前锁定垂直方向
     */
    private byte curVerticalLockAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_control);
        ButterKnife.bind(this);
        //初始化变量
        headMotionManager = (HeadMotionManager) getUnitManager(FuncConstant.HEADMOTION_MANAGER);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //相对运动方向下拉框
        svHeadRelativeAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < relativeAction.length) {
                    curRelativeAction = relativeAction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curRelativeAction = RelativeAngleHeadMotion.ACTION_UP;
            }
        });
        //绝对运动方向下拉框
        svHeadAbsoluteAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < absoluteAction.length) {
                    curAbsoluteAction = absoluteAction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curAbsoluteAction = AbsoluteAngleHeadMotion.ACTION_HORIZONTAL;
            }
        });
        //相对运动锁定下拉框
        svRelativeLockAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < relativeLockAction.length) {
                    curRelativeLockAction = relativeLockAction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curRelativeLockAction = LocateRelativeAngleHeadMotion.ACTION_NO_LOCK;
            }
        });
        //相对运动水平方向下拉框
        svRelativeHorizontalDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < relativeLockHorizontalAction.length) {
                    curHorizontalLockAction = relativeLockHorizontalAction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curHorizontalLockAction = relativeLockHorizontalAction[0];
            }
        });
        //相对运动垂直方向下拉框
        svRelativeVerticalDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < relativeLockVerticalAction.length) {
                    curVerticalLockAction = relativeLockVerticalAction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curVerticalLockAction = relativeLockVerticalAction[0];
            }
        });
        //绝对角度定位action
        svAbsoluteLockAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < absoluteLockAction.length) {
                    curAbsoluteLockAction = absoluteLockAction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                curAbsoluteLockAction = absoluteLockAction[0];
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_head_relative_start, R.id.tv_head_relative_end, R.id.tv_head_absolute_start, R.id.tv_head_horizontal_lock
            , R.id.tv_head_relative_lock_start, R.id.tv_head_absolute_lock_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //开始相对运动
            case R.id.tv_head_relative_start:
                int angle;
                try {
                    angle = Integer.parseInt(etHeadRelativeAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HeadControlActivity:", "please input number");
                    angle = 0;
                }
                RelativeAngleHeadMotion relativeAngleHeadMotion = new RelativeAngleHeadMotion(curRelativeAction
                        , angle);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            //结束相对运动
            case R.id.tv_head_relative_end:
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_STOP
                        , 0);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            //开始绝对运动
            case R.id.tv_head_absolute_start:
                try {
                    angle = Integer.parseInt(etHeadAbsoluteAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HeadControlActivity:", "please input number");
                    angle = 90;
                }
                AbsoluteAngleHeadMotion absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(curAbsoluteAction
                        , angle);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            //水平居中锁定
            case R.id.tv_head_horizontal_lock:
                headMotionManager.dohorizontalCenterLockMotion();
                break;
            //相对角度锁定
            case R.id.tv_head_relative_lock_start:
                int horizontalAngle = 0;
                int verticalAngle = 0;
                try {
                    horizontalAngle = Integer.parseInt(etHeadHorizontalAngle.getText().toString());
                    verticalAngle = Integer.parseInt(etHeadVerticalAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HeadControlActivity:", "please input number");
                    horizontalAngle = 0;
                    verticalAngle = 0;
                }
                Log.e("pgq", "action: " + curRelativeAction + ", horizontalAngle: " + horizontalAngle + ", verticalAngle: " + verticalAngle
                        + ", horizontalAction: " + curHorizontalLockAction + ", verticalAction: " + curVerticalLockAction);
                LocateRelativeAngleHeadMotion locateRelativeAngleHeadMotion = new LocateRelativeAngleHeadMotion(curRelativeLockAction
                        , horizontalAngle, verticalAngle, curHorizontalLockAction, curVerticalLockAction);
                headMotionManager.doRelativeLocateMotion(locateRelativeAngleHeadMotion);
                break;
            //绝对角度定位
            case R.id.tv_head_absolute_lock_start:
                try {
                    horizontalAngle = Integer.parseInt(etHeadAbsoluteHorizontalAngle.getText().toString());
                    verticalAngle = Integer.parseInt(etHeadAbsoluteVerticalAngle.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HeadControlActivity:", "please input number");
                    horizontalAngle = 90;
                    verticalAngle = 15;
                }
                LocateAbsoluteAngleHeadMotion locateAbsoluteAngleHeadMotion = new LocateAbsoluteAngleHeadMotion(curAbsoluteLockAction
                        , horizontalAngle, verticalAngle);
                headMotionManager.doAbsoluteLocateMotion(locateAbsoluteAngleHeadMotion);
                break;
        }
    }
}
