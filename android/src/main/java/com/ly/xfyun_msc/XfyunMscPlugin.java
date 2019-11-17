package com.ly.xfyun_msc;

import android.os.Bundle;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

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
    private SpeechSynthesizer mTts;
    private String content;

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
        } else  if (call.method.equals("speak")) {
            content = call.argument("content");
            mTts = SpeechSynthesizer.createSynthesizer(registrar.activeContext(), mTtsInitListener);
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            mTts.setParameter(SpeechConstant.SPEED, "50");
            mTts.startSpeaking(content, new SynthesizerListener() {
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

    private InitListener mTtsInitListener = new InitListener() {
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
