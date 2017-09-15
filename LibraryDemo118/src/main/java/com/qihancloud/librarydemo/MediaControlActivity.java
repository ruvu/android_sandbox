package com.qihancloud.librarydemo;

import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.FaceRecognizeBean;
import com.qihancloud.opensdk.function.beans.StreamOption;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.qihancloud.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.qihancloud.opensdk.function.unit.interfaces.media.MediaStreamListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * className: com.qihancloud.librarydemo.MediaControlActivity
 * function: 多媒体控制
 * <p/> 人脸识别和抓图功能 需要安装“家庭成员app”
 * create at 2017/5/25 9:25
 *
 * @author gangpeng
 */

public class MediaControlActivity extends TopBaseActivity implements SurfaceHolder.Callback {

    private final static String TAG = MediaControlActivity.class.getSimpleName();

    @Bind(R.id.sv_media)
    SurfaceView svMedia;
   /* @Bind(R.id.tv_capture)
    TextView tvCapture;*/
    @Bind(R.id.iv_capture)
    ImageView ivCapture;
    @Bind(R.id.tv_face)
    TextView tvFace;

    private MediaManager mediaManager;

    /**
     * 视频编解码器
     */
    MediaCodec mediaCodec;
    /**
     * 视频编解码器超时时间
     */
    long decodeTimeout = 16000;
    MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
    ByteBuffer[] videoInputBuffers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_control);
        ButterKnife.bind(this);
        //初始化变量
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
        svMedia.getHolder().addCallback(this);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        mediaManager.setMediaListener(new MediaStreamListener() {
            @Override
            public void getVideoStream(byte[] bytes) {
                showViewData(ByteBuffer.wrap(bytes));
            }

            @Override
            public void getAudioStream(byte[] bytes) {
            }
        });
        mediaManager.setMediaListener(new FaceRecognizeListener() {
            @Override
            public void recognizeResult(List<FaceRecognizeBean> list) {
                StringBuilder sb = new StringBuilder();
                for (FaceRecognizeBean bean : list) {
                    sb.append(new Gson().toJson(bean));
                    sb.append("\n");
                }
                tvFace.setText(sb.toString());
            }
        });
    }

    /**
     * 显示视频流
     *
     * @param sampleData
     */
    private void showViewData(ByteBuffer sampleData) {
        try {
            int inIndex = mediaCodec.dequeueInputBuffer(decodeTimeout);
            if (inIndex >= 0) {
                ByteBuffer buffer = videoInputBuffers[inIndex];
                int sampleSize = sampleData.limit();
                buffer.clear();
                buffer.put(sampleData);
                buffer.flip();
                mediaCodec.queueInputBuffer(inIndex, 0, sampleSize, 0, 0);
            }
            int outputBufferId = mediaCodec.dequeueOutputBuffer(videoBufferInfo, decodeTimeout);
            if (outputBufferId >= 0) {
                mediaCodec.releaseOutputBuffer(outputBufferId, true);
            } else {
                Log.e(TAG, "dequeueOutputBuffer() error");
            }

        } catch (Exception e) {
            Log.e(TAG, "发生错误", e);
        }
    }

    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //设置参数并打开媒体流
        StreamOption streamOption = new StreamOption();
        streamOption.setChannel(StreamOption.MAIN_STREAM);
        streamOption.setDecodType(StreamOption.HARDWARE_DECODE);
        streamOption.setJustIframe(false);
        mediaManager.openStream(streamOption);
        //配置MediaCodec
        startDecoding(holder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //关闭媒体流
        mediaManager.closeStream();
        stopDecoding();
    }

    /**
     * 初始化视频编解码器
     *
     * @param surface
     */
    private void startDecoding(Surface surface) {
        if (mediaCodec != null) {
            return;
        }
        try {
            mediaCodec = MediaCodec.createDecoderByType("video/avc");
            MediaFormat format = MediaFormat.createVideoFormat(
                    "video/avc", 1280, 720);
            mediaCodec.configure(format, surface, null, 0);
            mediaCodec.start();
            videoInputBuffers = mediaCodec.getInputBuffers();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束视频编解码器
     */
    private void stopDecoding() {
        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
            mediaCodec = null;
            Log.i(TAG, "stopDecoding");
        }
        videoInputBuffers = null;
    }

   /* @OnClick(R.id.tv_capture)
    public void onViewClicked() {
        storeImage(mediaManager.getVideoImage());
        ivCapture.setImageBitmap(mediaManager.getVideoImage());
    }*/

    public void storeImage(Bitmap bitmap){
        String dir = Environment.getExternalStorageDirectory()+ "/FACE_REG/IMG/" + "DCIM/";
        final File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(f, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
