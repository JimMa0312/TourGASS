package com.wollon.tourgass.util.navtts;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.util.LinkedList;

/**
 * Created by JimMa on 2017/11/28.
 */

public class AmapTTSControler {
    private final String APP_ID="5a1cd0ab";

    private final int TTS_PLAY=1;
    private final int CHECK_TTS_PLAY=2;

    public static AmapTTSControler ttsManager;
    private Context mContext;
    private SpeechSynthesizer mTts;
    private boolean isPlaying=false;
    private LinkedList<String> wordList=new LinkedList<>();//消息队列

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TTS_PLAY:
                    synchronized (mTts){
                        if (!isPlaying&&mTts!=null && wordList.size()>0){
                            isPlaying=true;
                            String playtts=wordList.removeFirst();
                            if (mTts==null){
                                createSynthesizer();
                            }
                            mTts.startSpeaking(playtts, new SynthesizerListener() {
                                @Override
                                public void onSpeakBegin() {
                                    AMapNavi.setTtsPlaying(isPlaying=true);
                                }

                                @Override
                                public void onBufferProgress(int i, int i1, int i2, String s) {
                                    isPlaying=true;
                                }

                                @Override
                                public void onSpeakPaused() {

                                }

                                @Override
                                public void onSpeakResumed() {
                                    isPlaying=true;
                                }

                                @Override
                                public void onSpeakProgress(int i, int i1, int i2) {
                                    isPlaying=true;
                                }

                                @Override
                                public void onCompleted(SpeechError speechError) {
                                    AMapNavi.setTtsPlaying(isPlaying=false);
                                    handler.obtainMessage(1).sendToTarget();
                                }

                                @Override
                                public void onEvent(int i, int i1, int i2, Bundle bundle) {

                                }
                            });
                        }
                    }
                    break;
                case CHECK_TTS_PLAY:
                    if (!isPlaying){
                        handler.obtainMessage(1).sendToTarget();
                    }
                    break;
            }
        }
    };


    private AmapTTSControler(Context context){
        mContext=context.getApplicationContext();
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID+"="+APP_ID);
        if (mTts==null){
            createSynthesizer();
        }
    }

    private void createSynthesizer() {
        mTts=SpeechSynthesizer.createSynthesizer(mContext,
                new InitListener() {
                    @Override
                    public void onInit(int i) {
                        if (ErrorCode.SUCCESS==i){

                        }else{

                            Toast.makeText(mContext,"语音合成初始化失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void init(){
        //设置发声人
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        //设置语速
        mTts.setParameter(SpeechConstant.SPEED,"55");
        //设置音量
        mTts.setParameter(SpeechConstant.VOLUME,"tts_valume");
        //设置语调
        mTts.setParameter(SpeechConstant.PITCH,"tts_pitch");
    }

    public static AmapTTSControler getInstance(Context context){
        if(ttsManager==null){
            ttsManager=new AmapTTSControler(context);
        }

        return ttsManager;
    }

    public void stopSpeaking(){
        if(wordList!=null){
            wordList.clear();
        }
        if (mTts!=null){
            mTts.stopSpeaking();
        }
        isPlaying=false;
    }

    public void destroy(){
        if(wordList!=null){
            wordList.clear();
        }
        if (mTts!=null){
            mTts.destroy();
        }
    }

    public void onGetNavigationText(String arg1){
        if (wordList!=null)
            wordList.addLast(arg1);
        //TODO
    }
}
