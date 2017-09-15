package com.qihancloud.librarydemo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.SpeakOption;
import com.qihancloud.opensdk.function.beans.speech.Grammar;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.SpeechManager;
import com.qihancloud.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.qihancloud.opensdk.function.unit.interfaces.speech.SpeakListener;
import com.qihancloud.opensdk.function.unit.interfaces.speech.WakenListener;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.SpeechControlActivity
 * function: 语音管理
 * 1. 语音识别拦截，需要在AndroidMainfest中配置RECOGNIZE_MODE为1，且回调方法返回true
 * 2. 语音合成时，英文语言无法识别出中文
 * <p/>
 * create at 2017/5/22 11:37
 *
 * @author gangpeng
 */

public class SpeechControlActivity extends TopBaseActivity {

    @Bind(R.id.et_text)
    EditText etText;
    @Bind(R.id.rb_english)
    RadioButton rbEnglish;
    @Bind(R.id.rb_chinese)
    RadioButton rbChinese;
    @Bind(R.id.rg_lang)
    RadioGroup rgLang;
    @Bind(R.id.et_speed)
    EditText etSpeed;
    @Bind(R.id.et_tone)
    EditText etTone;
    @Bind(R.id.tv_speech_synthesize_start)
    TextView tvSpeechSynthesizeStart;
    @Bind(R.id.tv_speech_synthesize_pause)
    TextView tvSpeechSynthesizePause;
    @Bind(R.id.tv_speech_synthesize_continue)
    TextView tvSpeechSynthesizeContinue;
    @Bind(R.id.tv_speech_synthesize_stop)
    TextView tvSpeechSynthesizeStop;
    @Bind(R.id.tv_speech_synthesize_finish)
    TextView tvSpeechSynthesizeFinish;
    @Bind(R.id.tv_speech_synthesize_progress)
    TextView tvSpeechSynthesizeProgress;
    @Bind(R.id.tv_speech_sleep)
    TextView tvSpeechSleep;
    @Bind(R.id.tv_speech_wakeup)
    TextView tvSpeechWakeup;
    @Bind(R.id.tv_speech_status)
    TextView tvSpeechStatus;
    @Bind(R.id.tv_speech_recognize_volume)
    TextView tvSpeechRecognizeVolume;
    @Bind(R.id.tv_speech_recognize_result)
    TextView tvSpeechRecognizeResult;
    @Bind(R.id.cb_intercept_message)
    CheckBox cbInterceptMessage;
    @Bind(R.id.tv_speech_speaking)
    TextView tvSpeaking;
    @Bind(R.id.tv_speech_speaking_result)
    TextView tvSpeakingResult;
    private SpeechManager speechManager;
    private TextToSpeech _speechManagerOffline;
    private HardWareManager hm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_control);
        ButterKnife.bind(this);
        //初始化变量
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);

        // Setup Hardware Manager
        this.hm = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);

        // Setup Google Text to Speech
        _speechManagerOffline = new TextToSpeech(SpeechControlActivity.this, new TextToSpeech.OnInitListener() {

            @Override
                    public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale loc = new Locale("nl", "NL");
                    int result = _speechManagerOffline.setLanguage(loc);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    }
                } else
                    Log.e("error", "Initilization Failed!");
            }

            });

        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //设置唤醒，休眠回调
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
                tvSpeechStatus.setText(R.string.speech_wakeup_status);
            }

            @Override
            public void onSleep() {
                tvSpeechStatus.setText(R.string.speech_sleep_status);
            }
        });
        //语音识别回调
        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                //只有在配置了RECOGNIZE_MODE为1，且返回为true的情况下，才会拦截
                tvSpeechRecognizeResult.setText(grammar.getText());
                if(grammar.getTopic().equals("test_topic")) {
                    speechManager.startSpeak("接收到了自定义语义");
                    return true;
                }
                return cbInterceptMessage.isChecked();
            }

            @Override
            public void onRecognizeVolume(int i) {
                tvSpeechRecognizeVolume.setText(String.valueOf(i));
            }
        });
        //语音合成状态回调
        speechManager.setOnSpeechListener(new SpeakListener() {
            @Override
            public void onSpeakFinish() {
                tvSpeechSynthesizeFinish.setText(R.string.yes);
            }

            @Override
            public void onSpeakProgress(int i) {
                tvSpeechSynthesizeFinish.setText(R.string.no);
                tvSpeechSynthesizeProgress.setText(String.valueOf(i));
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {

    }

    /**
     * 处理所有的点击事件
     *
     * @param view
     */
    @OnClick({R.id.tv_speech_synthesize_start, R.id.tv_speech_synthesize_pause, R.id.tv_speech_synthesize_continue
            , R.id.tv_speech_synthesize_stop, R.id.tv_speech_sleep, R.id.tv_speech_wakeup, R.id.tv_speech_speaking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //开始合成语音
            case R.id.tv_speech_synthesize_start:
                hm.switchWhiteLight(false);
                SpeakOption speakOption = new SpeakOption();
                //设置合成语言
                if (rgLang.getCheckedRadioButtonId() == R.id.rb_chinese) {
                    speakOption.setLanguageType(SpeakOption.LAG_CHINESE);
                } else if (rgLang.getCheckedRadioButtonId() == R.id.rb_english) {
                    speakOption.setLanguageType(SpeakOption.LAG_ENGLISH_US);
                }
                else if (rgLang.getCheckedRadioButtonId() == R.id.rb_dutch) {
                    _speechManagerOffline.speak(etText.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    hm.switchWhiteLight(true);

                }
                //设置合成语速
                String speed = etSpeed.getText().toString();
                if (!TextUtils.isEmpty(speed) && Integer.parseInt(speed) >= 0 && Integer.parseInt(speed) <= 100) {
                    speakOption.setSpeed(Integer.parseInt(speed));
                }
                //设置合成声调
                String tone = etTone.getText().toString();
                if (!TextUtils.isEmpty(tone) && Integer.parseInt(tone) >= 0 && Integer.parseInt(tone) <= 100) {
                    speakOption.setIntonation(Integer.parseInt(tone));
                }
                speechManager.startSpeak(etText.getText().toString(), speakOption);
                break;
            //暂停合成语音
            case R.id.tv_speech_synthesize_pause:
                speechManager.pauseSpeak();
                break;
            //继续合成语音
            case R.id.tv_speech_synthesize_continue:
                speechManager.resumeSpeak();
                break;
            //停止合成语音
            case R.id.tv_speech_synthesize_stop:
                speechManager.stopSpeak();
                break;
            //休眠
            case R.id.tv_speech_sleep:
                speechManager.doSleep();
                break;
            //唤醒
            case R.id.tv_speech_wakeup:
                speechManager.doWakeUp();
                break;
            //机器人是否正在说话
            case R.id.tv_speech_speaking:
                if (speechManager.isSpeaking().getResult().equals("1")) {
                    hm.switchWhiteLight(true);
                    tvSpeakingResult.setText(R.string.speaking);
                } else if (speechManager.isSpeaking().getResult().equals("0")) {
                    tvSpeakingResult.setText(R.string.not_speaking);
                    hm.switchWhiteLight(false);
                }
                break;
        }
    }
}
