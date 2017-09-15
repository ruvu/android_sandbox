package com.qihancloud.librarydemo;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qihancloud.opensdk.base.BindBaseService;
import com.qihancloud.opensdk.beans.ErrorCode;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.beans.OperationResult;
import com.qihancloud.opensdk.function.beans.EmotionsType;
import com.qihancloud.opensdk.function.beans.LED;
import com.qihancloud.opensdk.function.beans.SpeakOption;
import com.qihancloud.opensdk.function.beans.speech.Grammar;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.SpeechManager;
import com.qihancloud.opensdk.function.unit.SystemManager;
import com.qihancloud.opensdk.function.unit.interfaces.speech.RecognizeListener;

/**
 * MainService.java
 * "Functional Description"
 * <p>
 * Created by 卢杰 on 2016/12/19
 * Copyright (c) 2016 QihanCloud, Inc. All Rights Reserved.
 */
public class MainService extends BindBaseService {
    SpeechManager speechManager;
    SystemManager systemManager;
    HardWareManager hardWareManager;

    @Override
    public void onCreate() {
        super.onCreate();
        register(MainService.class);
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
        systemManager = (SystemManager) getUnitManager(FuncConstant.SYSTEM_MANAGER);
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                Log.e("service",grammar.getText());
                return true;
            }

            @Override
            public void onRecognizeVolume(int volume) {

            }
        });
    }

    @Override
    protected void onMainServiceConnected() {
        speak();
        //setLed();
        //setEmotion();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        speak();
        //setEmotion();
        setLed();
        return super.onStartCommand(intent, flags, startId);
    }

    void setLed(){
        hardWareManager.setLED(new LED(LED.PART_ALL,LED.MODE_RED));
    }

    void speak(){
        SpeakOption speakOption = new SpeakOption();
        speakOption.setSpeed(70);
        OperationResult operationResult = speechManager.startSpeak("在服务中我也可以和机器人通讯哦",
                speakOption);
        if(operationResult.getErrorCode() != ErrorCode.SUCCESS){
            Log.e("error",operationResult.getDescription());
        }
    }

    /**
     * 表情控制
     */
    void setEmotion(){
        systemManager.showEmotion(EmotionsType.ANGRY);
    }

    public class MyBinder extends Binder {

        public MainService getService(){
            return MainService.this;
        }
    }

    private MyBinder myBinder = new MyBinder();
}
