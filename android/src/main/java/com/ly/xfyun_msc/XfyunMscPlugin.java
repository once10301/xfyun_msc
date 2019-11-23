package com.ly.xfyun_msc;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;


/**
 * XfyunMscPlugin
 */
public class XfyunMscPlugin implements MethodCallHandler {
    private Registrar registrar;
    private Result result;

    private XfyunMscPlugin(Registrar registrar) {
        this.registrar = registrar;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "xfyun_msc");
        channel.setMethodCallHandler(new XfyunMscPlugin(registrar));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        this.result = result;
        if (call.method.equals("init")) {
            SpeechUtility.createUtility(registrar.activeContext(), SpeechConstant.APPID + "=" + call.argument("appId"));
            result.success(true);
        } else if (call.method.equals("speak")) {
            String content = call.argument("content");
            String type = call.argument("type");
            String speaker = call.argument("speaker");
            int speed = call.argument("speed");
            int volume = call.argument("volume");
            SpeechSynthesizer  tts = SpeechSynthesizer.createSynthesizer(registrar.activeContext(), ttsInitListener);
            //设置使用本地引擎
            tts.setParameter(SpeechConstant.ENGINE_TYPE, type);
            //设置发音人
            tts.setParameter(SpeechConstant.VOICE_NAME, speaker);
            //设置发音人资源路径
            if (type.equals(SpeechConstant.TYPE_LOCAL)) {
                tts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath(registrar.activeContext(), speaker));
            }
            //设置合成语速
            tts.setParameter(SpeechConstant.SPEED, speed + "");
            //设置合成音量
            tts.setParameter(SpeechConstant.VOLUME, volume + "");
            tts.startSpeaking(content, new SynthesizerListener() {
                @Override
                public void onSpeakBegin() {

                }

                @Override
                public void onBufferProgress(int i, int i1, int i2, String s) {

                }

                @Override
                public void onSpeakPaused() {

                }

                @Override
                public void onSpeakResumed() {

                }

                @Override
                public void onSpeakProgress(int i, int i1, int i2) {

                }

                @Override
                public void onCompleted(SpeechError speechError) {

                }

                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {

                }
            });
        } else {
            result.notImplemented();
        }
    }

    //获取发音人资源路径
    private String getResourcePath(Context context, String speaker) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = "tts";
        //合成通用资源
        stringBuffer.append(ResourceUtil.generateResourcePath(context, RESOURCE_TYPE.assets, type + "/common.jet"));
        stringBuffer.append(";");
        //发音人资源
        stringBuffer.append(ResourceUtil.generateResourcePath(context, RESOURCE_TYPE.assets, type + "/" + speaker + ".jet"));
        return stringBuffer.toString();
    }

    private InitListener ttsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code == ErrorCode.SUCCESS) {
                result.success(true);
            } else {
                result.success(false);
            }
        }
    };
}
