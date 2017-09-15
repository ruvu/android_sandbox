package com.qihancloud.librarydemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.ProjectorManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.ProjectorControlActivity
 * function:
 * <p/>
 * create at 2017/5/25 14:25
 *
 * @author gangpeng
 */

public class ProjectorControlActivity extends TopBaseActivity {

    @Bind(R.id.tv_open_projector)
    TextView tvOpenProjector;
    @Bind(R.id.tv_close_projector)
    TextView tvCloseProjector;

    ProjectorManager projectorManager;
    @Bind(R.id.sv_mirror)
    Spinner svMirror;
    @Bind(R.id.et_trapezoid_horizontal)
    EditText etTrapezoidHorizontal;
    @Bind(R.id.et_trapezoid_vertical)
    EditText etTrapezoidVertical;
    @Bind(R.id.et_contrast)
    EditText etContrast;
    @Bind(R.id.et_bright)
    EditText etBright;
    @Bind(R.id.et_color)
    EditText etColor;
    @Bind(R.id.et_saturation)
    EditText etSaturation;
    @Bind(R.id.ex_acuity)
    EditText exAcuity;
    @Bind(R.id.sv_mode)
    Spinner svMode;
    @Bind(R.id.status)
    TextView status;

    /**
     * 镜像模式
     */
    private int[] mirrorMode = {ProjectorManager.MIRROR_CLOSE, ProjectorManager.MIRROR_LR, ProjectorManager.MIRROR_UD, ProjectorManager.MIRROR_ALL};
    /**
     * 投影模式
     */
    private int[] mode = {ProjectorManager.MODE_WALL, ProjectorManager.MODE_CEILING};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actviity_projector_control);
        ButterKnife.bind(this);
        //初始化变量
        projectorManager = (ProjectorManager) getUnitManager(FuncConstant.PROJECTOR_MANAGER);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //设置镜像模式
        svMirror.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectorManager.setMirror(mirrorMode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                projectorManager.setMirror(mirrorMode[0]);
            }
        });
        //设置投影仪梯形水平校正值
        etTrapezoidHorizontal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setTrapezoidH(num);
            }
        });
        //设置投影仪梯形垂直校正值
        etTrapezoidVertical.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setTrapezoidV(num);
            }
        });
        //设置投影仪对比度
        etContrast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setContrast(num);
            }
        });
        //设置投影仪亮度
        etBright.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setBright(num);
            }
        });
        //设置投影仪色值
        etColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setColor(num);
            }
        });
        //设置投影仪画面饱和度
        etSaturation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setSaturation(num);
            }
        });
        //设置投影仪画面锐度
        exAcuity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num;
                try {
                    num = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                projectorManager.setAcuity(num);
            }
        });
        //设置投影仪模式
        svMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectorManager.setMode(mode[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                projectorManager.setMode(mode[0]);
            }
        });
    }


    @Override
    protected void onMainServiceConnected() {

    }

    @OnClick({R.id.tv_open_projector, R.id.tv_close_projector, R.id.status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open_projector:
                projectorManager.switchProjector(true);
                break;
            case R.id.tv_close_projector:
                projectorManager.switchProjector(false);
                break;
        }
    }
}
