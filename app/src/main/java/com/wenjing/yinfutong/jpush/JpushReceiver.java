package com.wenjing.yinfutong.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.activity.MainActivity;
import com.wenjing.yinfutong.function.mine.fragment.ToRechargeFragment;
import com.wenjing.yinfutong.iflytek.speech.setting.TtsSettings;
import com.wenjing.yinfutong.iflytek.voicedemo.TtsDemo;
import com.wenjing.yinfutong.utils.TLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.MODE_PRIVATE;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JpushReceiver extends BroadcastReceiver {

    public static final String KEY_TRADEAMOUNT = "tradeAmount" ;
    public static final String KEY_STATUS       ="status" ;

    /**
     * mutable-content : "1" 语音播放  “0" 不语音播放
     */
	public static final String KEY_ISSPEECH      ="mutable-content" ;

	public static final int STATUS_RECHARGE_SUCCESS      = 1;
	public static final int STATUS_RECHARGE_FAILED       = 2;
	public static final int STATUS_PAY_SUCCESS            = 3;
	public static final int STATUS_PAY_FAILED             = 4;
	public static final int STATUS_WITHDRAW_SUCCESS       = 5;
	public static final int STATUS_WITHDRAW_FAILED        = 6;
	public static final int STATUS_COLLECT_SUCCESS        = 7;
	public static final int STATUS_COLLECT_FAILED         = 8;
	public static final int STATUS_USER_LOGINAGAIN        = 9;


	private static String TAG = TtsDemo.class.getSimpleName();
	// 语音合成对象
	private SpeechSynthesizer mTts;

	// 默认发音人
	private String voicer = "xiaoyan";


	// 缓冲进度
	private int mPercentForBuffering = 0;
	// 播放进度
	private int mPercentForPlaying = 0;

	// 云端/本地单选按钮
	private RadioGroup mRadioGroup;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;

	private Toast mToast;
	private SharedPreferences mSharedPreferences;



	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			TLog.log(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				TLog.log(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				TLog.log(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				TLog.log(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				TLog.log(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

				receiveMsg(context , intent);
			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				TLog.log(TAG, "[MyReceiver] 用户点击打开了通知");

				//打开自定义的Activity
//				Intent i = new Intent(context, MainActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				TLog.log(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				TLog.log(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
			} else {
				TLog.log(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){
            TLog.log(TAG,e.getMessage());
		}

	}

	private void receiveMsg(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);

        String extraContent = bundle.getString(JPushInterface.EXTRA_EXTRA);
		JSONObject json = null;
		try {
			json = new JSONObject(extraContent);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String amount = json.optString(KEY_TRADEAMOUNT);
		int status = json.optInt(KEY_STATUS);
		String isSpeech = json.optString(KEY_ISSPEECH);

        String msg = null;

        switch (status){
            case STATUS_RECHARGE_SUCCESS:
                msg = AppContext.instance().getResources().getString(R.string.succ_recharge) + amount + AppContext.instance().getResources().getString(R.string.dollar) ;

				//线下分销商充值 成功
				sendOfflineRechargeBroadcast(context , bundle , ToRechargeFragment.ACTION_OFFLINERECHARGE_SUCC);
                break;
            case STATUS_RECHARGE_FAILED:
                msg = AppContext.instance().getResources().getString(R.string.fail_recharge) ;
				//线下分销商充值 失败
				sendOfflineRechargeBroadcast(context , bundle , ToRechargeFragment.ACTION_OFFLINERECHARGE_FAIL);
                break;
            case STATUS_PAY_SUCCESS:
                msg = AppContext.instance().getResources().getString(R.string.pay_succ) ;
                break;
            case STATUS_PAY_FAILED:
                msg = AppContext.instance().getResources().getString(R.string.pay_fail) ;
                break;

            case STATUS_WITHDRAW_SUCCESS:
                msg = AppContext.instance().getResources().getString(R.string.succ_withdraw_deposit) + amount + AppContext.instance().getResources().getString(R.string.dollar) ;
                break;
            case STATUS_WITHDRAW_FAILED:
                msg = AppContext.instance().getResources().getString(R.string.fail_withdraw_deposit) ;
                break;
            case STATUS_COLLECT_SUCCESS:
                msg = AppContext.instance().getResources().getString(R.string.succ_gathering) + amount + AppContext.instance().getResources().getString(R.string.dollar) ;
                break;
            case STATUS_COLLECT_FAILED:
                msg = AppContext.instance().getResources().getString(R.string.gathering_fail) ;
                break;
			case STATUS_USER_LOGINAGAIN:
				TLog.log(TAG , "alert : " + alert + " , msg : " + msg + " , amount : " + amount + " , status : " + status + " , isSpeech : " + isSpeech);

				//清除极光 别名  注册
				JpushUtils.deleteMyJpushAlias(AppContext.instance().getLoginUser());

				//清除缓存数据
				AppContext.instance().clearLoginUser(context);

				break;
        }


//        if(isSpeak) mTTS.notifyNewMessage(alert);

		// 初始化合成对象
		mTts = SpeechSynthesizer.createSynthesizer(AppContext.instance(), mTtsInitListener);

		mSharedPreferences = AppContext.instance().getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);


		// 移动数据分析，收集开始合成事件
		FlowerCollector.onEvent(AppContext.instance(), "tts_play");


		// 设置参数
		setParam();
		int code = mTts.startSpeaking(alert, mTtsListener);
		//			/**
		//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
		//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
		//			*/
		//			String path = Environment.getExternalStorageDirectory()+"/tts.ico";
		//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

		if (code != ErrorCode.SUCCESS) {
			showTip("语音合成失败,错误码: " + code);
		}


        TLog.log(TAG , "alert : " + alert + " , msg : " + msg + " , amount : " + amount + " , status : " + status + " , isSpeech : " + isSpeech);
	}

    private boolean isSpeak(String isSpeech) {
	    if(TextUtils.isEmpty(isSpeech)){
	        return false;
        }

        if(isSpeech.equals("1")){
	        return true;
        }

        return false;
    }

    // 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					TLog.log(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					TLog.log(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	//send msg to ToRechargeFragment

	/**
	 * 发送消息到   充值页面
	 * @param context
	 * @param bundle
	 */
	private void sendOfflineRechargeBroadcast(Context context, Bundle bundle , String action) {
		if (MainActivity.isForeground) {//在前台
			TLog.log(this , "MainActivity.isForeground : " + MainActivity.isForeground);

			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(action);
			msgIntent.putExtra(ToRechargeFragment.KEY_MESSAGE, message);
			if (!TextUtils.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(ToRechargeFragment.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
			TLog.log(this , "LocalBroadcastManager.getInstance(context) : action : " + action);
		}
	}







	/**
	 * 初始化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码："+code);
			} else {
				// 初始化成功，之后可以调用startSpeaking方法
				// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
				// 正确的做法是将onCreate中的startSpeaking调用移至这里
			}
		}
	};

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
									 String info) {
			// 合成进度
			mPercentForBuffering = percent;
			showTip(String.format(AppContext.instance().getResources().getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			mPercentForPlaying = percent;
			showTip(String.format(AppContext.instance().getResources().getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				showTip("播放完成");
			} else if (error != null) {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}



	/**
	 * 参数设置
	 * @return
	 */
	private void setParam(){
		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);
		// 根据合成引擎设置相应参数
		if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
			mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			// 设置在线合成发音人
			mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
			//设置合成语速
			mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
			//设置合成音调
			mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
			//设置合成音量
			mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
		}else {
			mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
			// 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
			mTts.setParameter(SpeechConstant.VOICE_NAME, "");
		}
		//设置播放器音频流类型
		mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
	}


}
